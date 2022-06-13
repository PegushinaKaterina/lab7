package katya.server.util;

import katya.common.entites.RequestType;
import katya.server.util.workingWithClient.ServerSocketWorker;
import katya.server.util.workingWithCommand.CommandManager;

import java.io.IOException;
import java.util.concurrent.*;

public class RequestThread extends Thread {
    private final ServerSocketWorker serverSocketWorker;
    private final CommandManager commandManager;
    private final UsersManager userManager;

    private final ExecutorService cachedService = Executors.newCachedThreadPool();
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(4);

    public RequestThread(ServerSocketWorker serverSocketWorker, CommandManager commandManager, UsersManager usersManager) {
        this.serverSocketWorker = serverSocketWorker;
        this.commandManager = commandManager;
        this.userManager = usersManager;
    }

    @Override
    public void run() {
        while (commandManager.getStatusOfCommandListening()) {
            try {
                ForkJoinTask<RequestWithAddress> listenTask = forkJoinPool.submit(serverSocketWorker::receiveRequest);
                RequestWithAddress acceptedRequest = listenTask.get();
                CompletableFuture.supplyAsync(acceptedRequest::getRequest)
                        .thenApplyAsync(request -> {
                            if (request.getRequestTypeArgument().equals(RequestType.COMMAND)) {
                                return commandManager.executeClientCommand(request);
                            } else if (request.getRequestTypeArgument().equals(RequestType.LOGIN)) {
                                return userManager.loginUser(request);
                            } else if (request.getRequestTypeArgument().equals(RequestType.REGISTER)) {
                                return userManager.registerNewUser(request);
                            } else {
                                return null;
                            }
                        }, cachedService)
                        .thenAcceptAsync(responseToSend -> {
                            try {
                                serverSocketWorker.sendResponse(responseToSend, acceptedRequest.getSocketAddress());
                            } catch (IOException e) {
                                System.out.println("Ошибка при отправке ответа клиенту");
                            }
                        }, cachedService);
            } catch (ExecutionException e) {
                System.out.println("Ошибка при обработке запроса от клиента");
            } catch (InterruptedException e) {
                System.out.println("Поток был прерван");
            }
        }
        serverSocketWorker.stopServer();
    }
}

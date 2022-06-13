package katya.client.util.workingWithServer;

import katya.common.entites.RequestType;
import katya.common.util.Request;
import katya.common.util.RequestBuilder;
import katya.common.util.Response;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class GeneratorUser {
    private List<String> user;

    public GeneratorUser(Scanner scanner, ClientSocketWorker clientSocketWorker) {
        boolean isRunning = true;
        while (isRunning) {
            String question = "У вас есть аккаунт? Введите да/нет";
            boolean answer = UserInitializer.askForRegistration(question, scanner);
            List<String> user = UserInitializer.getUser(scanner);
            boolean result;
            if (answer) {
                if (loginUser(user.get(0), user.get(1), clientSocketWorker)) {
                    isRunning = false;
                    this.user = user;
                }
            } else {
                if (registerUser(user.get(0), user.get(1), clientSocketWorker)) {
                    isRunning = false;
                }
                this.user = user;
            }
        }
    }

    private boolean registerUser(String login, String password, ClientSocketWorker clientSocketWorker) {
        try {
            clientSocketWorker.sendRequest(new Request(new RequestBuilder().withUsernameArgument(login).withPasswordArgument(password).withRequestTypeArgument(RequestType.REGISTER)));
            Response response = null;
            for (int i = 0; i < 50 && response == null; i++) {
                try {
                    System.out.println("Ждем ответ от сервера...");
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = clientSocketWorker.receiveResponse();
            }
            if (response == null) {
                System.out.println("Превышено время ожидания ответа от сервера");
                return false;
            }
            System.out.println(response.getMessageToResponse());
            return response.isSuccess();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при получении ответа");
        }
        return false;
    }

    private boolean loginUser(String login, String password, ClientSocketWorker clientSocketWorker) {
        try {
            clientSocketWorker.sendRequest(new Request(new RequestBuilder().withUsernameArgument(login).withPasswordArgument(password).withRequestTypeArgument(RequestType.LOGIN)));
            Response response = null;
            for (int i = 0; i < 50 && response == null; i++) {
                try {
                    System.out.println("Ждем ответ от сервера...");
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = clientSocketWorker.receiveResponse();
            }
            if (response == null) {
                System.out.println("Превышено время ожидания ответа от сервера");
                return false;
            }
            System.out.println(response.getMessageToResponse());
            return response.isSuccess();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при получении ответа");
        }
        return false;
    }

    public List<String> getUser() {
        return user;
    }
}

package katya.server;

import katya.server.DB.Connector;
import katya.server.DB.DBManager;
import katya.server.entites.CollectionManager;
import katya.server.util.ConsoleThread;
import katya.server.util.RequestThread;
import katya.server.util.UsersManager;
import katya.server.util.workingWithClient.GeneratorServerSocketWorker;
import katya.server.util.workingWithClient.ServerSocketWorker;
import katya.server.util.workingWithCommand.CommandManager;
import katya.server.util.workingWithCommand.CommandProcessor;
import katya.server.util.workingWithCommand.ServerCommandListener;

import java.sql.SQLException;
import java.util.Scanner;

public class ServerWorker {
    private final ServerCommandListener serverCommandListener = new ServerCommandListener();
    private Connector dbConnector;
    private DBManager dbManager;
    private UsersManager usersManager;
    private CommandProcessor commandProcessor;
    private CommandManager commandManager;
    private CollectionManager collectionManager;

    public void startServerWorker() {
        dbConnector = new Connector();
        dbManager = new DBManager(dbConnector);
        usersManager = new UsersManager(dbManager);
        collectionManager = new CollectionManager();
        commandProcessor = new CommandProcessor(dbManager, collectionManager);
        commandManager = new CommandManager(commandProcessor);
        try {
            collectionManager.setHumanBeings(dbManager.loadCollection());
        } catch (SQLException e) {
            System.out.println("Ошибка при инициализации коллекции");
            e.printStackTrace();
            System.exit(1);
        }
        Scanner scanner = new Scanner(System.in);
        GeneratorServerSocketWorker generatorServerSocketWorker = new GeneratorServerSocketWorker(scanner);
        ServerSocketWorker serverSocketWorker = generatorServerSocketWorker.getServerSocketWorker();
        RequestThread requestThread = new RequestThread(serverSocketWorker, commandManager, usersManager);
        ConsoleThread consoleThread = new ConsoleThread(serverCommandListener, commandManager, scanner);
        requestThread.start();
        consoleThread.start();
    }
}

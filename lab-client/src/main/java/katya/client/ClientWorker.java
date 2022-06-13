package katya.client;

import katya.client.generators.ConsoleGeneratorHumanBeing;
import katya.client.util.workingWithCommand.CommandManager;
import katya.client.util.workingWithServer.ClientSocketWorker;
import katya.client.util.workingWithServer.GeneratorClientSocketWorker;
import katya.client.util.workingWithServer.GeneratorUser;
import katya.common.entites.HumanBeing;

import java.util.List;
import java.util.Scanner;

public class ClientWorker {
    public void startClientWorker() {
        try (Scanner scanner = new Scanner(System.in)) {
            GeneratorClientSocketWorker generatorClientSocketWorker = new GeneratorClientSocketWorker(scanner);
            ClientSocketWorker clientSocketWorker = generatorClientSocketWorker.getClientSocketWorker();
            GeneratorUser generatorUser = new GeneratorUser(scanner, clientSocketWorker);
            List<String> user = generatorUser.getUser();
            HumanBeing.getGeneratorHumanBeing().changeState(new ConsoleGeneratorHumanBeing(scanner));
            CommandManager.runConsoleCycle(scanner, clientSocketWorker, user);
        }
    }
}

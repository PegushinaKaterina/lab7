package katya.client.util.workingWithServer;

import katya.common.util.SocketInitializer;

import java.io.IOException;
import java.util.Scanner;

public class GeneratorClientSocketWorker {
    private ClientSocketWorker clientSocketWorker;

    public GeneratorClientSocketWorker(Scanner scanner) {
        setAddress(scanner);
        setPort(scanner);
    }

    private void setAddress(Scanner scanner) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                clientSocketWorker = new ClientSocketWorker();
                String address = SocketInitializer.askForAddress(scanner);
                if (address != null) {
                    clientSocketWorker.setAddress(address);
                }
                isRunning = false;
            } catch (IOException e) {
                System.out.println("Ошибка при установке адреса");
            }
        }
    }

    private void setPort(Scanner scanner) {
        Integer port = SocketInitializer.askForPort(scanner);
        if (port != null) {
            clientSocketWorker.setPort(port);
        }
    }

    public ClientSocketWorker getClientSocketWorker() {
        return clientSocketWorker;
    }
}

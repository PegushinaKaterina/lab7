package katya.server.commands.serverCommands;

import katya.server.util.workingWithCommand.CommandManager;

import java.util.Scanner;

public class ExitCommand extends AbstractServerCommand {
    private final Scanner scanner = new Scanner(System.in);

    public ExitCommand() {
        super(new AbstractServerCommand.AbstractCommandBuilder()
                .withName("exit")
                .withDescription("завершить программу"));
    }

    @Override
    public String executeCommand() {
        CommandManager.changeStatus();
        System.out.println("Работа сервера завершена");
        scanner.close();
        System.exit(0);
        return "Работа сервера завершена";
    }
}

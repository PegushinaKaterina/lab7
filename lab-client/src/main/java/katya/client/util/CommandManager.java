package katya.client.util;

import katya.client.ClientSocketWorker;
import katya.client.commands.ExecuteScriptCommand;
import katya.client.commands.ExitCommand;
import katya.client.commands.ReceiveResponseCommand;
import katya.client.commands.SendRequestCommand;

import java.util.Locale;
import java.util.Scanner;

public class CommandManager {
    private static final CommandListener commandListener = new CommandListener();
    private static boolean statusOfCommandListening = true;

    public static void performCommandd(Scanner scanner, ClientSocketWorker clientSocketWorker) {
        while (statusOfCommandListening) {
            CommandToSend command = commandListener.readCommandFromConsole(scanner);
            performCommand(command, clientSocketWorker);
        }
    }

    public static void performCommand(CommandToSend command, ClientSocketWorker clientSocketWorker) {
        String name = command.getCommandName();
        if ("exit".equals(command.getCommandName().toLowerCase(Locale.ROOT))) {
            ExitCommand.executeCommand(command.getCommandArgs());
        } else if ("execute_script".equals(command.getCommandName())) {
            ExecuteScriptCommand.executeCommand(command.getCommandArgs(), clientSocketWorker);
        } else if (SendRequestCommand.executeCommand(command, clientSocketWorker)) {
            ReceiveResponseCommand.executeCommand(clientSocketWorker);
        }
    }

    public static void changeStatus() {
        statusOfCommandListening = !statusOfCommandListening;
    }

    public static CommandListener getCommandListener() {
        return commandListener;
    }
}

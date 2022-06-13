package katya.server.util.workingWithCommand;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.server.commands.AvailableCommands;

import java.time.format.DateTimeFormatter;

public class CommandManager {
    private static boolean statusOfCommandListening = true;
    private static CommandHistory commandHistory = new CommandHistory();
    private final AvailableCommands availableCommands;

    public CommandManager(CommandProcessor commandProcessor) {
        availableCommands = new AvailableCommands(commandProcessor);
    }

    public static void changeStatus() {
        statusOfCommandListening = !statusOfCommandListening;
    }

    public static boolean isStatusOfCommandListening() {
        return statusOfCommandListening;
    }

    public static void setStatusOfCommandListening(boolean statusOfCommandListening) {
        CommandManager.statusOfCommandListening = statusOfCommandListening;
    }

    public static CommandHistory getCommandHistory() {
        return commandHistory;
    }

    public static void setCommandHistory(CommandHistory commandHistory) {
        CommandManager.commandHistory = commandHistory;
    }

    public Response executeClientCommand(Request request) {
        commandHistory.pushCommand(request.getCurrentTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                + " " + request.getClientInfo() + ": " + request.getCommandName());
        return AvailableCommands.CLIENT_AVAILABLE_COMMANDS.get(request.getCommandName()).executeCommand(request);
    }

    public String executeServerCommand(String commandName) {
        if (AvailableCommands.SERVER_AVAILABLE_COMMANDS.containsKey(commandName)) {
            return AvailableCommands.SERVER_AVAILABLE_COMMANDS.get(commandName).executeCommand();
        } else {
            return ("Такой команды не существует, для того, чтобы увидеть список команд введите HELP");
        }
    }

    public boolean getStatusOfCommandListening() {
        return statusOfCommandListening;
    }

    public AvailableCommands getAvailableCommands() {
        return availableCommands;
    }
}

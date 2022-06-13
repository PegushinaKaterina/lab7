package katya.server.commands;

import katya.server.commands.clientCommands.*;
import katya.server.commands.serverCommands.AbstractServerCommand;
import katya.server.commands.serverCommands.ExitCommand;
import katya.server.commands.serverCommands.ServerHelpCommand;
import katya.server.util.workingWithCommand.CommandManager;
import katya.server.util.workingWithCommand.CommandProcessor;

import java.util.HashMap;

public class AvailableCommands {
    public static final HashMap<String, AbstractClientCommand> CLIENT_AVAILABLE_COMMANDS = new HashMap<>();
    public static final HashMap<String, AbstractServerCommand> SERVER_AVAILABLE_COMMANDS = new HashMap<>();

    public AvailableCommands(CommandProcessor commandProcessor) {
        AbstractClientCommand clientHelpCommand = new ClientHelpCommand(commandProcessor, CLIENT_AVAILABLE_COMMANDS);
        AbstractClientCommand infoCommand = new InfoCommand(commandProcessor);
        AbstractClientCommand showCommand = new ShowCommand(commandProcessor);
        AbstractClientCommand addCommand = new AddCommand(commandProcessor);
        AbstractClientCommand updateCommand = new UpdateCommand(commandProcessor);
        AbstractClientCommand removeByIdCommand = new RemoveByIdCommand(commandProcessor);
        AbstractClientCommand clearCommand = new ClearCommand(commandProcessor);
        AbstractClientCommand removeHead = new RemoveHeadCommand(commandProcessor);
        AbstractClientCommand removeLower = new RemoveLowerCommand(commandProcessor);
        AbstractClientCommand historyCommand = new HistoryCommand(commandProcessor, CommandManager.getCommandHistory().getHistory());
        AbstractClientCommand removeAllByMinutesOfWaiting = new RemoveAllByMinutesOfWaitingCommand(commandProcessor);
        AbstractClientCommand sumOfMinutesOfWaiting = new SumOfMinutesOfWaitingCommand(commandProcessor);
        AbstractClientCommand countByImpactSpeed = new CountByImpactSpeedCommand(commandProcessor);

        CLIENT_AVAILABLE_COMMANDS.put(clientHelpCommand.getName(), clientHelpCommand);
        CLIENT_AVAILABLE_COMMANDS.put(infoCommand.getName(), infoCommand);
        CLIENT_AVAILABLE_COMMANDS.put(showCommand.getName(), showCommand);
        CLIENT_AVAILABLE_COMMANDS.put(addCommand.getName(), addCommand);
        CLIENT_AVAILABLE_COMMANDS.put(updateCommand.getName(), updateCommand);
        CLIENT_AVAILABLE_COMMANDS.put(removeByIdCommand.getName(), removeByIdCommand);
        CLIENT_AVAILABLE_COMMANDS.put(clearCommand.getName(), clearCommand);
        CLIENT_AVAILABLE_COMMANDS.put(removeHead.getName(), removeHead);
        CLIENT_AVAILABLE_COMMANDS.put(removeLower.getName(), removeLower);
        CLIENT_AVAILABLE_COMMANDS.put(historyCommand.getName(), historyCommand);
        CLIENT_AVAILABLE_COMMANDS.put(removeAllByMinutesOfWaiting.getName(), removeAllByMinutesOfWaiting);
        CLIENT_AVAILABLE_COMMANDS.put(sumOfMinutesOfWaiting.getName(), sumOfMinutesOfWaiting);
        CLIENT_AVAILABLE_COMMANDS.put(countByImpactSpeed.getName(), countByImpactSpeed);

        AbstractServerCommand serverHelpCommand = new ServerHelpCommand(SERVER_AVAILABLE_COMMANDS);
        AbstractServerCommand exitCommand = new ExitCommand();

        SERVER_AVAILABLE_COMMANDS.put(serverHelpCommand.getName(), serverHelpCommand);
        SERVER_AVAILABLE_COMMANDS.put(exitCommand.getName(), exitCommand);
    }
}

package katya.server.commands.clientCommands;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.server.util.workingWithCommand.CommandProcessor;

import java.util.HashMap;

public class ClientHelpCommand extends AbstractClientCommand {
    private final HashMap<String, AbstractClientCommand> availableCommands;
    private final CommandProcessor commandProcessor;

    public ClientHelpCommand(CommandProcessor commandProcessor, HashMap<String, AbstractClientCommand> availableCommands) {
        super(new AbstractCommandBuilder()
                .withName("help")
                .withQuantityOfArgs(0)
                .withDescription("вывести справку по доступным командам")
                .withGeneratesHumanBeing(false));
        this.availableCommands = availableCommands;
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeCommand(Request request) {
        return commandProcessor.help(request, availableCommands);
    }
}

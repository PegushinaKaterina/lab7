package katya.server.commands.clientCommands;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.server.util.workingWithCommand.CommandProcessor;

public class RemoveLowerCommand extends AbstractClientCommand {
    private final CommandProcessor commandProcessor;

    public RemoveLowerCommand(CommandProcessor commandProcessor) {
        super(new AbstractCommandBuilder()
                .withName("remove_lower")
                .withQuantityOfArgs(0)
                .withDescription("удалить из коллекции все элементы, меньшие, чем заданный")
                .withGeneratesHumanBeing(true));
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeCommand(Request request) {
        return commandProcessor.removeLower(request);
    }
}

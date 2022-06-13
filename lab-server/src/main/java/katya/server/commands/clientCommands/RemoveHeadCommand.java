package katya.server.commands.clientCommands;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.server.util.workingWithCommand.CommandProcessor;

public class RemoveHeadCommand extends AbstractClientCommand {
    private final CommandProcessor commandProcessor;

    public RemoveHeadCommand(CommandProcessor commandProcessor) {
        super(new AbstractCommandBuilder()
                .withName("remove_head")
                .withQuantityOfArgs(0)
                .withDescription("вывести первый элемент коллекции и удалить его")
                .withGeneratesHumanBeing(false));
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeCommand(Request request) {
        return commandProcessor.removeHead(request);
    }
}

package katya.server.commands.clientCommands;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.server.util.workingWithCommand.CommandProcessor;

public class ShowCommand extends AbstractClientCommand {
    private final CommandProcessor commandProcessor;

    public ShowCommand(CommandProcessor commandProcessor) {
        super(new AbstractCommandBuilder()
                .withName("show")
                .withQuantityOfArgs(0)
                .withDescription("вывести в стандартный поток вывода все элементы коллекции в строковом представлении")
                .withGeneratesHumanBeing(false));
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeCommand(Request request) {
        return commandProcessor.show(request);
    }
}

package katya.server.commands.clientCommands;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.server.util.workingWithCommand.CommandProcessor;

public class InfoCommand extends AbstractClientCommand {
    private final CommandProcessor commandProcessor;

    public InfoCommand(CommandProcessor commandProcessor) {
        super(new AbstractCommandBuilder()
                .withName("info")
                .withQuantityOfArgs(0)
                .withDescription(" вывести в стандартный поток вывода информацию о коллекции")
                .withGeneratesHumanBeing(false));
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeCommand(Request request) {
        return commandProcessor.info(request);
    }
}

package katya.server.commands.clientCommands;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.server.util.workingWithCommand.CommandProcessor;

/**
 * Класс команды: add {element} : добавить новый элемент в коллекцию
 */
public class AddCommand extends AbstractClientCommand {
    private final CommandProcessor commandProcessor;

    public AddCommand(CommandProcessor commandProcessor) {
        super(new AbstractCommandBuilder()
                .withName("add")
                .withQuantityOfArgs(0)
                .withDescription("добавить новый элемент в коллекцию")
                .withGeneratesHumanBeing(true));
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeCommand(Request request) {
        return commandProcessor.add(request);

    }
}

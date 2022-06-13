package katya.server.commands.clientCommands;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.server.util.workingWithCommand.CommandProcessor;

public class RemoveAllByMinutesOfWaitingCommand extends AbstractClientCommand {
    private final CommandProcessor commandProcessor;

    public RemoveAllByMinutesOfWaitingCommand(CommandProcessor commandProcessor) {
        super(new AbstractCommandBuilder()
                .withName("remove_all_by_minutes_of_waiting")
                .withQuantityOfArgs(1)
                .withDescription("удалить из коллекции все элементы, значение поля ВРЕМЯ ОЖИДАНИЯ которого эквивалентно заданному. ")
                .withDescriptionOfArgs("Значение поля ВРЕМЯ ОЖИДАНИЯ - целое число минут")
                .withGeneratesHumanBeing(false));
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeCommand(Request request) {
        return commandProcessor.removeAllByMinutesOfWaiting(request);
    }
}

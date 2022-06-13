package katya.server.commands.clientCommands;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.server.util.workingWithCommand.CommandProcessor;

public class SumOfMinutesOfWaitingCommand extends AbstractClientCommand {
    private final CommandProcessor commandProcessor;

    public SumOfMinutesOfWaitingCommand(CommandProcessor commandProcessor) {
        super(new AbstractCommandBuilder()
                .withName("sum_of_minutes_of_waiting")
                .withQuantityOfArgs(0)
                .withDescription("вывести сумму значений поля ВРЕМЯ ОЖИДАНИЯ для всех элементов коллекции")
                .withGeneratesHumanBeing(false));
        this.commandProcessor = commandProcessor;
    }


    @Override
    public Response executeCommand(Request request) {
        return commandProcessor.sumOfMinutesOfWaiting(request);
    }
}

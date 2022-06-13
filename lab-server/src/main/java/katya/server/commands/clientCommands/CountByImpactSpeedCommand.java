package katya.server.commands.clientCommands;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.server.util.workingWithCommand.CommandProcessor;

public class CountByImpactSpeedCommand extends AbstractClientCommand {
    private final CommandProcessor commandProcessor;

    public CountByImpactSpeedCommand(CommandProcessor commandProcessor) {
        super(new AbstractCommandBuilder()
                .withName("count_by_impact_speed")
                .withQuantityOfArgs(1)
                .withDescription("вывести количество элементов, значение поля СКОРОСТЬ УДАРА которых равно заданному. ")
                .withDescriptionOfArgs("Значение поля СКОРОСТЬ УДАРА - вещественное число, которое больше чем -484")
                .withGeneratesHumanBeing(false));
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeCommand(Request request) {
        return commandProcessor.countByImpactSpeed(request);
    }
}

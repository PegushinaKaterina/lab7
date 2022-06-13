package katya.server.commands.clientCommands;

import katya.common.util.Request;
import katya.common.util.Response;
import katya.server.util.workingWithCommand.CommandProcessor;

import java.util.ArrayDeque;

public class HistoryCommand extends AbstractClientCommand {
    private final ArrayDeque<String> queueOfCommands;
    private final CommandProcessor commandProcessor;

    public HistoryCommand(CommandProcessor commandProcessor, ArrayDeque<String> queueOfCommands) {
        super(new AbstractCommandBuilder()
                .withName("history")
                .withQuantityOfArgs(0)
                .withDescription("вывести последние 10 команд (без их аргументов)")
                .withGeneratesHumanBeing(false));
        this.queueOfCommands = queueOfCommands;
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeCommand(Request request) {
        return commandProcessor.history(request, queueOfCommands);
    }
}

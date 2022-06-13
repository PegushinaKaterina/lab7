package katya.server.util.workingWithCommand;

import katya.common.entites.HumanBeing;
import katya.common.util.Request;
import katya.common.util.Response;
import katya.common.util.ResponseBuilder;
import katya.server.DB.DBManager;
import katya.server.commands.clientCommands.AbstractClientCommand;
import katya.server.entites.CollectionManager;

import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;

public class CommandProcessor {
    private final DBManager dBManager;
    private final CollectionManager collectionManager;


    public CommandProcessor(DBManager dBManager, CollectionManager collectionManager) {
        this.dBManager = dBManager;
        this.collectionManager = collectionManager;
    }

    public Response add(Request request) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            HumanBeing humanBeing = request.getHumanBeingArgument();
            Long id = dBManager.add(humanBeing, request.getUsername());
            humanBeing.setId(id);
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(collectionManager.add(humanBeing) + "Его id = " + id));
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }

    public Response clear(Request request) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            List<Long> idList = dBManager.clear(request.getUsername());
            String result;
            if (idList.isEmpty()) {
                result = "Коллекция уже пуста";
            } else {
                idList.forEach(collectionManager::removeById);
                result = "Коллекция успешно очищена, id удаленных элементов: " + idList;
            }
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(result));
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }

    public Response help(Request request, HashMap<String, AbstractClientCommand> availableCommands) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());

            StringBuilder stringBuilder = new StringBuilder();
            availableCommands.values().forEach(command -> stringBuilder.append(command.toString()).append("\n"));
            String result = "Доступные команды:\n" + stringBuilder.substring(0, stringBuilder.length() - 1);
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(result));
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }

    public Response countByImpactSpeed(Request request) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(collectionManager
                            .countByImpactSpeed(request.getDoubleArgument())));
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }

    public Response history(Request request, ArrayDeque<String> queueOfCommands) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            String result;
            if (!queueOfCommands.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                queueOfCommands.forEach(command -> stringBuilder.append(command).append("\n"));
                result = "Последние 10 команд:\n" + stringBuilder.substring(0, stringBuilder.length() - 1);
            } else {
                result = "История команд пуста";
            }
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(result));
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }

    public Response info(Request request) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(collectionManager.info()));
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }

    public Response removeAllByMinutesOfWaiting(Request request) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            Integer minutesOfWaiting = request.getIntegerArgument();
            List<Long> idList = dBManager.removeAllByMinutesOfWaiting(minutesOfWaiting, request.getUsername());
            String result;
            if (collectionManager.getCollectionHumanBeing().isEmpty()) {
                result = "Коллекция пуста";
            } else if (idList.isEmpty()) {
                result = "Элементов со значением ВРЕМЯ ОЖИДАНИЯ = " + minutesOfWaiting + " не найдено";
            } else {
                idList.forEach(collectionManager::removeById);
                result = "Элементы со значением ВРЕМЯ ОЖИДАНИЯ = " + minutesOfWaiting
                        + " успешно удалены, id удаленных элементов: " + idList;
            }
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(result));
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }

    public Response removeById(Request request) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            dBManager.checkHumanBeingExistence(request.getLongArgument(), request.getUsername());
            dBManager.removeById(request.getLongArgument(), request.getUsername());
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(collectionManager.removeById(request.getLongArgument())));
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }

    public Response removeHead(Request request) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            List<Long> idList = dBManager.getIdsOfUsersElements(request.getUsername());
            Long id = collectionManager.getFirstId(idList);
            String result;
            if (id == null) {
                result = "Ваша коллекция пуста";
            } else {
                dBManager.removeById(id, request.getUsername());
                result = collectionManager.removeById(id);
            }
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(result));
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }

    public Response removeLower(Request request) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            HumanBeing humanBeing = request.getHumanBeingArgument();
            List<Long> idList = dBManager.getIdsOfUsersElements(request.getUsername());
            List<Long> idListLower = collectionManager.returnIdsOfLower(humanBeing, idList);
            String result;
            if (collectionManager.getCollectionHumanBeing().isEmpty()) {
                result = "Коллекция пуста";
            } else if (idListLower.isEmpty()) {
                result = "Элементов, меньших, чем заданный, не найдено";
            } else {
                //idListLower.forEach(id -> dBManager.removeById(id, request.getUsername()));
                dBManager.removeByIds(idListLower, request.getUsername());
                idListLower.forEach(collectionManager::removeById);
                result = "Элементы, меньшие, чем заданный, успешно удалены"
                        + ", id удаленных элементов: " + idList.toString();
            }
            //  List<Long> idList = collectionManager.getCollectionHumanBeing()
            //        .stream().filter(hb -> hb.compareTo(humanBeing) < 0).map(HumanBeing::getId).collect(Collectors.toList());
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(result));
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }

    public Response show(Request request) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            if (collectionManager.getCollectionHumanBeing().isEmpty()) {
                return new Response(new ResponseBuilder()
                        .withMessageToResponse("Коллекция пуста"));
            } else {
                List<Long> idList = dBManager.getIdsOfUsersElements(request.getUsername());
                return new Response(new ResponseBuilder()
                        .withUsersCollectionToResponse(collectionManager.getUsersElements(idList))
                        .withAlienCollectionToResponse(collectionManager.getAlienElements(idList)));
            }
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }

    public Response sumOfMinutesOfWaiting(Request request) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(collectionManager
                            .sumOfMinutesOfWaiting()));
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }


    public Response update(Request request) {
        try {
            dBManager.validateUser(request.getUsername(), request.getPassword());
            dBManager.checkHumanBeingExistence(request.getLongArgument(), request.getUsername());
            dBManager.update(request.getLongArgument(), request.getHumanBeingArgument(), request.getUsername());
            return new Response(new ResponseBuilder()
                    .withMessageToResponse(collectionManager.update(request.getLongArgument(), request.getHumanBeingArgument())));
        } catch (SQLException | IllegalArgumentException e) {
            return new Response(new ResponseBuilder().withMessageToResponse(e.getMessage()));
        }
    }
}

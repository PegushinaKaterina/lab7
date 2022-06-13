package katya.common.util;

import katya.common.entites.HumanBeing;

import java.io.Serializable;
import java.util.LinkedList;

public final class Response implements Serializable {

    private final String messageToResponse;
    private final HumanBeing humanBeingToResponse;
    private final LinkedList<HumanBeing> usersElementsToResponse;
    private final LinkedList<HumanBeing> alienElementsToResponse;
    private boolean successToResponse = true;

    public Response(ResponseBuilder responseBuilder) {
        this.messageToResponse = responseBuilder.getMessageToResponse();
        this.humanBeingToResponse = responseBuilder.getHumanBeingToResponse();
        this.usersElementsToResponse = responseBuilder.getUsersElementsToResponse();
        this.alienElementsToResponse = responseBuilder.getAlienElementsToResponse();
        this.successToResponse = responseBuilder.isSuccess();
    }

    public String getMessageToResponse() {
        return messageToResponse;
    }

    public HumanBeing getHumanBeingToResponse() {
        return humanBeingToResponse;
    }

    public LinkedList<HumanBeing> getUsersElementsToResponse() {
        return usersElementsToResponse;
    }

    public LinkedList<HumanBeing> getAlienElementsToResponse() {
        return alienElementsToResponse;
    }

    public boolean isSuccess() {
        return successToResponse;
    }

    public String getInfoAboutResponse() {
        return "Response contains: " + (messageToResponse == null ? "" : "message")
                + (humanBeingToResponse == null ? "" : ", humanBeing")
                + (usersElementsToResponse == null ? "" : ", collection");
    }

    @Override
    public String toString() {
        StringBuilder collection = new StringBuilder();
        if (!(usersElementsToResponse == null)) {
            if (usersElementsToResponse.isEmpty()) {
                collection.append("У вас нет элементов в коллекции");
            } else {
                for (HumanBeing humanBeing : usersElementsToResponse) {
                    collection.append(humanBeing.toString()).append("\n");
                }
            }
        }
        if (!(alienElementsToResponse == null)) {
            if (alienElementsToResponse.isEmpty()) {
                collection.append("В коллекции нет элементов других пользователей");
            } else {
                for (HumanBeing humanBeing : alienElementsToResponse) {
                    collection.append(humanBeing.toString()).append("\n");
                }
            }
            collection = new StringBuilder(collection.substring(0, collection.length() - 1));
            return String.valueOf(collection);
        }
        return (messageToResponse == null ? "" : messageToResponse)
                + (humanBeingToResponse == null ? "" : "\n" + humanBeingToResponse)
                + (usersElementsToResponse == null ? "" : "\n" + collection);
    }
}

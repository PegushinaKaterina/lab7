package katya.common.util;

import katya.common.entites.HumanBeing;

import java.util.LinkedList;

public class ResponseBuilder {
    private String messageToResponse;
    private HumanBeing humanBeingToResponse;
    private LinkedList<HumanBeing> usersElementsToResponse;
    private LinkedList<HumanBeing> alienElementsToResponse;
    private boolean successToResponse = true;

    public ResponseBuilder withMessageToResponse(String messageToResponse) {
        this.messageToResponse = messageToResponse;
        return this;
    }

    public ResponseBuilder withHumanBeingToResponse(HumanBeing humanBeingToResponse) {
        this.humanBeingToResponse = humanBeingToResponse;
        return this;
    }

    public ResponseBuilder withUsersCollectionToResponse(LinkedList<HumanBeing> usersElementsToResponse) {
        this.usersElementsToResponse = usersElementsToResponse;
        return this;
    }

    public ResponseBuilder withAlienCollectionToResponse(LinkedList<HumanBeing> alienElementsToResponse) {
        this.alienElementsToResponse = alienElementsToResponse;
        return this;
    }

    public ResponseBuilder withSuccessToResponse(boolean successToResponse) {
        this.successToResponse = successToResponse;
        return this;
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
}

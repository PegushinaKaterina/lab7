package katya.common.util;

import katya.common.entites.HumanBeing;
import katya.common.entites.RequestType;

import java.io.Serializable;
import java.time.LocalTime;

public final class Request implements Serializable {
    private final String commandName;
    private final Long longArgument;
    private final Integer integerArgument;
    private final Double doubleArgument;
    private final HumanBeing humanBeingArgument;
    private String clientInfo;
    private LocalTime currentTime;
    private String username;
    private String password;
    private RequestType requestTypeArgument;

    public Request(RequestBuilder requestBuilder) {
        this.commandName = requestBuilder.getCommandName();
        this.longArgument = requestBuilder.getLongArgument();
        this.integerArgument = requestBuilder.getIntegerArgument();
        this.doubleArgument = requestBuilder.getDoubleArgument();
        this.humanBeingArgument = requestBuilder.getHumanBeingArgument();
        this.username = requestBuilder.getUsernameArgument();
        this.password = requestBuilder.getPasswordArgument();
        this.requestTypeArgument = requestBuilder.getRequestTypeArgument();
    }

    public String getCommandName() {
        return commandName;
    }

    public Long getLongArgument() {
        return longArgument;
    }

    public Integer getIntegerArgument() {
        return integerArgument;
    }

    public Double getDoubleArgument() {
        return doubleArgument;
    }


    public HumanBeing getHumanBeingArgument() {
        return humanBeingArgument;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalTime currentTime) {
        this.currentTime = currentTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RequestType getRequestTypeArgument() {
        return requestTypeArgument;
    }

    public void setRequestTypeArgument(RequestType requestTypeArgument) {
        this.requestTypeArgument = requestTypeArgument;
    }

    @Override
    public String toString() {
        //что-то написать
        return null;
    }
}

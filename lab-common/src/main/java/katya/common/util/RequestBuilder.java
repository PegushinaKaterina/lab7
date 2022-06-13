package katya.common.util;

import katya.common.entites.HumanBeing;
import katya.common.entites.RequestType;

public class RequestBuilder {
    private String commandName;
    private Long longArgument;
    private Integer integerArgument;
    private Double doubleArgument;
    private HumanBeing humanBeingArgument;
    private String usernameArgument;
    private String passwordArgument;
    private RequestType requestTypeArgument;

    public RequestBuilder withName(String commandName) {
        this.commandName = commandName;
        return this;
    }

    public RequestBuilder withLongArgument(Long longArgument) {
        this.longArgument = longArgument;
        return this;
    }

    public RequestBuilder withIntegerArgument(Integer integerArgument) {
        this.integerArgument = integerArgument;
        return this;
    }

    public RequestBuilder withDoubleArgument(Double doubleArgument) {
        this.doubleArgument = doubleArgument;
        return this;
    }

    public RequestBuilder withHumanBeingArgument(HumanBeing humanBeingArgument) {
        this.humanBeingArgument = humanBeingArgument;
        return this;
    }

    public RequestBuilder withUsernameArgument(String usernameArgument) {
        this.usernameArgument = usernameArgument;
        return this;
    }

    public RequestBuilder withPasswordArgument(String passwordArgument) {
        this.passwordArgument = passwordArgument;
        return this;
    }

    public RequestBuilder withRequestTypeArgument(RequestType requestTypeArgument) {
        this.requestTypeArgument = requestTypeArgument;
        return this;
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

    public String getUsernameArgument() {
        return usernameArgument;
    }

    public String getPasswordArgument() {
        return passwordArgument;
    }

    public RequestType getRequestTypeArgument() {
        return requestTypeArgument;
    }


}

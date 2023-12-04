package integration.constants;

public enum ResponseMessage {
    LOGGED_IN("logged in user session:"),
    USER_NOT_FOUND("User not found"),
    OK("ok");

    private String value;

    ResponseMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

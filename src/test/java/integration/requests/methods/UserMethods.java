package integration.requests.methods;

public enum UserMethods {
    CREATE_WITH_ARRAY("/user/createWithArray"),
    CREATE_WITH_LIST("/user/createWithList"),
    GET_USER_BY_USERNAME("/user/%s"),
    LOGIN_USER("/user/login"),
    LOGOUT_USER("/user/logout"),
    UPDATE_USER("/user/%s"),
    DELETE_USER("/user/%s"),
    CREATE_USER("/user");

    private final String value;

    public String getValue() {
        return value;
    }

    UserMethods(String value) {
        this.value = value;
    }
}

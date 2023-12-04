package rest.enums;

public enum ContentType {

    JSON("application/json"),
    XML("application/xml"),
    TEXT("text/plain"),
    FORM_DATA("multipart/form-data"),
    FORM_URLENCODED("application/x-www-form-urlencoded"),
    IMAGE("image/jpeg");

    public static final String KEY = "Content-Type";
    private String value;

    ContentType(String type) {
        this.value = type;
    }

    public String getValue() {
        return value;
    }
}

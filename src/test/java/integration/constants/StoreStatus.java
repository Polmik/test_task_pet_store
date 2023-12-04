package integration.constants;

public enum StoreStatus {
    placed("placed"),
    approved("approved"),
    delivered ("delivered");

    private String value;

    StoreStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package integration.requests.methods;

public enum StoreMethods {
    PLACE_ORDER("/store/order"),
    FIND_ORDER_BY_ID("/store/order/%s"),
    DELETE_ORDER_BY_ID("/store/order/%s"),
    GET_INVENTORIES("/store/inventory");

    private final String value;

    public String getValue() {
        return value;
    }

    StoreMethods(String value) {
        this.value = value;
    }
}

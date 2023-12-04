package integration.constants;

public enum PetStatus {
    available("available"),
    pending("pending"),
    sold("sold");

    private String value;

    PetStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

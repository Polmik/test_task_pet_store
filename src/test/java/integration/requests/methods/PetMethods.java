package integration.requests.methods;

public enum PetMethods {
    UPLOAD_IMAGE("/pet/%s/uploadImage"),
    ADD_NEW_PET("/pet"),
    UPDATE_PET("/pet"),
    FIND_PET_BY_STATUS("/pet/findByStatus"),
    FIND_PET_BY_ID("/pet/%s"),
    UPDATE_PET_BY_ID("/pet/%s"),
    DELETE_PET_BY_ID("/pet/%s");

    private final String value;

    public String getValue() {
        return value;
    }

    PetMethods(String value) {
        this.value = value;
    }
}

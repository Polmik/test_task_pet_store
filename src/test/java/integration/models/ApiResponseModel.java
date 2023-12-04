package integration.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponseModel {
    @JsonProperty("code")
    public int code;
    @JsonProperty("type")
    public String type;
    @JsonProperty("message")
    public String message;
}

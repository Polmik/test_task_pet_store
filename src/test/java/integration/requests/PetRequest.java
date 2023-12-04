package integration.requests;

import integration.constants.PetStatus;
import integration.helpers.ApiHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import integration.models.PetModel;
import integration.requests.methods.PetMethods;
import rest.enums.ContentType;
import rest.enums.HttpRequestType;
import utils.JsonUtils;
import utils.StringUtils;

import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class PetRequest extends BaseApi {

    @Step("Creating a pet {petModel.name}")
    public static Response createPet(PetModel petModel) {
        return ApiHelper.sendRequest(HttpRequestType.POST,
                StringUtils.concat(baseURI, PetMethods.ADD_NEW_PET.getValue()),
                JsonUtils.getJsonStringFromObject(petModel),
                getAuthorizationHeader(),
                ContentType.JSON);
    }

    @Step("Updating the pet {petModel.name}")
    public static Response updatePet(PetModel petModel) {
        return ApiHelper.sendRequest(HttpRequestType.PUT,
                StringUtils.concat(baseURI, PetMethods.UPDATE_PET.getValue()),
                JsonUtils.getJsonStringFromObject(petModel),
                getAuthorizationHeader(),
                ContentType.JSON);
    }

    public static Response getPetById(int id) {
        return getPetById(BigInteger.valueOf(id));
    }

    @Step("Getting the pet by ID {id}")
    public static Response getPetById(BigInteger id) {
        return ApiHelper.sendRequest(HttpRequestType.GET,
                String.format(StringUtils.concat(baseURI, PetMethods.FIND_PET_BY_ID.getValue()), id),
                null,
                getAuthorizationHeader());
    }

    @Step("Getting pets by status {status}")
    public static Response getPetsByStatus(String status) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("status", status);
        return ApiHelper.sendRequest(HttpRequestType.GET,
                String.format(StringUtils.concat(baseURI, PetMethods.FIND_PET_BY_STATUS.getValue()), status),
                null,
                getAuthorizationHeader(),
                ContentType.JSON,
                parameters);
    }

    public static Response getPetsByStatus(PetStatus status) {
        return getPetsByStatus(status.getValue());
    }

    @Step("Deleting the pet by ID {petId}")
    public static Response deletePetById(BigInteger petId) {
        Map<String, String> parameters = new HashMap<>();
        //parameters.put("api_key", "");
        return ApiHelper.sendRequest(HttpRequestType.DELETE,
                String.format(StringUtils.concat(baseURI, PetMethods.DELETE_PET_BY_ID.getValue()), petId),
                null,
                getAuthorizationHeader());
    }

    @Step("Updating the pet by ID {petId}")
    public static Response updatePetById(BigInteger petId, String name, PetStatus petStatus) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("status", petStatus.getValue());
        return ApiHelper.sendRequest(HttpRequestType.POST,
                String.format(StringUtils.concat(baseURI, PetMethods.UPDATE_PET_BY_ID.getValue()), petId),
                null,
                getAuthorizationHeader(),
                ContentType.FORM_URLENCODED,
                parameters);
    }

    @Step("Uploading Image to the pet by ID {petId}")
    public static Response uploadImage(BigInteger petId, String additionalMetadata, File file) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("additionalMetadata", additionalMetadata);
        parameters.put("file", file);
        return ApiHelper.sendRequest(HttpRequestType.POST,
                String.format(StringUtils.concat(baseURI, PetMethods.UPLOAD_IMAGE.getValue()), petId),
                null,
                getAuthorizationHeader(),
                ContentType.FORM_DATA,
                parameters);
    }
}

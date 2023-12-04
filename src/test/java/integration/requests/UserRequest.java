package integration.requests;

import integration.helpers.ApiHelper;
import integration.requests.methods.UserMethods;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import integration.models.UserModel;
import rest.enums.ContentType;
import rest.enums.HttpRequestType;
import utils.JsonUtils;
import utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRequest extends BaseApi{
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    @Step("Getting user '{username}'")
    public static Response getUserByUsername(String username) {
        return ApiHelper.sendRequest(HttpRequestType.GET,
                String.format(StringUtils.concat(baseURI, UserMethods.GET_USER_BY_USERNAME.getValue()), username),
                null,
                getAuthorizationHeader());
    }

    @Step("Logging in")
    public static Response login(String username, String password) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(USERNAME_KEY, username);
        parameters.put(PASSWORD_KEY, password);
        return ApiHelper.sendRequest(HttpRequestType.GET,
                StringUtils.concat(baseURI, UserMethods.LOGIN_USER.getValue()),
                null,
                getAuthorizationHeader(),
                ContentType.JSON,
                parameters);
    }

    @Step("Logging out")
    public static Response logout() {
        return ApiHelper.sendRequest(HttpRequestType.GET,
                StringUtils.concat(baseURI, UserMethods.LOGOUT_USER.getValue()),
                null,
                getAuthorizationHeader());
    }

    @Step("Creating user {userModel.username}")
    public static Response createUser(UserModel userModel) {
        return ApiHelper.sendRequest(HttpRequestType.POST,
                StringUtils.concat(baseURI, UserMethods.CREATE_USER.getValue()),
                JsonUtils.getJsonStringFromObject(userModel),
                getAuthorizationHeader(),
                ContentType.JSON);
    }

    @Step("Creating user with List")
    public static Response createWithList(List<UserModel> userModels) {
        return ApiHelper.sendRequest(HttpRequestType.POST,
                StringUtils.concat(baseURI, UserMethods.CREATE_WITH_LIST.getValue()),
                JsonUtils.getJsonStringFromObject(userModels),
                getAuthorizationHeader(),
                ContentType.JSON);
    }

    @Step("Creating user with Array")
    public static Response createWithArray(List<UserModel> userModels) {
        return ApiHelper.sendRequest(HttpRequestType.POST,
                StringUtils.concat(baseURI, UserMethods.CREATE_WITH_ARRAY.getValue()),
                JsonUtils.getJsonStringFromObject(userModels),
                getAuthorizationHeader(),
                ContentType.JSON);
    }

    @Step("Updating user '{username}'")
    public static Response updateUser(String username, UserModel userModel) {
        return ApiHelper.sendRequest(HttpRequestType.PUT,
                String.format(StringUtils.concat(baseURI, UserMethods.UPDATE_USER.getValue()), username),
                JsonUtils.getJsonStringFromObject(userModel),
                getAuthorizationHeader(),
                ContentType.JSON);
    }

    @Step("Deleting user '{username}'")
    public static Response deleteUser(String username) {
        return ApiHelper.sendRequest(HttpRequestType.DELETE,
                String.format(StringUtils.concat(baseURI, UserMethods.DELETE_USER.getValue()), username),
                null,
                getAuthorizationHeader());
    }
}

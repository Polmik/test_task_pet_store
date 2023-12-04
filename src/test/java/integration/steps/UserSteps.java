package integration.steps;

import integration.models.UserModel;
import integration.requests.UserRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;

public class UserSteps extends BaseSteps {

    public static void createUser(UserModel model) {
        Response response = UserRequest.createUser(model);
        verifyStatusCode(response, HttpStatus.SC_OK);
    }

    public static UserModel createAndGetUser(UserModel model) {
        createUser(model);
        return getAndValidateUser(model);
    }

    public static UserModel getUser(UserModel model) {
        return getUser(model.username);
    }

    @Step("Creating and getting the User")
    public static UserModel getAndValidateUser(UserModel model) {
        UserModel createdModel = getUser(model.username);

        Assert.assertEquals(createdModel.id, model.id, "Id is not as expected");
        Assert.assertEquals(createdModel.username, model.username, "Username is not as expected");
        Assert.assertEquals(createdModel.firstName, model.firstName, "FirstName is not as expected");
        Assert.assertEquals(createdModel.lastName, model.lastName, "LastName is not as expected");
        Assert.assertEquals(createdModel.email, model.email, "Email is not as expected");
        Assert.assertEquals(createdModel.password, model.password, "Password is not as expected");
        Assert.assertEquals(createdModel.phone, model.phone, "Phone is not as expected");
        Assert.assertEquals(createdModel.userStatus, model.userStatus, "UserStatus is not as expected");

        return createdModel;
    }

    public static UserModel getUser(String username) {
        Response response = UserRequest.getUserByUsername(username);
        verifyStatusCode(response, HttpStatus.SC_OK);
        return response.as(UserModel.class);
    }
}

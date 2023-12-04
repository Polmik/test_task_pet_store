package tests.users;

import integration.constants.ResponseMessage;
import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.UserModel;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import integration.requests.UserRequest;
import integration.steps.BaseSteps;
import integration.steps.UserSteps;

import java.util.ArrayList;
import java.util.List;

public class CreateUserTests extends UserBaseTest {

    @DataProvider(name = "countUsers")
    public Object[][] dataProvFunc(){
        return new Object[][]{
                {0}, {1}, {2}, {3}
        };
    }

    @Test(testName = "CreateUser")
    public void testCreateUser() {
        UserModel model = getRandomUser();

        CustomLogger.logStep("Creating the User");
        Response createResponse = UserRequest.createUser(model);
        BaseSteps.verifyStatusAndApiResponse(createResponse, HttpStatus.SC_OK, model.id.toString());

        CustomLogger.logStep(String.format("Getting the created User [%s]", model.username));
        Response getResponse = UserRequest.getUserByUsername(model.username);
        BaseSteps.verifyStatusCode(getResponse, HttpStatus.SC_OK);

        CustomLogger.logStep(String.format("Validating the created User [%s]", model.username));
        UserModel createdModel = getResponse.as(UserModel.class);
        Assert.assertEquals(createdModel.id, model.id, "ID is not as expected");
        Assert.assertEquals(createdModel.username, model.username, "Username is not as expected");
        Assert.assertEquals(createdModel.firstName, model.firstName, "FirstName is not as expected");
        Assert.assertEquals(createdModel.lastName, model.lastName, "LastName is not as expected");
        Assert.assertEquals(createdModel.email, model.email, "Email is not as expected");
        Assert.assertEquals(createdModel.password, model.password, "Password is not as expected");
        Assert.assertEquals(createdModel.phone, model.phone, "Phone is not as expected");
        Assert.assertEquals(createdModel.userStatus, model.userStatus, "UserStatus is not as expected");
    }

    @Test(testName = "CreateUserWithList", dataProvider = "countUsers")
    public void testCreateUserWithList(int count) {
        CustomLogger.logStep(String.format("Preparing the %s UserModels", count));
        List<UserModel> userModels = new ArrayList<>();
        while (count-- > 0) {
            userModels.add(getRandomUser());
        }

        CustomLogger.logStep("Creating the User With List");
        Response createResponse = UserRequest.createWithList(userModels);
        BaseSteps.verifyStatusAndApiResponse(createResponse, HttpStatus.SC_OK, ResponseMessage.OK.getValue());

        CustomLogger.logStep("Validating the created Users");
        for (UserModel userModel : userModels) {
            UserSteps.getAndValidateUser(userModel);
        }
    }

    @Test(testName = "CreateUserWithArray", dataProvider = "countUsers")
    public void testCreateUserWithArray(int count) {
        CustomLogger.logStep(String.format("Preparing the %s UserModel", count));
        List<UserModel> userModels = new ArrayList<>();
        while (count-- > 0) {
            userModels.add(getRandomUser());
        }

        CustomLogger.logStep("Creating the User");
        Response createResponse = UserRequest.createWithArray(userModels);
        BaseSteps.verifyStatusAndApiResponse(createResponse, HttpStatus.SC_OK, ResponseMessage.OK.getValue());

        CustomLogger.logStep("Validating the created Users");
        for (UserModel userModel : userModels) {
            UserSteps.getAndValidateUser(userModel);
        }
    }

    @Test(testName = "CreateUserAndLogin")
    public void testCreateUserAndLogin() {
        UserModel model = getRandomUser();

        CustomLogger.logStep("Creating the User");
        UserModel createdUser = UserSteps.createAndGetUser(model);

        CustomLogger.logStep("Logging Out from current session");
        Response logOutResponse = UserRequest.logout();
        BaseSteps.verifyStatusAndApiResponse(logOutResponse, HttpStatus.SC_OK, ResponseMessage.OK.getValue());

        CustomLogger.logStep("Logging In using the created user");
        Response loginResponse = UserRequest.login(createdUser.username, createdUser.password);
        BaseSteps.verifyStatusCode(loginResponse, HttpStatus.SC_OK);
        BaseSteps.verifyPartOfApiResponse(loginResponse, ResponseMessage.LOGGED_IN.getValue());
    }

}

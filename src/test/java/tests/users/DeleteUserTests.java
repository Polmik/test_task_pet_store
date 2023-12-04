package tests.users;

import integration.constants.ResponseMessage;
import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.UserModel;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import integration.requests.UserRequest;
import integration.steps.BaseSteps;
import integration.steps.UserSteps;

public class DeleteUserTests extends UserBaseTest {

    @Test(testName = "DeleteAnExistingUser")
    public void testDeleteAnExistingUser() {
        CustomLogger.logStep("Creating and getting the User");
        UserModel createdModel = UserSteps.createAndGetUser(getRandomUser());

        CustomLogger.logStep("Deleting the User");
        Response deleteResponse = UserRequest.deleteUser(createdModel.username);
        BaseSteps.verifyStatusAndApiResponse(deleteResponse, HttpStatus.SC_OK, createdModel.username);

        CustomLogger.logStep("Verifying that the user has been deleted");
        Response getResponse = UserRequest.getUserByUsername(createdModel.username);
        BaseSteps.verifyStatusAndApiResponse(getResponse, HttpStatus.SC_NOT_FOUND, ResponseMessage.USER_NOT_FOUND.getValue());
    }

    @Test(testName = "DeleteNonExistingUser")
    public void testDeleteNonExistingUser() {
        CustomLogger.logStep("Deleting the User");
        Response deleteResponse = UserRequest.deleteUser("IncorrectName123123qw");
        BaseSteps.verifyStatusCode(deleteResponse, HttpStatus.SC_NOT_FOUND);
    }
}

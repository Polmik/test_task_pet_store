package tests.users;

import integration.constants.ResponseMessage;
import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import integration.requests.UserRequest;
import integration.steps.BaseSteps;

public class GetUserTests extends UserBaseTest {

    @Test(testName = "GetNonExistentUser")
    public void testGetNonExistentUser() {
        CustomLogger.logStep("Getting user by incorrect ID");
        Response response = UserRequest.getUserByUsername("IncorrectName12345");
        BaseSteps.verifyStatusAndApiResponse(response, HttpStatus.SC_NOT_FOUND, ResponseMessage.USER_NOT_FOUND.getValue());
    }

}

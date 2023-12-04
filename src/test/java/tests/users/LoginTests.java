package tests.users;

import integration.constants.ResponseMessage;
import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import integration.properties.ConfigProperties;
import integration.requests.UserRequest;
import integration.steps.BaseSteps;
import tests.BaseTest;

public class LoginTests extends BaseTest {

    @AfterTest
    public synchronized void teardownTest() {
        Response response = UserRequest.logout();
        BaseSteps.verifyStatusCode(response, HttpStatus.SC_OK);
    }

    @Test(testName = "LogIn")
    public void testLogIn() {
        CustomLogger.logStep("Logging In");
        Response response = UserRequest.login(ConfigProperties.getUsername(), ConfigProperties.getPassword());
        BaseSteps.verifyStatusCode(response, HttpStatus.SC_OK);
        BaseSteps.verifyPartOfApiResponse(response, ResponseMessage.LOGGED_IN.getValue());
    }
}

package tests.users;

import integration.constants.ResponseMessage;
import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import integration.properties.ConfigProperties;
import integration.requests.UserRequest;
import integration.steps.BaseSteps;
import tests.BaseTest;

public class LogoutTests extends BaseTest {

    @Test(testName = "LogOut")
    public void testLogOut() {
        CustomLogger.logStep("Logging In");
        Response responseLogin = UserRequest.login(ConfigProperties.getUsername(), ConfigProperties.getPassword());
        BaseSteps.verifyStatusCode(responseLogin, HttpStatus.SC_OK);
        BaseSteps.verifyPartOfApiResponse(responseLogin, ResponseMessage.LOGGED_IN.getValue());

        CustomLogger.logStep("Logging Out");
        Response responseLogout = UserRequest.logout();
        BaseSteps.verifyStatusAndApiResponse(responseLogout, HttpStatus.SC_OK, ResponseMessage.OK.getValue());
    }
}

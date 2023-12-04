package tests;

import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import integration.properties.ConfigProperties;
import integration.requests.UserRequest;
import integration.steps.BaseSteps;

public class LoginBaseTest extends BaseTest {

    @BeforeTest
    public synchronized void setUpLogin() {
        CustomLogger.logAllureStep("Logging in", () -> {
            Response response = UserRequest.login(ConfigProperties.getUsername(), ConfigProperties.getPassword());
            BaseSteps.verifyStatusCode(response, HttpStatus.SC_OK);
        });
    }

    @AfterTest
    public synchronized void teardownLogout() {
        CustomLogger.logAllureStep("Logging out", () -> {
            Response response = UserRequest.logout();
            BaseSteps.verifyStatusCode(response, HttpStatus.SC_OK);
        });
    }

}

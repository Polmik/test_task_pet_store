package tests.store;

import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import integration.requests.StoreRequest;
import integration.steps.BaseSteps;

import java.math.BigInteger;

public class GetStoreTests extends StoreBaseTest {

    @Test(testName = "GetNonExistentStore")
    public void testDeleteNotExistentStore() {
        CustomLogger.logStep("Getting the store");
        Response getResponse = StoreRequest.getStoreById(BigInteger.valueOf(-1));
        BaseSteps.verifyStatusCode(getResponse, HttpStatus.SC_NOT_FOUND);
    }
}

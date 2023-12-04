package tests.store;

import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import integration.requests.StoreRequest;
import integration.steps.BaseSteps;
import java.util.Map;

public class GetInventoryTests extends StoreBaseTest {

    @Test(testName = "GetInventory")
    public void testGetInventory() {
        CustomLogger.logStep("Getting inventories");
        Response response = StoreRequest.getInventories();
        BaseSteps.verifyStatusCode(response, HttpStatus.SC_OK);

        CustomLogger.logStep("Checking inventories");
        Map<Object, Object> values = response.as(Map.class);
        for (Map.Entry<Object, Object> entry: values.entrySet()) {
            Assert.assertTrue(entry.getKey() instanceof String, String.format("Key %s has incorrect type", entry.getKey()));
            Assert.assertTrue(entry.getValue() instanceof Integer, String.format("Value %s of key %s has incorrect type", entry.getValue(), entry.getKey()));
        }
    }
}

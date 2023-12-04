package tests.store;

import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.PetModel;
import integration.models.StoreModel;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import integration.requests.StoreRequest;
import integration.steps.BaseSteps;
import integration.steps.PetSteps;

public class PlaceStoreTests extends StoreBaseTest {

    @Test(testName = "PlaceOrder")
    public void testPlaceOrder() {
        PetModel petModel = PetSteps.createPet(getRandomPet());
        StoreModel storeModel = getRandomStore(petModel.id);

        CustomLogger.logStep("Creating a store");
        Response createResponse = StoreRequest.placeStore(storeModel);
        BaseSteps.verifyStatusCode(createResponse, HttpStatus.SC_OK);
        StoreModel createdStore = createResponse.as(StoreModel.class);

        CustomLogger.logStep("Getting the store");
        Response getResponse = StoreRequest.getStoreById(createdStore.id);
        BaseSteps.verifyStatusCode(getResponse, HttpStatus.SC_OK);
        StoreModel getStore = getResponse.as(StoreModel.class);

        CustomLogger.logStep("Validating the created store");
        Assert.assertEquals(getStore, createdStore);
    }
}

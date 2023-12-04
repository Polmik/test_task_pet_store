package tests.store;

import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.PetModel;
import integration.models.StoreModel;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import integration.requests.StoreRequest;
import integration.steps.BaseSteps;
import integration.steps.PetSteps;

import java.math.BigInteger;

public class DeleteStoreTests extends StoreBaseTest {

    @Test(testName = "DeleteStoreOrder")
    public void testDeleteStoreOrder() {
        PetModel petModel = PetSteps.createPet(getRandomPet());
        StoreModel storeModel = getRandomStore(petModel.id);

        CustomLogger.logStep("Creating a store");
        Response createResponse = StoreRequest.placeStore(storeModel);
        BaseSteps.verifyStatusCode(createResponse, HttpStatus.SC_OK);
        StoreModel createdStore = createResponse.as(StoreModel.class);

        CustomLogger.logStep("Deleting the store");
        Response deleteResponse = StoreRequest.deleteStoreById(createdStore.id);
        BaseSteps.verifyStatusCode(deleteResponse, HttpStatus.SC_OK);

        CustomLogger.logStep("Getting the store");
        Response getResponse = StoreRequest.getStoreById(createdStore.id);
        BaseSteps.verifyStatusCode(getResponse, HttpStatus.SC_NOT_FOUND);
    }

    @Test(testName = "DeleteStoreOrder")
    public void testDeleteNotExistentStore() {
        CustomLogger.logStep("Deleting the store");
        Response deleteResponse = StoreRequest.deleteStoreById(BigInteger.valueOf(-1));
        BaseSteps.verifyStatusCode(deleteResponse, HttpStatus.SC_NOT_FOUND);
    }
}

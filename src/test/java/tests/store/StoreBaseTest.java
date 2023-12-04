package tests.store;

import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.StoreModel;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterTest;
import integration.requests.StoreRequest;
import integration.steps.BaseSteps;
import tests.pets.PetBaseTest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class StoreBaseTest extends PetBaseTest {
    private final List<StoreModel> storesToDelete = new ArrayList<>();

    @AfterTest
    public synchronized void teardownDeleteStores() {
        CustomLogger.logAllureStep(String.format("Deleting stores: %s", storesToDelete.size()), () -> {
            for (StoreModel storeModel : storesToDelete) {
                Response response = StoreRequest.getStoreById(storeModel.id);
                if (response.getStatusCode() == HttpStatus.SC_OK) {
                    try {
                        response = StoreRequest.deleteStoreById(storeModel.id);
                        BaseSteps.verifyStatusCode(response, HttpStatus.SC_OK);
                    } catch (Exception e) {
                        CustomLogger.warn(String.format("Store '%s' has not been deleted", storeModel.id));
                    }
                }
            }
        });
        storesToDelete.clear();
    }

    protected StoreModel getRandomStore(BigInteger petId) {
        StoreModel storeModel = StoreModel.getRandomStore(petId);
        storesToDelete.add(storeModel);
        return storeModel;
    }
}

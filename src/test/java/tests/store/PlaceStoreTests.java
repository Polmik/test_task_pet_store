package tests.store;

import integration.constants.StoreStatus;
import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.PetModel;
import integration.models.StoreModel;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import integration.requests.StoreRequest;
import integration.steps.BaseSteps;
import integration.steps.PetSteps;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class PlaceStoreTests extends StoreBaseTest {

    @DataProvider(name = "daysOffset")
    public Object[][] dataProvFuncDaysOffset(){
        return new Object[][]{
                {-5},
                {0},
                {5},
        };
    }

    @DataProvider(name = "storeStatuses")
    public Object[][] dataProvFuncStatuses(){
        return new Object[][]{
                {StoreStatus.approved},
                {StoreStatus.placed},
                {StoreStatus.delivered}
        };
    }

    private String getDateWithOffset(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime().toInstant().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    @Test(testName = "PlaceOrderWithDifferentDays", dataProvider = "daysOffset")
    public void testPlaceOrderWithDifferentDays(int daysOffset) {
        PetModel petModel = PetSteps.createPet(getRandomPet());
        StoreModel storeModel = getRandomStore(petModel.id);
        storeModel.shipDate = getDateWithOffset(daysOffset);

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

    @Test(testName = "PlaceOrderWithDifferentStatuses", dataProvider = "storeStatuses")
    public void testPlaceOrderWithPlaceOrderWithDifferentStatuses(StoreStatus status) {
        PetModel petModel = PetSteps.createPet(getRandomPet());
        StoreModel storeModel = getRandomStore(petModel.id);
        storeModel.status = status;

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

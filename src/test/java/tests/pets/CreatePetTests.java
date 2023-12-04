package tests.pets;

import integration.constants.PetStatus;
import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.PetModel;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import integration.requests.PetRequest;
import integration.steps.BaseSteps;

public class CreatePetTests extends PetBaseTest {

    @DataProvider(name = "petStatuses")
    public Object[][] dataProvFunc(){
        return new Object[][]{
                {PetStatus.available},
                {PetStatus.sold},
                {PetStatus.pending}
        };
    }

    @Test(testName = "CreatePetWithDifferentStatuses", dataProvider = "petStatuses")
    public void testCreatePetWithDifferentStatuses(PetStatus petStatus) {
        PetModel petModel = getRandomPet();
        petModel.status = petStatus;

        CustomLogger.logStep("Creating the Pet");
        Response createPetResponse = PetRequest.createPet(petModel);
        BaseSteps.verifyStatusCode(createPetResponse, HttpStatus.SC_OK);
        Assert.assertEquals(createPetResponse.as(PetModel.class), petModel, "Created model is not as expected");

        CustomLogger.logStep("Getting the Pet");
        Response getPetResponse = PetRequest.getPetById(petModel.id);
        BaseSteps.verifyStatusCode(getPetResponse, HttpStatus.SC_OK);
        Assert.assertEquals(getPetResponse.as(PetModel.class), petModel, "Created model is not as expected");
    }

}

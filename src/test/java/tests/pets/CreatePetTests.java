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
import java.util.ArrayList;

public class CreatePetTests extends PetBaseTest {

    @DataProvider(name = "petStatuses")
    public Object[][] dataProvFuncStatuses(){
        return new Object[][]{
                {PetStatus.available},
                {PetStatus.sold},
                {PetStatus.pending}
        };
    }

    @DataProvider(name = "missingAttr")
    public Object[][] dataProvFuncMissingAttrs(){
        return new Object[][]{
                {"name"},
                {"category"},
                {"photoUrls"},
                {"tags"},
                {"status"},
        };
    }

    private void updateModel(PetModel model, String attr) {
        switch (attr) {
            case "name": model.name = null; break;
            case "category": model.category = null; break;
            case "photoUrls": model.photoUrls = new ArrayList<>(); break;
            case "tags": model.tags = new ArrayList<>(); break;
            case "status": model.status = null; break;
            default: throw new IllegalArgumentException("Incorrect attr to update");
        }
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

    @Test(testName = "CreatePetWithoutAttr", dataProvider = "missingAttr")
    public void testCreatePetWithoutAttr(String missingAttr) {
        PetModel petModel = getRandomPet();
        updateModel(petModel, missingAttr);

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

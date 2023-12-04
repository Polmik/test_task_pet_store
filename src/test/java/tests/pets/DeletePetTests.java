package tests.pets;

import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.PetModel;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import integration.requests.PetRequest;
import integration.steps.BaseSteps;

import java.math.BigInteger;

public class DeletePetTests extends PetBaseTest {


    @Test(testName = "DeletePet")
    public void testDeletePet() {
        PetModel petModel = getRandomPet();

        CustomLogger.logStep("Creating the Pet");
        Response createPetResponse = PetRequest.createPet(petModel);
        BaseSteps.verifyStatusCode(createPetResponse, HttpStatus.SC_OK);
        Assert.assertEquals(createPetResponse.as(PetModel.class), petModel, "Created model is not as expected");

        CustomLogger.logStep("Deleting the Pet");
        Response deletePetResponse = PetRequest.deletePetById(petModel.id);
        BaseSteps.verifyStatusCode(deletePetResponse, HttpStatus.SC_OK);

        CustomLogger.logStep("Getting the Pet");
        Response getPetResponse = PetRequest.getPetById(petModel.id);
        BaseSteps.verifyStatusCode(getPetResponse, HttpStatus.SC_NOT_FOUND);
    }

    @Test(testName = "DeleteNonExistentPet")
    public void testDeleteNonExistentPet() {
        CustomLogger.logStep("Deleting the non-existent Pet");
        Response deletePetResponse = PetRequest.deletePetById(BigInteger.valueOf(-1));
        BaseSteps.verifyStatusCode(deletePetResponse, HttpStatus.SC_NOT_FOUND);
    }
}

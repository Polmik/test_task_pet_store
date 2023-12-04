package tests.pets;

import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.PetModel;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import integration.requests.PetRequest;
import integration.steps.BaseSteps;
import tests.LoginBaseTest;


public class GetPetTests extends LoginBaseTest {

    @Test(testName = "testGetNonExistentPetById")
    public void testGetNonExistentPetById() {
        CustomLogger.logStep("Getting the Pet by incorrect ID");
        Response response = PetRequest.getPetById(-1);
        BaseSteps.verifyStatusCode(response, HttpStatus.SC_NOT_FOUND);
    }

    @Test(testName = "GetPetUsingIncorrectStatus")
    public void testGetPetUsingIncorrectStatus() {
        CustomLogger.logStep("Getting Pets by incorrect status");
        Response response = PetRequest.getPetsByStatus("IncorrectStatus");
        BaseSteps.verifyStatusCode(response, HttpStatus.SC_OK);
        Assert.assertEquals(response.as(PetModel[].class).length, 0);
    }
}

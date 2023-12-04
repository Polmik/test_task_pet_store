package tests.pets;

import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.PetModel;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import integration.requests.PetRequest;
import integration.steps.BaseSteps;
import tests.LoginBaseTest;

import java.util.ArrayList;
import java.util.List;

public class PetBaseTest extends LoginBaseTest {
    protected final List<PetModel> petsToDelete = new ArrayList<>();

    @AfterMethod
    public synchronized void teardownDeletePets() {
        CustomLogger.logAllureStep(String.format("Deleting pets: %s", petsToDelete.size()), () -> {
            for (PetModel petModel : petsToDelete) {
                Response response = PetRequest.getPetById(petModel.id);
                if (response.getStatusCode() == HttpStatus.SC_OK) {
                    try {
                        response = PetRequest.deletePetById(petModel.id);
                        BaseSteps.verifyStatusCode(response, HttpStatus.SC_OK);
                    } catch (Exception e) {
                        CustomLogger.warn(String.format("Pet '%s' has not been deleted", petModel.id));
                    }
                }
            }
            petsToDelete.clear();
        });
    }

    protected PetModel getRandomPet() {
        CustomLogger.logStep("Preparing the PetModel");
        PetModel pet = PetModel.getRandomPet();
        petsToDelete.add(pet);
        return pet;
    }
}

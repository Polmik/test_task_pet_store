package integration.steps;

import integration.constants.PetStatus;
import integration.models.PetModel;
import integration.requests.PetRequest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class PetSteps extends BaseSteps {

    public static PetModel getPetById(BigInteger id) {
        Response response = PetRequest.getPetById(id);
        verifyStatusCode(response, HttpStatus.SC_OK);
        return response.as(PetModel.class);
    }

    public static List<PetModel> getPetsByStatus(PetStatus petStatus) {
        Response response = PetRequest.getPetsByStatus(petStatus);
        verifyStatusCode(response, HttpStatus.SC_OK);
        return Arrays.stream(response.as(PetModel[].class)).toList();
    }

    public static PetModel getPetByStatusAndId(PetStatus petStatus, BigInteger id) {
        List<PetModel> pets = getPetsByStatus(petStatus);
        return pets.stream().filter(pet -> pet.id.equals(id)).findFirst().orElse(null);
    }

    public static PetModel createPet(PetModel petModel) {
        Response response = PetRequest.createPet(petModel);
        verifyStatusCode(response, HttpStatus.SC_OK);
        return response.as(PetModel.class);
    }
}

package tests.pets;

import integration.constants.PetStatus;
import integration.constants.PetUrls;
import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.CategoryModel;
import integration.models.PetModel;
import integration.models.TagModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import integration.requests.PetRequest;
import integration.steps.BaseSteps;
import utils.EnumUtils;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class UpdatePetTests extends PetBaseTest {

    @DataProvider(name = "valuesToUpdate")
    public Object[][] dataProvFunc(){
        return new Object[][]{
                {"id", new BigInteger(RandomStringUtils.random(8, false, true))},
                {"name", RandomStringUtils.random(10, true, false)},
                {"category", CategoryModel.getRandomCategory()},
                {"photoUrls", Arrays.stream((new String[] {EnumUtils.randomValue(PetUrls.class).getValue()})).toList()},
                {"tags", Arrays.stream((new TagModel[] {TagModel.getRandomTag()})).toList()},
                {"status", EnumUtils.randomValue(PetStatus.class)},
        };
    }

    private void updateModel(PetModel model, String attr, Object value) {
        switch (attr) {
            case "id": model.id = (BigInteger) value; break;
            case "name": model.name = (String) value; break;
            case "category": model.category = (CategoryModel) value; break;
            case "photoUrls": model.photoUrls = (List<String>) value; break;
            case "tags": model.tags = (List<TagModel>) value; break;
            case "status": model.status = (PetStatus) value; break;
            default: throw new IllegalArgumentException("Incorrect attr to update");
        }
        petsToDelete.add(model);
    }

    @Test(testName = "UpdatePet", dataProvider = "valuesToUpdate")
    public void testUpdatePet(String attr, Object value) {
        PetModel petModel = getRandomPet();

        CustomLogger.logStep("Creating the Pet");
        Response createPetResponse = PetRequest.createPet(petModel);
        BaseSteps.verifyStatusCode(createPetResponse, HttpStatus.SC_OK);
        PetModel createdPet = createPetResponse.as(PetModel.class);
        Assert.assertEquals(createdPet, petModel, "Created model is not as expected");

        CustomLogger.logStep("Updating the Pet");
        updateModel(createdPet, attr, value);
        Response updatePetResponse = PetRequest.updatePet(createdPet);
        BaseSteps.verifyStatusCode(updatePetResponse, HttpStatus.SC_OK);
        PetModel updatedModel = updatePetResponse.as(PetModel.class);
        Assert.assertEquals(updatedModel, createdPet, "Updated model is not as expected");

        CustomLogger.logStep("Getting the Pet");
        Response getPetResponse = PetRequest.getPetById(updatedModel.id);
        BaseSteps.verifyStatusCode(getPetResponse, HttpStatus.SC_OK);
        Assert.assertEquals(getPetResponse.as(PetModel.class), updatedModel, "Updated model is not as expected");
    }

    @Test(testName = "UpdatePetById")
    public void testUpdatePetById() {
        PetModel petModel = getRandomPet();
        String newName = RandomStringUtils.random(10, true, false);
        PetStatus newStatus = Arrays.stream(PetStatus.values()).filter(status -> !status.equals(petModel.status)).findAny().orElse(petModel.status);

        CustomLogger.logStep("Creating the Pet");
        Response createPetResponse = PetRequest.createPet(petModel);
        BaseSteps.verifyStatusCode(createPetResponse, HttpStatus.SC_OK);
        PetModel createdPet = createPetResponse.as(PetModel.class);
        Assert.assertEquals(createdPet, petModel, "Created model is not as expected");

        CustomLogger.logStep("Updating the Pet");
        Response updatePetResponse = PetRequest.updatePetById(petModel.id, newName, newStatus);
        BaseSteps.verifyStatusCode(updatePetResponse, HttpStatus.SC_OK);

        CustomLogger.logStep("Getting the Pet");
        Response getPetResponse = PetRequest.getPetById(petModel.id);
        BaseSteps.verifyStatusCode(getPetResponse, HttpStatus.SC_OK);
        PetModel updatedModel = getPetResponse.as(PetModel.class);
        Assert.assertEquals(updatedModel.name, newName, "Updated model is not as expected");
        Assert.assertEquals(updatedModel.status, newStatus, "Updated model is not as expected");
    }

    @Test(testName = "UploadImage")
    public void testUploadImage() {
        File file = new File(getClass().getClassLoader().getResource("testData/test_image.jpg").getFile());
        PetModel petModel = getRandomPet();

        CustomLogger.logStep("Creating the Pet");
        Response createPetResponse = PetRequest.createPet(petModel);
        BaseSteps.verifyStatusCode(createPetResponse, HttpStatus.SC_OK);
        PetModel createdPet = createPetResponse.as(PetModel.class);
        Assert.assertEquals(createdPet, petModel, "Created model is not as expected");

        CustomLogger.logStep("Updating the Pet");
        Response updatePetResponse = PetRequest.uploadImage(petModel.id, "ImageToUpload", file);
        BaseSteps.verifyStatusCode(updatePetResponse, HttpStatus.SC_OK);
    }
}

package tests.users;

import io.qameta.allure.Issue;
import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.UserModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import integration.requests.UserRequest;
import integration.steps.BaseSteps;
import integration.steps.UserSteps;

import java.math.BigInteger;

public class UpdateUserTests extends UserBaseTest {

    @DataProvider(name = "valuesToUpdate")
    public Object[][] dataProvFunc(){
        return new Object[][]{
                {"id", new BigInteger(RandomStringUtils.random(10, false, true))},
                {"username", RandomStringUtils.random(12, true, false)},
                {"firstName", "firstNameW"},
                {"lastName", "lastNameQ"},
                {"email", "email@email.ru"},
                {"password", "password123444"},
                {"phone", "88888888"},
                {"userStatus", Integer.parseInt(RandomStringUtils.random(1, false, true))},
        };
    }

    private UserModel getUpdatedModel(UserModel model, String attr, Object value) {
        UserModel newModel = SerializationUtils.clone(model);
        switch (attr) {
            case "id": newModel.id = (BigInteger) value; break;
            case "username": newModel.username = (String) value; break;
            case "firstName": newModel.firstName = (String) value; break;
            case "lastName": newModel.lastName = (String) value; break;
            case "email": newModel.email = (String) value; break;
            case "password": newModel.password = (String) value; break;
            case "phone": newModel.phone = (String) value; break;
            case "userStatus": newModel.userStatus = (int) value; break;
            default: throw new IllegalArgumentException("Incorrect attr to update");
        }
        return newModel;
    }

    @Test(testName = "UpdateOneField", dataProvider = "valuesToUpdate")
    @Issue("No possible to update ID. Update method creates a new object")
    public void testUpdateOneField(String attr, Object value) {
        UserModel model = getRandomUser();
        UserModel newModel = getUpdatedModel(model, attr, value);

        CustomLogger.logStep("Creating and getting the User");
        UserModel createdModel = UserSteps.createAndGetUser(model);

        CustomLogger.logStep("Updating the User");
        Response updateResponse = UserRequest.updateUser(createdModel.username, newModel);
        BaseSteps.verifyStatusAndApiResponse(updateResponse, HttpStatus.SC_OK, newModel.id.toString());

        CustomLogger.logStep("Getting the User");
        UserModel updatedModel = UserSteps.getUser(newModel.username);

        Assert.assertEquals(updatedModel.id, newModel.id, "ID is not as expected");
        Assert.assertEquals(updatedModel.username, newModel.username, "Username is not as expected");
        Assert.assertEquals(updatedModel.firstName, newModel.firstName, "FirstName is not as expected");
        Assert.assertEquals(updatedModel.lastName, newModel.lastName, "LastName is not as expected");
        Assert.assertEquals(updatedModel.email, newModel.email, "Email is not as expected");
        Assert.assertEquals(updatedModel.password, newModel.password, "Password is not as expected");
        Assert.assertEquals(updatedModel.phone, newModel.phone, "Phone is not as expected");
        Assert.assertEquals(updatedModel.userStatus, newModel.userStatus, "UserStatus is not as expected");
    }

    @Test(testName = "UpdateAllFields")
    public void testUpdateAllFields() {
        UserModel model = getRandomUser();
        UserModel newModel = getRandomUser();

        CustomLogger.logStep("Creating and getting the User");
        UserModel createdModel = UserSteps.createAndGetUser(model);

        CustomLogger.logStep("Updating the User");
        Response updateResponse = UserRequest.updateUser(createdModel.username, newModel);
        BaseSteps.verifyStatusAndApiResponse(updateResponse, HttpStatus.SC_OK, newModel.id.toString());

        CustomLogger.logStep("Getting the User");
        UserModel updatedModel = UserSteps.getUser(newModel.username);

        Assert.assertEquals(updatedModel.id, newModel.id, "Id is not as expected");
        Assert.assertEquals(updatedModel.username, newModel.username, "Username is not as expected");
        Assert.assertEquals(updatedModel.firstName, newModel.firstName, "FirstName is not as expected");
        Assert.assertEquals(updatedModel.lastName, newModel.lastName, "LastName is not as expected");
        Assert.assertEquals(updatedModel.email, newModel.email, "Email is not as expected");
        Assert.assertEquals(updatedModel.password, newModel.password, "Password is not as expected");
        Assert.assertEquals(updatedModel.phone, newModel.phone, "Phone is not as expected");
        Assert.assertEquals(updatedModel.userStatus, newModel.userStatus, "UserStatus is not as expected");
    }

    @Test(testName = "UpdateNonExistingUser")
    @Issue("Update method creates a new object")
    public void testUpdateNonExistingUser() {
        String incorrectName = RandomStringUtils.random(12, true, false);
        UserModel model = getRandomUser();

        CustomLogger.logStep("Updating the User");
        Response updateResponse = UserRequest.updateUser(incorrectName, model);
        BaseSteps.verifyStatusCode(updateResponse, HttpStatus.SC_NOT_FOUND);

        CustomLogger.logStep("Validating the User has not been created");
        for(String name: new String[] {incorrectName, model.username}) {
            Response response = UserRequest.getUserByUsername(name);
            BaseSteps.verifyStatusCode(response, HttpStatus.SC_NOT_FOUND);
        }

    }
}

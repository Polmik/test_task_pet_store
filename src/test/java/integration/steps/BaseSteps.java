package integration.steps;

import integration.models.ApiResponseModel;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;

public class BaseSteps {

    @Step("Check the status code of response")
    public static void verifyStatusCode(Response response, int expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), expectedStatus,
                String.format("The status code of response is %d\n%s", expectedStatus, response.asString()));
    }

    @Step("Check the message of ApiResponse")
    public static void verifyApiResponse(Response response, String expectedMessage) {
        ApiResponseModel apiResponseModel = response.getBody().as(ApiResponseModel.class);
        Assert.assertEquals(apiResponseModel.message, expectedMessage,
                String.format("The message of api response is %s\n%s", expectedMessage, apiResponseModel.message));
    }

    @Step("Check the message of ApiResponse")
    public static void verifyPartOfApiResponse(Response response, String expectedMessage) {
        ApiResponseModel apiResponseModel = response.getBody().as(ApiResponseModel.class);
        Assert.assertTrue(apiResponseModel.message.contains(expectedMessage),
                String.format("The response does not contain %s", expectedMessage));
    }

    @Step("Check the status code and message of Response")
    public static void verifyStatusAndApiResponse(Response response, int expectedStatus, String expectedMessage) {
        verifyStatusCode(response, expectedStatus);
        verifyApiResponse(response, expectedMessage);
    }
}

package tests.users;

import io.restassured.response.Response;
import integration.loggers.CustomLogger;
import integration.models.UserModel;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import integration.requests.UserRequest;
import integration.steps.BaseSteps;
import tests.LoginBaseTest;

import java.util.ArrayList;
import java.util.List;

public class UserBaseTest extends LoginBaseTest {
    private final List<UserModel> usersToDelete = new ArrayList<>();

    @AfterMethod
    public synchronized void teardownDeleteUsers() {
        CustomLogger.logAllureStep(String.format("Deleting users: %s", usersToDelete.size()), () -> {
            for (UserModel userModel : usersToDelete) {
                Response response = UserRequest.getUserByUsername(userModel.username);
                if (response.getStatusCode() == HttpStatus.SC_OK) {
                    try {
                        response = UserRequest.deleteUser(userModel.username);
                        BaseSteps.verifyStatusCode(response, HttpStatus.SC_OK);
                    } catch (Exception e) {
                        CustomLogger.warn(String.format("User '%s' has not been deleted", userModel.username));
                    }
                }
            }
        });
        usersToDelete.clear();
    }

    protected UserModel getRandomUser() {
        UserModel user = UserModel.getRandomUser();
        usersToDelete.add(user);
        return user;
    }
}

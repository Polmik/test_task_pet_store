package integration.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class UserModel  implements Serializable {
    @JsonProperty("id")
    public BigInteger id;
    @JsonProperty("username")
    public String username;
    @JsonProperty("firstName")
    public String firstName;
    @JsonProperty("lastName")
    public String lastName;
    @JsonProperty("email")
    public String email;
    @JsonProperty("password")
    public String password;
    @JsonProperty("phone")
    public String phone;
    @JsonProperty("userStatus")
    public int userStatus;

    public static UserModel getRandomUser() {
        return getRandomUser(new BigInteger(RandomStringUtils.random(10, false, true)));
    }

    public static UserModel getRandomUser(BigInteger id) {
        UserModel model = new UserModel();
        model.id = id;
        model.username = RandomStringUtils.random(10, true, false);
        model.firstName = RandomStringUtils.random(10, true, false);
        model.lastName = RandomStringUtils.random(10, true, false);
        model.email = RandomStringUtils.random(10, true, false) + "@email.com";
        model.password = RandomStringUtils.random(10, true, true);
        model.phone = RandomStringUtils.random(10, false, true);
        model.userStatus = Integer.parseInt(RandomStringUtils.random(1, false, true));
        return model;
    }
}

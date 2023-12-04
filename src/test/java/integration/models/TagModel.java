package integration.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigInteger;

public class TagModel {
    @JsonProperty("id")
    public BigInteger id;
    @JsonProperty("name")
    public String name;

    public static TagModel getRandomTag() {
        var model = new TagModel();
        model.id = new BigInteger(RandomStringUtils.random(8, false, true));
        model.name = RandomStringUtils.random(10, true, false);
        return model;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var expObj = (TagModel) obj;
        return this.id.equals(expObj.id)
                && this.name.equals(expObj.name);
    }
}

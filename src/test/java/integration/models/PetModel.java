package integration.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import integration.constants.PetStatus;
import integration.constants.PetUrls;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import utils.EnumUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class PetModel {
    @JsonProperty("id")
    public BigInteger id;
    @JsonProperty("category")
    public CategoryModel category;
    @JsonProperty("name")
    public String name;
    @JsonProperty("photoUrls")
    public List<String> photoUrls = new ArrayList<>();
    @JsonProperty("tags")
    public List<TagModel> tags = new ArrayList<>();
    @JsonProperty("status")
    public PetStatus status;

    public static PetModel getRandomPet() {
        PetModel model = new PetModel();
        model.id = new BigInteger(RandomStringUtils.random(8, false, true));
        model.category = CategoryModel.getRandomCategory();
        model.name = RandomStringUtils.random(10, true, false);
        model.photoUrls.add(EnumUtils.randomValue(PetUrls.class).getValue());
        model.tags.add(TagModel.getRandomTag());
        model.status = EnumUtils.randomValue(PetStatus.class);
        return model;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        PetModel expObj = (PetModel) obj;
        return this.id.equals(expObj.id)
                && this.name.equals(expObj.name)
                && this.status.equals(expObj.status)
                && this.category.equals(expObj.category)
                && this.tags.equals(expObj.tags)
                && this.photoUrls.equals(expObj.photoUrls);
    }
}

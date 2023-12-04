package integration.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import integration.constants.StoreStatus;
import org.apache.commons.lang3.RandomStringUtils;
import utils.EnumUtils;

import java.math.BigInteger;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class StoreModel {
    @JsonProperty("id")
    public BigInteger id;
    @JsonProperty("petId")
    public BigInteger petId;
    @JsonProperty("quantity")
    public int quantity;
    @JsonProperty("shipDate")
    public String shipDate;
    @JsonProperty("status")
    public StoreStatus status;
    @JsonProperty("complete")
    public boolean complete;

    public static StoreModel getRandomStore(BigInteger id, BigInteger petId) {
        StoreModel model = new StoreModel();
        model.id = id;
        model.petId = petId;
        model.quantity = Integer.parseInt(RandomStringUtils.random(4, false, true));
        model.shipDate = Calendar.getInstance().getTime().toInstant().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        model.status = EnumUtils.randomValue(StoreStatus.class);
        model.complete = true;
        return model;
    }

    public static StoreModel getRandomStore(BigInteger petId) {
        return getRandomStore(new BigInteger(RandomStringUtils.random(10, false, true)), petId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        StoreModel expObj = (StoreModel) obj;
        return this.id.equals(expObj.id)
                && this.petId.equals(expObj.petId)
                && this.status.equals(expObj.status)
                && this.quantity == expObj.quantity
                && this.shipDate.equals(expObj.shipDate)
                && this.complete == expObj.complete;
    }
}

package integration.requests;

import integration.helpers.ApiHelper;
import integration.requests.methods.StoreMethods;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import integration.models.StoreModel;
import rest.enums.ContentType;
import rest.enums.HttpRequestType;
import utils.JsonUtils;
import utils.StringUtils;

import java.math.BigInteger;

public class StoreRequest extends BaseApi {

    @Step("Placing a store {storeModel.id} for a pet {storeModel.petId}")
    public static Response placeStore(StoreModel storeModel) {
        return ApiHelper.sendRequest(HttpRequestType.POST,
                StringUtils.concat(baseURI, StoreMethods.PLACE_ORDER.getValue()),
                JsonUtils.getJsonStringFromObject(storeModel),
                getAuthorizationHeader(),
                ContentType.JSON);
    }

    @Step("Getting the store by ID {id}")
    public static Response getStoreById(BigInteger id) {
        return ApiHelper.sendRequest(HttpRequestType.GET,
                String.format(StringUtils.concat(baseURI, StoreMethods.FIND_ORDER_BY_ID.getValue()), id),
                null,
                getAuthorizationHeader());
    }

    @Step("Deleting the store by ID {id}")
    public static Response deleteStoreById(BigInteger id) {
        return ApiHelper.sendRequest(HttpRequestType.DELETE,
                String.format(StringUtils.concat(baseURI, StoreMethods.DELETE_ORDER_BY_ID.getValue()), id),
                null,
                getAuthorizationHeader());
    }

    @Step("Getting pet Inventories")
    public static Response getInventories() {
        return ApiHelper.sendRequest(HttpRequestType.GET,
                StringUtils.concat(baseURI, StoreMethods.GET_INVENTORIES.getValue()),
                null,
                getAuthorizationHeader());
    }
}

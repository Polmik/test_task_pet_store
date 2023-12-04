package rest;

import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import logging.Logger;
import org.apache.http.HttpStatus;
import org.apache.http.entity.mime.HttpMultipartMode;
import rest.enums.ContentType;
import rest.enums.HttpRequestType;
import utils.JsonUtils;

import java.util.Collections;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class RestAssuredHelper {

    private RestAssuredHelper() {
    }

    public static Response sendRequest(HttpRequestType type, String url, String body) {
        return sendRequest(type, url, body, Collections.emptyMap());
    }

    public static Response sendRequest(HttpRequestType type, String url, Object body) {
        return sendRequest(type, url, body, Collections.emptyMap());
    }

    public static Response sendRequest(HttpRequestType type, String url, Object body, Map<String, String> headers) {
        return sendRequest(type, url, body, headers, ContentType.JSON);
    }

    public static Response sendRequest(HttpRequestType type, String url, Object body, ContentType contentType) {
        return sendRequest(type, url, body, Collections.emptyMap(), contentType);
    }

    public static Response sendRequest(HttpRequestType type, String url, Object body, Map<String, String> headers, ContentType contentType) {
        return sendRequest(type, url, body, headers, contentType, Collections.emptyMap());
    }

    public static Response sendRequest(HttpRequestType type, String url, Object body, Map<String, String> headers, ContentType contentType,
                                       Map<String, Object> params) {
        RequestSpecification request = given().config(RestAssuredConfig.config().
                httpClient(HttpClientConfig.httpClientConfig().
                        httpMultipartMode(HttpMultipartMode.BROWSER_COMPATIBLE))).
                header(ContentType.KEY, contentType.getValue());
        if (contentType.equals(ContentType.FORM_DATA)) {
            for (var entry: params.entrySet()) {
                request = request.multiPart(entry.getKey(), entry.getValue());
            }
        } else if (contentType.equals(ContentType.FORM_URLENCODED)) {
            for (var entry: params.entrySet()) {
                request = request.formParam(entry.getKey(), entry.getValue());
            }
        } else {
            request = request.params(params);
        }
        if (body != null) {
            request = request.body(body);
        }

        headers.forEach(request::header);
        Response response = executeRequest(type, url, request);
        Logger.getInstance().debug(format("Status code is %s", response.getStatusCode()));
        Logger.getInstance().debug(format("Response time is %s", response.getTime()));
        if (response.getStatusCode() != HttpStatus.SC_OK) {
            Logger.getInstance().debug(format("Response is not 200 OK. Body of response: \"%s\"",
                    JsonUtils.isJsonValid(response.asString()) ? JsonUtils.getPrettyJson(response.asString())
                            : response.asString()));
        }
        return response;
    }

    private static Response executeRequest(HttpRequestType type, String url, RequestSpecification request) {
        switch (type) {
            case POST:
                return request.post(url);
            case PATCH:
                return request.patch(url);
            case GET:
                return request.get(url);
            case DELETE:
                return request.delete(url);
            case PUT:
                return request.put(url);
            default:
                throw new RuntimeException(format("Unknown HttpRequestType %s", type));
        }
    }
}

package integration.helpers;

import integration.loggers.CustomLogger;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import integration.loggers.RequestLogger;
import integration.properties.ConfigProperties;
import rest.RestAssuredHelper;
import rest.enums.ContentType;
import rest.enums.HttpRequestType;
import utils.JsonUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class ApiHelper {
    private ApiHelper() {}

    public static Response sendRequest(HttpRequestType type, String url, String body) {
        return sendRequest(type, url, body, Collections.emptyMap(), ContentType.JSON);
    }

    public static Response sendRequest(HttpRequestType type, String url) {
        return sendRequest(type, url, null, ContentType.JSON);
    }

    public static Response sendRequest(HttpRequestType type, String url, Object body, Map<String, String> headers) {
        return sendRequest(type, url, body, headers, ContentType.JSON);
    }

    public static Response sendRequest(HttpRequestType type, String url, Object body, ContentType contentType) {
        return sendRequest(type, url, body, Collections.emptyMap(), contentType);
    }

    public static Response sendRequest(HttpRequestType type, String url, Map<String, String> headers) {
        return sendRequest(type, url, null, headers, ContentType.JSON);
    }

    public static Response sendRequest(HttpRequestType type, String url, Object body,
                                       Map<String, String> headers, ContentType contentType) {
        return sendRequest(type, url, body, headers, contentType, Collections.emptyMap());
    }


    public synchronized static Response sendRequest(HttpRequestType type, String url, Object body,
                                                    Map<String, String> headers, ContentType contentType,
                                                    Map<String, Object> params) {
        RequestLogger requestLogger = new RequestLogger();
        if (ConfigProperties.getLogRequest()) {
            requestLogger.logRequestInfo(type, url, body, headers, contentType);
        }
        ByteArrayOutputStream requestBytes = new ByteArrayOutputStream();
        ByteArrayOutputStream responseBytes = new ByteArrayOutputStream();
        PrintStream requestVar;
        PrintStream responseVar;
        try {
            requestVar = new PrintStream(requestBytes, true, StandardCharsets.UTF_8.toString());
            responseVar  = new PrintStream(responseBytes, true, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            requestVar = new PrintStream(requestBytes, true);
            responseVar  = new PrintStream(responseBytes, true);
        }
        RestAssured.filters(
                new ResponseLoggingFilter(LogDetail.ALL, responseVar),
                new RequestLoggingFilter(LogDetail.ALL, requestVar)
        );

        Response response = RestAssuredHelper.sendRequest(type, url, body, headers, contentType, params);

        if (ConfigProperties.getLogResponse()) {
            String responseAsStr = response.asString();
            if (JsonUtils.isJsonValid(responseAsStr)) {
                requestLogger.logResponseBody(responseAsStr, ContentType.JSON);
            }
            else {
                CustomLogger.info("Response:\n" + responseAsStr);
            }
        }
        attachToAllure(String.format("%s request: %s", type, url), requestBytes);
        attachToAllure(String.format("%s response: %s", type, url), responseBytes);

        return response;
    }

    private static void attachToAllure(String name, ByteArrayOutputStream stream) {
        byte[] array = stream.toByteArray();
        stream.reset();
        Allure.addAttachment(name, new String(array, StandardCharsets.UTF_8));
    }
}

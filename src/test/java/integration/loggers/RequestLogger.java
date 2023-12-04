package integration.loggers;

import integration.helpers.SecretWordsReplacer;
import rest.enums.ContentType;
import rest.enums.HttpRequestType;
import utils.JAXBUtils;
import utils.JsonUtils;

import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class RequestLogger {
    private static final String SEND_REQUEST_WITH_HEADERS_MSG = "Sending \"%s\" %s request with headers:%n%s";
    private static final String SEND_REQUEST_WITH_BODY_MSG = "Sending \"%s\" %s request with body:%n%s";
    private static final String RESPONSE_BODY_MSG = "Response: %n%s";
    private static final String SEND_REQUEST_WITH_HEADERS_AND_BODY_MSG = "Sending \"%s\" %s request with\nheaders:%n%s\nbody:%n%s";

    private static final SecretWordsReplacer SECRET_WORDS_REPLACER = new SecretWordsReplacer();

    public void logRequestInfo(HttpRequestType type, String url, Object body, Map<String, String> headers,
                               ContentType contentType) {
        Map<String, String> headersToLog = SECRET_WORDS_REPLACER.hideVulnerableHeaders(headers);
        logRequestInfoWithHeaders(type, url, body, headersToLog, contentType);
    }

    private void logRequestInfoWithHeaders(HttpRequestType type, String url, Object body, Map<String, String> headers,
                                           ContentType contentType) {
        if (body == null) {
            CustomLogger.info(format(SEND_REQUEST_WITH_HEADERS_MSG, url, type, getFormattedHeaders(headers)));
        } else if (headers.isEmpty()) {
            CustomLogger.info(format(SEND_REQUEST_WITH_BODY_MSG, url, type,
                    SECRET_WORDS_REPLACER.hideVulnerableBodyParts(getBody(body, contentType))));
        } else {
            CustomLogger.info(format(SEND_REQUEST_WITH_HEADERS_AND_BODY_MSG, url, type, getFormattedHeaders(headers),
                    SECRET_WORDS_REPLACER.hideVulnerableBodyParts(getBody(body, contentType))));
        }
    }

    public void logResponseBody(Object body, ContentType contentType) {
        CustomLogger.info(format(RESPONSE_BODY_MSG, SECRET_WORDS_REPLACER.hideVulnerableBodyParts(getBody(body, contentType))));
    }

    private String getFormattedHeaders(Map<String, String> headers) {
        return headers.entrySet().stream()
                .map(entry -> format("\t%s:%s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String getBody(Object body, ContentType contentType) {
        if (contentType == ContentType.XML) {
            return getBodyAsXml(body);
        }
        return JsonUtils.getPrettyJson(body.toString());
    }

    private String getBodyAsXml(Object body) {
        try {
            return JAXBUtils.marshalObjectToString(body);
        } catch (RuntimeException e) {
            CustomLogger.warn(format("Exception occurred during parsing request body to xml: %s", e));
            return body.toString();
        }
    }
}

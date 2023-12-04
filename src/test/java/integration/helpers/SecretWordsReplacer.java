package integration.helpers;

import integration.properties.SecretWordsProperties;
import utils.RegexMatcherUtils;

import java.util.Map;
import java.util.stream.Collectors;

public class SecretWordsReplacer {

    public Map<String, String> hideVulnerableHeaders(Map<String, String> headers) {
        return headers.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, this::hideHeaderValue));
    }

    private String hideHeaderValue(Map.Entry<String, String> rawHeader) {
        if (SecretWordsProperties.getSecretHeaders().contains(rawHeader.getKey())) {
            String headerValue = rawHeader.getValue();
            String value = RegexMatcherUtils.regexGetMatch(
                    headerValue,
                    SecretWordsProperties.getSecretHeadersReplacementPattern()
            );
            return headerValue.replace(value, SecretWordsProperties.getReplacement());
        }
        return rawHeader.getValue();
    }

    public String hideVulnerableBodyParts(String body) {
        String securedBody = body;
        for (String pattern : SecretWordsProperties.getSecretBodyWordsPatterns()) {
            String wordToHide = RegexMatcherUtils.regexGetMatchGroup(securedBody, pattern, 1);
            if (wordToHide != null) {
                securedBody = securedBody.replace(wordToHide, SecretWordsProperties.getReplacement());
            }
        }
        return securedBody;
    }
}

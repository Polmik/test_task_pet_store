package integration.properties;

import constants.StringConstants;
import properties.PropertiesResourceManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SecretWordsProperties {
    private static final PropertiesResourceManager PROPERTIES =
            new PropertiesResourceManager("properties/secretWords.properties");

    private SecretWordsProperties() {}

    public static List<String> getSecretBodyWordsPatterns() {
        return getSecrets("secretBodyWords").stream()
                .map(word -> String.format(getSecretBodyWordsReplacementTemplate(), word))
                .collect(Collectors.toList());
    }

    public static List<String> getSecretHeaders() {
        return getSecrets("secretHeaders");
    }

    public static String getSecretHeadersReplacementPattern() {
        return PROPERTIES.getProperty("secretHeadersReplacementPattern");
    }

    public static String getReplacement() {
        return PROPERTIES.getProperty("replacement");
    }

    private static List<String> getSecrets(String secretWord) {
        return Arrays.stream(PROPERTIES.getProperty(secretWord).split(StringConstants.COMMA))
                .collect(Collectors.toList());
    }

    private static String getSecretBodyWordsReplacementTemplate() {
        return PROPERTIES.getProperty("secretBodyWordsReplacementTemplate");
    }
}

package integration.requests;

import constants.StringConstants;
import integration.properties.ConfigProperties;
import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class BaseApi {

    protected static String baseURI = StringUtils.concat(ConfigProperties.getHost(),
            StringConstants.SLASH, ConfigProperties.getVersion());

    public static Map<String, String> getAuthorizationHeader() {
        return new HashMap<>();
    }
}

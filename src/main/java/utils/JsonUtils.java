package utils;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    private static final int INDENT_FACTOR = 4;

    private JsonUtils() {}

    public static String getJsonStringFromObject(Object object) {
        return getInstance().toJson(object);
    }

    public static String getPrettyJson(String jsonBody) {
        try {
            return (new JSONObject(jsonBody)).toString(INDENT_FACTOR);
        } catch (JSONException var6) {
            try {
                return (new JSONArray(jsonBody)).toString(INDENT_FACTOR);
            } catch (JSONException var5) {
                throw new RuntimeException(String.format("This string '%s' is not json", jsonBody));
            }
        }
    }

    public static boolean isJsonValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException var4) {
            try {
                new JSONArray(test);
            } catch (JSONException var3) {
                return false;
            }
        }
        return true;
    }

    public static <T> T getObjectFromJson(String jsonBody, Class<T> inputObject) {
        T object = getInstance().fromJson(jsonBody, inputObject);
        if (object == null) {
            throw new RuntimeException(String.format("Json '%s' can't be parsed into class '%s'", jsonBody, inputObject));
        }
        return object;
    }

    private static Gson getInstance() {
        return new Gson();
    }
}


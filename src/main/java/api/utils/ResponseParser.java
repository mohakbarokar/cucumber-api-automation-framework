package api.utils;

import io.restassured.response.Response;
import org.json.JSONObject;

public class ResponseParser {
    public static String extractValue(Response response, String key) {
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        return jsonResponse.getString(key);
    }
}

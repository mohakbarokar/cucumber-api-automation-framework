package api.utils;

import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private static Map<String, String> dataStore = new HashMap<>();

    public static void saveData(String key, String value) {
        dataStore.put(key, value);
    }

    public static String getData(String key) {
        return dataStore.get(key);
    }
}

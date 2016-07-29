package com.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Spirit on 16/6/29.
 */
public class ConfigLoader {

    private Map<String, String> configPairs;



    public ConfigLoader(String path) {
        configPairs = new HashMap<String, String>();
        String[] lines = TxtUtil.getFileContent(path).split("\n");

        for (String line : lines) {
            if (line.startsWith("#"))
                continue;
            String[] kv = line.split("=");
            if (kv.length != 2)
                continue;
            configPairs.put(kv[0], kv[1]);
        }
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        if (configPairs.containsKey(key)) {
            return configPairs.get(key);
        } else {
            return defaultValue;
        }
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        if (configPairs.containsKey(key)) {
            return Integer.parseInt(configPairs.get(key));
        } else {
            return defaultValue;
        }
    }

    public double getDouble(String key) {
        return getDouble(key, -1.0);
    }

    public double getDouble(String key, double defaultValue) {
        if (configPairs.containsKey(key)) {
            return Double.parseDouble(configPairs.get(key));
        } else {
            return defaultValue;
        }
    }

    public static void main(String[] args) {
        String configPath = "config";
        ConfigLoader loader = new ConfigLoader(configPath);
        System.out.println(loader.getString("lda_max_words"));
    }
}

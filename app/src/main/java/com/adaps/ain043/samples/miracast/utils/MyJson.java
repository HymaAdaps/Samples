package com.adaps.ain043.samples.miracast.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyJson {
    public static JSONObject getNode(JSONObject jObject, String KeyCommand) {
        if (jObject != null) {
            try {
                if (jObject.has(KeyCommand)) {
                    return jObject.getJSONObject(KeyCommand);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static JSONArray getArray(JSONObject jObject, String KeyCommand) {
        if (jObject != null) {
            try {
                if (jObject.has(KeyCommand)) {
                    return jObject.getJSONArray(KeyCommand);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getString(JSONObject jObject, String KeyCommand) {
        if (jObject != null) {
            try {
                if (jObject.has(KeyCommand)) {
                    return jObject.getString(KeyCommand).replace("null", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static boolean getBoolean(JSONObject jObject, String KeyCommand) {
        if (jObject != null) {
            try {
                if (jObject.has(KeyCommand)) {
                    return jObject.getBoolean(KeyCommand);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static int getInt(JSONObject jObject, String KeyCommand) {
        if (jObject != null) {
            try {
                if (jObject.has(KeyCommand)) {
                    return jObject.getInt(KeyCommand);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}

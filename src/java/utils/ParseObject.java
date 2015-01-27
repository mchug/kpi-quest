/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Maxim
 */
public class ParseObject {

    private final Map<String, String> fields = new HashMap<>();
    private final String createdAt;
    private final String updatedAt;
    private final String objectId;

    public ParseObject(String json) {
        json = json.substring(1, json.length() - 1);
        String[] objs = json.split(",(?=\")");

        for (String field : objs) {
            String[] split = field.split(":", 2);
            split[0] = split[0].substring(1, split[0].length() - 1);
            fields.put(split[0], split[1]);
        }

        createdAt = fields.remove("createdAt").replace("\"", "");
        updatedAt = fields.remove("updatedAt").replace("\"", "");
        objectId = fields.remove("objectId").replace("\"", "");
    }

    public String getObjectId() {
        return objectId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getField(String name) {
        String value = fields.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Field " + name + " does not exist in object");
        }
        return value.startsWith("\"") ? value.substring(1, value.length() - 1) : value;
    }

    public void setField(String name, String value) {

        if (fields.get(name) != null && fields.get(name).startsWith("\"")) {
            value = "\"" + value + "\"";
        }

        if (fields.replace(name, value) == null) {
            throw new IllegalArgumentException("Field " + name + " does not exist in object");
        }
    }

    @Override
    public String toString() {
        StringBuilder outStr = new StringBuilder();
        fields.forEach((String K, String V) -> {
            outStr.append(",\"").append(K).append("\":").append(V);
        });
        return "{" + outStr.substring(1) + "}";
    }        
}

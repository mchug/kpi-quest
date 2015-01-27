/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Maxim
 */
public class ParseSession {

    private static final String[][] defaultHeader = {
        {"X-Parse-Application-Id", "dr4f1Kca7ju9cud1NSaooKsAqtBNGMcZNDS2ApU1"},
        {"X-Parse-REST-API-Key", " XXUnoa2ga0VIRnIETF3ftJsDGS77n3y12hhVGkKp"},
        {"Content-Type", "application/json"}
    };
    private final static String BASE_URL = "https://api.parse.com/1/";

    public static List<ParseObject> getQuery(String className) throws HttpRequestException {
        String resp = HttpRequest.sendRequest(HttpRequest.HTTP_GET, BASE_URL
                + "classes/" + className, defaultHeader, null);

        String[] split = resp.substring(resp.indexOf("[") + 1,
                resp.lastIndexOf("]") - 1).split(",(?=[{])");

        List<ParseObject> list = new LinkedList<>();
        for (String item : split) {
            list.add(new ParseObject(item));
        }

        return list;
    }

    public static ParseObject getObject(String className, String id) throws HttpRequestException {
        String resp = HttpRequest.sendRequest(HttpRequest.HTTP_GET, BASE_URL
                + "classes/" + className + "/" + id, defaultHeader, null);

        return new ParseObject(resp);
    }

    public static void updateObject(String className, String id, ParseObject newObj) throws HttpRequestException {
        HttpRequest.sendRequest(HttpRequest.HTTP_PUT, BASE_URL
                + "classes/" + className + "/" + id, defaultHeader, newObj.toString());
    }

    public static void createObject(String className, String json) throws HttpRequestException {
        HttpRequest.sendRequest(HttpRequest.HTTP_POST, BASE_URL
                + "classes/" + className, defaultHeader, json);
    }

    public static void deleteObject(String className, String id) throws HttpRequestException {
        HttpRequest.sendRequest(HttpRequest.HTTP_DELETE, BASE_URL
                + "classes/" + className + "/" + id, defaultHeader, null);
    }
}

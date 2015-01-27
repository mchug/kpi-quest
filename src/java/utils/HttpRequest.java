/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 *
 * @author Maxim
 */
public class HttpRequest {

    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_DELETE = "DELETE";
    public static final int TIMEOUT = 1000;

    public static String sendRequest(String type, String url, String[][] headers, String data) throws HttpRequestException {
        HttpURLConnection conn;
        BufferedReader rd;
        String result = "";
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(TIMEOUT);
            conn.setRequestMethod(type);

            for (String[] pair : headers) {
                conn.setRequestProperty(pair[0], pair[1]);
            }

            if (data != null) {
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(data.getBytes(Charset.forName("utf-8")));
                }
            }

            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),Charset.forName("utf-8")));
            String line;
            while ((line = rd.readLine()) != null) {
                result += line + "\n";
            }
            rd.close();
        } catch (Exception ex) {
            throw new HttpRequestException(ex);
        }
        return result;
    }
}

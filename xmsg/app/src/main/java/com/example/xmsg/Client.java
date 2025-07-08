package com.example.xmsg;

import java.io.*;
import java.net.*;

public class Client {
    private static final String CONTENT_TYPE = "application/json";
    private static final String BASE_URL = "http://127.0.0.1:8001";
    private static final String MY_USER_ID = "1";
    private static final String MY_PASS_HASH = "b1b3773a05c0ed0176787a4f1574ff0075f7521e";

    public static void main(String[] args) {
        System.out.println("start");
        String requestMethod = "POST";
        String jsonInputString = "{\"user_id\": \"7\", \"key\": \"6ed66eac474e5379251d44b851edfa7e85432250\"}";
        String url_line = BASE_URL + "/signin";

        try {
            URL url = URI.create(url_line).toURL();

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", CONTENT_TYPE);
            con.setRequestMethod(requestMethod);

            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            byte[] postDataBytes = jsonInputString.getBytes("UTF-8");
            os.write(postDataBytes);

            int responseCode = con.getResponseCode();
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

            System.out.println("Response Code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

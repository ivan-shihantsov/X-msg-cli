package com.example.xmsg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import android.os.AsyncTask;

import java.util.Map;
import org.json.JSONObject;


public class HTTPReqTask extends AsyncTask<String, Void, Void> {
    private String REQ_METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json";
    private static final String BASE_URL = "http://192.168.100.59:8001";


    // next are changeable data - specific for each request
    private String USER_ID = "not-user-id";
    private String USER_KEY = "not-sha1-hashsum";
    private String ENDPOINT = "/not-existing-endpoint";
    private String RESPONSE_LINE = "not-a-response";
    private CustomCallback reqCallback;

    // This is the JSON body of the post
    private JSONObject postData;
    // This is a constructor that allows you to pass in the JSON body

    public HTTPReqTask(Map<String, String> postData, CustomCallback callback) {
        if (postData != null) {
            this.postData = new JSONObject(postData);
        }
        this.reqCallback = callback;
    }

    @Override
    protected Void doInBackground(String... params) {
        // params: 0. requestMethod, 1. endpoint, 2. user_id, 3. password
        REQ_METHOD = params[0];
        USER_ID = params[2];
        USER_KEY = params[3];

        ENDPOINT = params[1];
        String url_line = BASE_URL + ENDPOINT;

        HttpURLConnection con = null;

        try {
            JSONObject postData = new JSONObject();
            postData.put("user_id", USER_ID);
            postData.put("key", USER_KEY);

            URL url = URI.create(url_line).toURL();

            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", CONTENT_TYPE);
            con.setRequestMethod(REQ_METHOD);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            DataOutputStream out = new DataOutputStream(os);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
            writer.write(postData.toString());
            writer.flush();

            int responseCode = con.getResponseCode();

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                RESPONSE_LINE = response.toString();
                System.out.println("My HTTP responseCode: " + responseCode);
                System.out.println("My HTTP RESPONSE_LINE: " + RESPONSE_LINE);

                reqCallback.completionHandler(ENDPOINT, RESPONSE_LINE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        return null;
    }

    public interface CustomCallback {
        // call this function from AsyncTask
        // when ready to call back controller (fragment)
        // Object object: is something that you need to send to controller
        void completionHandler(String endpoint, String response);
    }

}

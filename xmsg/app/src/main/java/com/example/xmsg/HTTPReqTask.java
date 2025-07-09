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

import org.json.JSONObject;


public class HTTPReqTask extends AsyncTask<Void, Void, Void> {
    private static final String REQ_METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json";
    private static final String BASE_URL = "http://192.168.100.59:8001";


    // next are changeable data - specific for each request
    private String USER_ID = "not-user-id";
    private String USER_KEY = "not-sha1-hashsum";
    private String ENDPOINT = "/not-existing-endpoint";
    private String RESPONSE_LINE;


    @Override
    protected Void doInBackground(Void... params) {
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
                System.out.println(responseCode);
                System.out.println(RESPONSE_LINE);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Response Code: " + responseCode);
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

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public void setUSER_KEY(String USER_KEY) {
        this.USER_KEY = USER_KEY;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public String getRESPONSE_LINE() {
        return RESPONSE_LINE;
    }
}

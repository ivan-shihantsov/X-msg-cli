import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;


public class HelloWorld {
    private static final String CONTENT_TYPE = "application/json";
    private static final String BASE_URL = "http://192.168.100.59:8001";
    private static final String MY_USER_ID = "7";
	private static final String MY_PASS_HASH = "b1b3773a05c0ed0176787a4f1574ff0075f7521e";
	
    public static void main(String[] args) {
        System.out.println("start");
        String requestMethod = "POST";
        String url_line = BASE_URL + "/signin";

		HttpURLConnection con = null;

        try {
			String jsonInputString = "{\"user_id\": \"7\", \"key\": \"6ed66eac474e5379251d44b851edfa7e85432250\"}";
            byte[] postDataBytes = jsonInputString.getBytes("UTF-8");

            URL url = URI.create(url_line).toURL();

            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", CONTENT_TYPE);
            con.setRequestMethod(requestMethod);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(postDataBytes);

            int responseCode = con.getResponseCode();

            try(BufferedReader br = new BufferedReader(
					new InputStreamReader(con.getInputStream(), "utf-8"))) {
				String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
					System.out.println(responseLine);
                }
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

    }
}

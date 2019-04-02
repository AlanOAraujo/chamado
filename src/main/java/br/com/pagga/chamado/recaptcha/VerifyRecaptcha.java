package br.com.pagga.chamado.recaptcha;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import br.com.pagga.chamado.controller.PaggaController;

public class VerifyRecaptcha extends PaggaController {

	public static final String url = "https://www.google.com/recaptcha/api/siteverify";
	public static final String secret = "6Leh7o8UAAAAADo19CmyOBwqL6qMNkXi6Y9JxfTA";
	private final static String USER_AGENT = "Mozilla/5.0";
	
	public static boolean verify(String gRecaptchaResponse) throws IOException {
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}
		try{
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String postParams = "secret=" + secret + "&response="
					+ gRecaptchaResponse;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + postParams);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			String jsonText = response.toString();
			in.close();
			
			JSONObject json = new JSONObject(jsonText);
		    
		    return json.getBoolean("success");
			
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
}

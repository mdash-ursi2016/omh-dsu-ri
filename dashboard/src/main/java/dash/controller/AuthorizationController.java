package dash.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.lang.StringBuilder;
import java.net.URLEncoder;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.json.*;

import static dash.properties.ClientProperties.*;

@RestController
public class AuthorizationController {

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void processCode(@RequestParam(value = "code") String code,
			    HttpServletResponse response) throws Exception {

	// Request Access Token
	String target = createOAuthTokenUrl();
	String parameters = createOAuthTokenParameters(code);
	String serverResponse = sendTokenPost(target, parameters);

	// Prepare HttpServletResponse
	Cookie cookie = getCookie(serverResponse);
	response.addCookie(cookie);
	response.sendRedirect("https://mdash.cs.vassar.edu:8080/visualization");
    }



    private String sendTokenPost(String target, String parameters) {
	String authString = createEncodedAuthString();
	HttpsURLConnection connection = null;

	BufferedReader reader = null;
	StringBuilder builder = new StringBuilder();
	String line = null;

	try {
	    URL url = new URL(target);
	    connection = (HttpsURLConnection) url.openConnection();
	    connection.setRequestMethod("POST");
	    connection.setRequestProperty("Authorization", "Basic " + authString);
	    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    connection.setRequestProperty("Accept", "application/json");
	    connection.setRequestProperty("Cache-Control", "no-cache");
	    connection.setUseCaches(false);
	    connection.setDoOutput(true); // Output to send data in post
	    connection.setDoInput(true); // Input to retrieve data from server

	    // Write the body of the request
	    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
	    outputStream.writeBytes(parameters);
	    outputStream.flush();
	    outputStream.close();

	    // Connect
	    connection.connect(); // Necessary? Can I remove?

	    // Read body
	    InputStream inputStream = connection.getInputStream();
	    reader = new BufferedReader(new InputStreamReader(inputStream));
	    while ((line = reader.readLine()) != null) {
		builder.append(line + "\n");
	    }
	    reader.close();
	    return builder.toString();

	} catch (Exception e) {
	    return  e.getMessage();
	} finally {
	    if (connection != null) {
		connection.disconnect();
	    }
	}

    }


    private String createOAuthTokenUrl() {
	String url = "https://mdash.cs.vassar.edu:443/oauth/token";
	return url;
    }

    private String createOAuthTokenParameters(String code) {
	String parameters = "client_id=" + CLIENT_ID;
	parameters += "&client_secret=" + CLIENT_SECRET;
	parameters += "&grant_type=" + "authorization_code";
	parameters += "&code=" + code;
	parameters += "&redirect_uri=" + "https://mdash.cs.vassar.edu:8080/code";
	return parameters;
    }

    private String createEncodedAuthString() {
	Base64 base = new Base64();
	String authString = CLIENT_ID + ":" + CLIENT_SECRET;
	String authStringEnc = base.encodeBase64String(authString.getBytes());

	return authStringEnc;
    }

    private Cookie getCookie(String serverResponse) {
	JSONObject obj = new JSONObject(serverResponse);
	String value = null;
	if (obj.has("error")) {
	    value = obj.getString("error");
	    return new Cookie("error", value);
	} else if (obj.has("access_token")) {
	    value = obj.getString("access_token");
	    return new Cookie("token", value);
	} else {
	    return new Cookie("FAILURE", "Error in processing");
	}
    }

}

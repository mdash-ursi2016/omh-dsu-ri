package dash;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.lang.StringBuilder;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.json.*;

import static dash.properties.ClientProperties.*;

@RestController
public class AuthorizationController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {
	return "Greetings from Spring Boot! -- Says Alex";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@RequestParam(value = "name") String name, @RequestParam(value = "test") String tester) {
	return "The name is: " + name + " and the value is: " + tester;
    }

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void processCode(@RequestParam(value = "code", required = false) String code,
			    HttpServletResponse response) throws Exception {

	if (code == null) {
	    response.sendRedirect("http://143.229.6.40:8080/test?name=failure&test=No%20Code");
	} else {
	    // Request Access Token
	    String target = createOAuthTokenUrl();
	    String parameters = createOAuthTokenParameters(code);
	    String json = sendTokenPost(target, parameters);

	    // Prepare HttpServletResponse
	    Cookie cookie = getCookie(json);	    
	    response.addCookie(cookie);
	    response.sendRedirect("http://143.229.6.40:8080/access");
	}
    }


    
    private String sendTokenPost(String target, String parameters) {
	String authString = createEncodedAuthString();
	HttpURLConnection connection = null;

	BufferedReader reader = null;
	StringBuilder builder = new StringBuilder();
	String line = null;

	try {
	    URL url = new URL(target);
	    connection = (HttpURLConnection) url.openConnection();
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

	    System.out.println(builder.toString());
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
	String url = "http://143.229.6.40:80/oauth/token";
	return url;
    }

    private String createOAuthTokenParameters(String code) {
	String parameters = "client_id=" + CLIENT_ID;
	parameters += "&client_secret=" + CLIENT_SECRET;
	parameters += "&grant_type=" + "authorization_code";
	parameters += "&code=" + code;
	parameters += "&redirect_uri=" + "http://143.229.6.40:8080/code";
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
	    return new Cookie("failure", "Error in processing");
	}
	
    }

}

package dash;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

import static dash.properties.ClientProperties.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller // For RestController annotation, response is direct, as opposed to a direction to a template
public class DashboardController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String rootHome() {
	return "index";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(){
	return "about";
    }

    @RequestMapping(value = "/visualization", method = RequestMethod.GET)
    public String authorizeClient() {
	String url = createOAuthAuthorizeUrl();
	return "redirect:" + url;
    }

    @RequestMapping(value = "/access", method = RequestMethod.GET)
    public String getDash(HttpServletRequest request) {
	System.out.println("GetRemoteUser: " + request.getRemoteUser());
	return "visualization";
    }

    private String createOAuthAuthorizeUrl(){
	String url = "http://143.229.6.40/oauth/authorize";
	url += "?response_type=code";
	url += "&client_id=" + CLIENT_ID;
	url += "&redirect_uri=http://143.229.6.40:8080/code";
	url += "&scope=read_data_points%20write_data_points%20delete_data_points";
	return url;
    }

}

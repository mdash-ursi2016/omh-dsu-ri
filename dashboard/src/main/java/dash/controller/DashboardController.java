package dash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;

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

    @RequestMapping(value = "/access", method = RequestMethod.GET)
    public String authorizeClient() {
	String url = createOAuthAuthorizeUrl();
	return "redirect:" + url;
    }

    @RequestMapping(value = "/visualization", method = RequestMethod.GET)
    public String getDash(HttpServletRequest request, Model model) {
	try {
	    Cookie[] cookies = request.getCookies();
	    String val = null;
	    for (int i=0; i<cookies.length; i++) {
		if (cookies[i].getName().equals("token")) {
		    model.addAttribute("present", "true");
		}
	    }
	} catch (Exception e) {
	}
	return "visualization";
    }

    private String createOAuthAuthorizeUrl(){
	String url = "https://mdash.cs.vassar.edu:443/oauth/authorize";
	url += "?response_type=code";
	url += "&client_id=" + CLIENT_ID;
	url += "&redirect_uri=https://mdash.cs.vassar.edu:8080/code";
	url += "&scope=read_data_points%20write_data_points%20delete_data_points";
	return url;
    }

}

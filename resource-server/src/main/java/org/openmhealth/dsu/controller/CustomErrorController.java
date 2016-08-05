package org.openmhealth.dsu.controller;

import org.openmhealth.dsu.domain.ErrorObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class CustomErrorController implements ErrorController {
    static final String PATH = "/error";

    private boolean debug = true;

        @Autowired
	private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH, method = GET)
    public String error(HttpServletRequest request, HttpServletResponse response, ModelMap mod) {
	ErrorObject err =  new ErrorObject(response.getStatus(), getErrorAttributes(request, debug));
	mod.addAttribute("error", err.get());
	return "error";
    }

        @Override
	public String getErrorPath() {
	    return PATH;
	}

    private Map<String,Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
	RequestAttributes requestAttributes = new ServletRequestAttributes(request);
	return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}

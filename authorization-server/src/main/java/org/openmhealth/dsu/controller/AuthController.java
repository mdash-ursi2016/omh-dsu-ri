package org.openmhealth.dsu.controller;

import org.openmhealth.dsu.domain.EndUser;
import org.openmhealth.dsu.repository.EndUserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import static org.springframework.http.HttpStatus.*;

import java.util.Optional;

@Controller
public class AuthController {

    EndUserRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String rootHome() {
	return "index";
    }
    
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(){
	return "about";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
	return "login";
    }
    
    @RequestMapping(value = "/client_registration", method = RequestMethod.GET)
    public String clientSignup() {
	return "client_registration";
    }

    @RequestMapping(value = "/client_registration", method = RequestMethod.POST)
    public ResponseEntity<?> clientRegistration() {
	//This is where a client is created
	return new ResponseEntity<>(CREATED);
    }
    
}

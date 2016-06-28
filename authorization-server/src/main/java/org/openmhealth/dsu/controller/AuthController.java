package org.openmhealth.dsu.configuration;

import org.openmhealth.dsu.domain.EndUser;
import org.openmhealth.dsu.repository.EndUserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import static org.springframework.http.HttpStatus.*;

import java.util.Optional;

@Controller
public class AuthController {
    EndUserRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
	return "index";
    }

    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
	return "index";
    }    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
	return "login";
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String clientSignup() {
	return "client";
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public ResponseEntity<?> clientRegistration() {
	//This is where a client is created
	return new ResponseEntity<>(CREATED);
    }
    
}

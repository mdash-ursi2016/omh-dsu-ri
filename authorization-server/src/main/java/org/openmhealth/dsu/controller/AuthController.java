package org.openmhealth.dsu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.*;

import java.util.Optional;

@Controller
public class AuthController {

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String clientSignup() {
	return "client_registration";
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public ResponseEntity<?> clientRegistration() {
	//This is where a client is created
	return new ResponseEntity<>(CREATED);
    }
    
}

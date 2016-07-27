package org.openmhealth.dsu.controller;

import org.openmhealth.dsu.domain.ClientRegistrationData;
import org.openmhealth.dsu.domain.SimpleClientDetails;
import org.openmhealth.dsu.service.SpringDataClientDetailsServiceImpl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.openmhealth.dsu.configuration.OAuth2Properties.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Set;
import java.util.List;

/**
 * A controller that manages client accounts
 * Based on EndUserController by Emerson Farrugia
 **/

@Controller
public class ClientController {
    @Autowired
    private Validator validator;

    @Autowired
    private ClientRegistrationService service;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    /**
     * Directs clients to the client registration page
     *
     * @return a String for the client_registration template
     **/    
    @RequestMapping(value = "/clients", method = GET)
    public String clientSignup() {
	return "client_registration";
    }


    /**
     * Registers a new client.
     *
     * @param clientDetails the clientDetails of the client
     * @return a response entity with status OK if the client is registered
     * or CONFLICT if the client exists
     **/
    @RequestMapping(value = "/clients", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> clientRegistration(@RequestBody ClientRegistrationData registrationData) {
	BaseClientDetails baseClientDetails = createClientDetails(registrationData);
	
	if (baseClientDetails == null) {
	    return new ResponseEntity<>(BAD_REQUEST);
	}

	Set<ConstraintViolation<ClientRegistrationData>> constraintViolations = validator.validate(registrationData);

	if (!constraintViolations.isEmpty()) {
	    return new ResponseEntity<>(asErrorMessageList(constraintViolations), BAD_REQUEST);
	}
	
	try { // Does not currently work. Saves to mongodb
	    service.addClientDetails(baseClientDetails);
	    return new ResponseEntity<>(baseClientDetails.getClientSecret(), CREATED);
	} catch (Exception e) {
	    return new ResponseEntity<>(CONFLICT);
	}
    }

    /**
     * From EndUserController (by Emerson Farrugia)
     **/
    protected List<String> asErrorMessageList(Set<ConstraintViolation<ClientRegistrationData>> constraintViolations) {
	return constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }

    /**
     * Creates ClientDetails with given ClientRegistrationData
     * @param registrationData input registration data
     * @return ClientDetails created with the registration data
     **/
    private BaseClientDetails createClientDetails(ClientRegistrationData registrationData) {
	if (registrationData == null) {
	    return null;
	}

	String clientId = registrationData.getId();
	String resourceId = DATA_POINT_RESOURCE_ID;
	String scopes = DATA_POINT_READ_SCOPE + ", " + DATA_POINT_WRITE_SCOPE;
	String grantTypes = null; 
	if (registrationData.isMobileApp()){
	    grantTypes = "implicit";
	} else {
	    grantTypes = "authorization_code";
	}
	String authorities = CLIENT_ROLE;

	BaseClientDetails baseClientDetails = new BaseClientDetails(clientId,resourceId,scopes,grantTypes,authorities);
	baseClientDetails.setClientSecret(passwordEncoder.encode(registrationData.getPassword()));
	return baseClientDetails;
    }
}

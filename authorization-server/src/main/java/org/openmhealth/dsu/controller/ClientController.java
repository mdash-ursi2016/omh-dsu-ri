package org.openmhealth.dsu.controller;

import org.openmhealth.dsu.domain.ClientRegistrationData;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

/**
 * A controller that manages client accounts
 * Based on EndUserController by Emerson Farrugia
 **/

@Controller
public class ClientController {
    @Autowired
    private Validator validator;

    @Autowired
    private SpringDataClientDetailsServiceImpl clientDetailsService;

    
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
	ClientDetails clientDetails = createClientDetails(registrationData);
	
	if (clientDetails == null) {
	    return new ResponseEntity<>(BAD_REQUEST);
	}

	Set<ConstraintViolation<ClientDetails>> constraintViolations = validator.validate(clientDetails);

	if (!constraintViolations.isEmpty()) {
	    return new ResponseEntity<>(asErrorMessageList(constraintViolations), BAD_REQUEST);
	}

	SimpleClientDetails client = new SimpleClientDetails();
	try {
	    client = clientDetailsService.loadClientByClientId(clientDetails.getClientId());
	} catch (Exception e) {
	    clientDetailsService.addClientDetails(clientDetails);
	    return new ResponseEntity<>(CREATED);
	}
	
	return new ResponseEntity<>(CONFLICT);
    }

    /**
     * From EndUserController (by Emerson Farrugia)
     **/
    protected List<String> asErrorMessageList(Set<ConstraintViolation<EndUserRegistrationData>> constraintViolations) {
	return constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }

    /**
     * Creates ClientDetails with given ClientRegistrationData
     * @param registrationData input registration data
     * @return ClientDetails created with the registration data
     **/
    private ClientDetails createClientDetails(ClientRegistrationData registrationData) {
	if (registrationData == null) {
	    return null;
	}
	
	BaseClientDetails clientDetails = new BaseClientDetails();
	clientDetails.setClientId(registrationData.getId());
	clientDetails.setRegisteredRedirectUri(registrationData.getRedirectUri());

	/* Collection<String> grantTypes */
	if (registrationData.isMobileApp()) {
	    /* grantTypes = implicit */
	} else {
	    /* grantTypes = authorizationCode */
	}
	clientDetails.setAuthorizedGrantTypes(grantTypes);
	
	/*
	setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds)
	    voidsetAdditionalInformation(Map<String,?> additionalInformation)
	    voidsetAuthorities(Collection<? extends org.springframework.security.core.GrantedAuthority> authorities)
	    voidsetAuthorizedGrantTypes(Collection<String> authorizedGrantTypes)
	    voidsetAutoApproveScopes(Collection<String> autoApproveScopes)
	    voidsetClientId(String clientId)
	    voidsetClientSecret(String clientSecret)
	    voidsetRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds)
	    voidsetRegisteredRedirectUri(Set<String> registeredRedirectUris)
	    voidsetResourceIds(Collection<String> resourceIds)
	    voidsetScope(Collection<String> scope) 
	*/
	
	return clientDetails;
    }
}

/*
 * Copyright 2014 Open mHealth
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openmhealth.dsu.controller;


import org.openmhealth.dsu.domain.EndUserRegistrationData;
import org.openmhealth.dsu.service.EndUserService;
import org.openmhealth.dsu.domain.EndUser;
import org.openmhealth.dsu.domain.EndUserUserDetails;
import org.openmhealth.dsu.repository.EndUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * A controller that manages user accounts.
 *
 * @author Emerson Farrugia
 */
@Controller
public class EndUserController {

    @Autowired
    private Validator validator;

    @Autowired
    private EndUserService endUserService;

    @Autowired
    private EndUserRepository repository;
    
    /**
     * Directs users to a login page
     * @return a String for the login page
     */
    @RequestMapping(value = "/login", method = GET)
    public String login() {
	return "login";
    }

    /**
    * Directs users to the signup page   
    * @return a String for the signup page
    */
    @RequestMapping(value = "/users", method = GET)
    public String signup() {
        return "user_registration";
      }


    /**
     * Directs users to the visualizations page
     * @return a String for the visualizations page
     
    @PreAuthorize("#oauth2.isUser()")
    @RequestMapping(value = "/users/visualization", method = GET)
    public String visualization(Authentication auth, ModelMap model) {
	Optional<EndUser> user = repository.findOne(auth.getName());
	model.addAttribute("user", user.get());
	return "users/visualization";
    }
    */
    
    /**
     * Registers a new user.
     *
     * @param registrationData the registration data of the user
     * @return a response entity with status OK if the user is registered, BAD_REQUEST if the request is invalid,
     * or CONFLICT if the user exists
     */
    @RequestMapping(value = "/users", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody EndUserRegistrationData registrationData) {

        if (registrationData == null) {
            return new ResponseEntity<>("Bad request, this is null", BAD_REQUEST);
        }

        Set<ConstraintViolation<EndUserRegistrationData>> constraintViolations = validator.validate(registrationData);

        if (!constraintViolations.isEmpty()) {
            //return new ResponseEntity<>(asErrorMessageList(constraintViolations), BAD_REQUEST);
	    return new ResponseEntity<>("Constraint Violation", BAD_REQUEST);
	}

        if (endUserService.doesUserExist(registrationData.getUsername()) ||
	    endUserService.doesEmailExist(registrationData.getEmailAddress())) {
            return new ResponseEntity<>("Bad request", CONFLICT);
        }

        endUserService.registerUser(registrationData);

        return new ResponseEntity<>("Created", CREATED);
    }

    protected List<String> asErrorMessageList(Set<ConstraintViolation<EndUserRegistrationData>> constraintViolations) {

        return constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }

    /**
     * Deletes an existing user.
     * @param authentication
     * @return a response entity with status OK if the user was deleted, BAD_REQUEST if the request is invalid
     */
    @PreAuthorize("#oath2.isUser()")
    @RequestMapping(value = "/users/del", method = POST)
    public ResponseEntity<?> deleteUser(Authentication authentication) {
	String user = ((EndUserUserDetails) authentication.getPrincipal()).getUsername();
	endUserService.delete(user);
	return new ResponseEntity<>("Success",OK);
    }
}

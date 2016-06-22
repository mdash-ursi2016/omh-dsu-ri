package org.openmhealth.dsu.configuration;

import org.openmhealth.dsu.domain.EndUser;
import org.openmhealth.dsu.repository.EndUserRepository;
import org.springframework.stereotype.Controller;
import org. springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
public class AuthController {
    EndUserRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
	return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Authentication auth, Model mod) {
	Optional<EndUser> user = repository.findOne(auth.getName());
	mod.addAttribute("user", user);
	return "index";
    }    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
	return "login";
    }
    
}

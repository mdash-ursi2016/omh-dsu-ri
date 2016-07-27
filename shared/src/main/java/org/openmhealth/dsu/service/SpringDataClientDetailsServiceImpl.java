package org.openmhealth.dsu.service;

import org.openmhealth.dsu.domain.SimpleClientDetails;
import org.openmhealth.dsu.repository.ClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static java.lang.String.format;


/**
 * An implementation of Spring Security's {@link ClientDetailsService} and {@link ClientRegistrationService} that
 * persists OAuth 2.0 client details using Spring Data repositories.
 *
 * @author Emerson Farrugia
 */
@Service
public class SpringDataClientDetailsServiceImpl implements ClientDetailsService {

        @Autowired
	private ClientDetailsRepository repository;

        @Override
	@Transactional(readOnly = true)
	public SimpleClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

	    return repository.findOne(clientId).orElseThrow(() ->
							    new NoSuchClientException(format("A client with id '%s' wasn't found.", clientId)));
	}

}

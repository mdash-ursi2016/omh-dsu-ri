package org.openmhealth.dsu.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
    
/*
 * A WebMvcConfigurerAdapter that includes CORS Mappings
 */

@Configuration
//@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

        @Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/v1.0.M1/dash/**")
		.allowedOrigins("http://143.229.6.40:8080")
		.allowedMethods("GET")
		.allowedHeaders("Accept", "Cache-Control", "Authorization")
		.allowCredentials(true).maxAge(3600)
		.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Methods", "Access-Control-Allow-Headers", "Access-Control-Allow-Credentials");
	}
}

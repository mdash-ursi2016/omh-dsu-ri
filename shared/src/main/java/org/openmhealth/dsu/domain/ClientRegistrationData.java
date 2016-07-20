package org.openmhealth.dsu.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * A bean containing client registration data.
 *
 **/
public class ClientRegistrationData {
    
    private String id;
    private String redirectUri;
    private boolean isMobileApp;

    @NotNull
    @Size(min = 1)
    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @NotNull
    @Size(min = 7) // http://
    public String getRedirectUri() {
	return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
	this.redirectUri = redirectUri;
    }

    public boolean isMobileApp() {
	return isMobileApp;
    }

    public void setIsMobileApp(boolean value) {
	this.isMobileApp = value;
    }


}

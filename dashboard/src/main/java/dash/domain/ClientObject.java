package dash.domain;

import java.util.Map;

// Temporary fix to having client properties stored
public class ClientObject {
    public String id;
    public String secret;
    public String token;

    public ClientObject(String clientId, String clientSecret) {
	this.id = clientId;
	this.secret = clientSecret;
    }

    public ClientObject(String clientId, String clientSecret, String clientToken) {
	this.id = clientId;
	this.secret = clientSecret;
	this.token = clientToken;
    }
    
    public void setToken(String clientToken) {
	this.token = clientToken;
    }


    public String getToken() {
	return this.token;
    }
}


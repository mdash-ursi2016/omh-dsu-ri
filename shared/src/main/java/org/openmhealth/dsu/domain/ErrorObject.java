package org.openmhealth.dsu.domain;

import java.util.Map;

public class ErrorObject {
    public Integer status;
    public String name;
    public String message;
    public String timeStamp;
    public String trace;
    
    public ErrorObject(int status, Map<String,Object> errorAttributes) {
	this.status = status;
	this.name = (String) errorAttributes.get("error");
	this.message = (String) errorAttributes.get("message");
	this.timeStamp = errorAttributes.get("timestamp").toString();
	this.trace = (String) errorAttributes.get("trace");
    }

    public ErrorObject get() {
	return this;
    }

}

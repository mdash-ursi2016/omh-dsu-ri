newClient = function() {
    var data = JSON.stringify({
	"id": id.value,
	"password":password.value,
	"redirect_uri": redirectUri.value,
	"is_mobile_app": form.elements["isMobileApp"].checked
    });
    
    document.getElementById("res").innerHTML = data;
    
    /*
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    xhr.addEventListener("readystatechange", function() {
	if (this.readyState===4) {
	if (this.status===200) {
	document.getElementById("res").innerHTML = "Use your input client id and this client secret in your grant flow: <br/>" + this.responseText;
	} else {
	document.getElementById("res").innerHTML = this.statusText;
	document.getElementById("res").innerHTML += " : " + this.responseText;
	}
    });

    xhr.open("POST", "https://mdash.cs.vassar.edu:80/clients");
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Cache-Control", "no-cache");
    //xhr.setRequestHeader(header, token);
    xhr.send(data);
    */
    
    document.getElementById("form").reset();
}



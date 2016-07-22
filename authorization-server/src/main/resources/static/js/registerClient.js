newClient = function() {
    var data = JSON.stringify({
	"id": id.value,
	"redirectUri": redirectUri.value,
	"isMobileApp": form.elements["isMobileApp"].checked
    });
    
    document.getElementById("test2").innerHTML = data;
    
    /*
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    xhr.addEventListener("readystatechange", function() {
	if (this.readyState===4) {
	    document.getElementById("res").innerHTML = this.statusText;
	}
    });

    xhr.open("POST", "http://143.229.6.40:80/clients");
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Cache-Control", "no-cache");
    //xhr.setRequestHeader(header, token);
    xhr.send(data);
    */
    
    document.getElementById("form").reset();
}



//var token = $("meta[name='_csrf']").attr("content");
//var header = $("meta[name='_csrf_header']").attr("content");

newUser = function() {
    var data = JSON.stringify({
	"username": username.value,
	"password": password.value,
	"email_address": emailAddress.value
    });
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    xhr.addEventListener("readystatechange", function () {
	if (this.readyState===4) {
	    document.getElementById("res").innerHTML = this.statusText;
	}
    });
    xhr.open("POST", "http://143.229.6.40:80/users");
    xhr.setRequestHeader("accept", "application/json");
    xhr.setRequestHeader("content-type", "application/json");
    xhr.setRequestHeader("cache-control", "no-cache");
    //xhr.setRequestHeader(header, token);
    xhr.send(data);

    document.getElementById("form").reset();
}

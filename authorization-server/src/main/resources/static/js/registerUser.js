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
    xhr.open("POST", "https://mdash.cs.vassar.edu:80/users");
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Cache-Control", "no-cache");
    //xhr.setRequestHeader(header, token);
    xhr.send(data);

    document.getElementById("form").reset();
}

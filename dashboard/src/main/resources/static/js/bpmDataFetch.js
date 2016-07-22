var thisVar = setInterval(getBpmData, 2000);

function getByName(name) {
    var cookies = document.cookie.split('; ');

    for(var i=0; i<cookies.length; i++){
	var cookiePair = cookies[i].split('=');
	if (cookiePair[0] == name) {
	    return cookiePair[1];
	}
    }
    return "";
}

function getBpmData() {
    // Create new XMLHttpRequest for the resource server
    var xhr = new XMLHttpRequest();
    // Initialize response for the dashboard
    var res = {};
    // Use current time minus 2 seconds for the date parameter of the url
    var date = new Date().getTime();
    date = new Date(date-30000).toISOString();

    var url = "http://143.229.6.40:443/v1.0.M1/dash/heartRate?created_on_or_after=" + date;

    // When the request has completed (readyState === 4), parse the responseText from the server and getBPM
    xhr.withCredentials = true;
    xhr.addEventListener("readystatechange", function () {
	if (this.readyState===4) {
	    res = JSON.parse(xhr.responseText);
	    getBPM(res);
	}});

    // Send request with the necessary headers
    xhr.open("GET", url, true);
    xhr.setRequestHeader("accept", "application/json");
    xhr.setRequestHeader("Authorization", "Bearer " + getByName("token"));
    xhr.setRequestHeader("cache-control", "no-cache");
    xhr.send();
}

// Function that reads the heart_rate values from the response
function getBPM(dataPoints) {
    if (dataPoints.length>0) {
	for (var i=0; i<dataPoints.length; i++) {	
	    document.getElementById("content").innerHTML = dataPoints[i].body.heart_rate.value + " bpm";
	}
    } else {
	document.getElementById("content").innerHTML = "-- bpm";
    }
}


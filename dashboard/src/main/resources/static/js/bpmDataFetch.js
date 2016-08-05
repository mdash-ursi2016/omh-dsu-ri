if (getByName("token")){
    var thisVar = setInterval(getBpmData, 2000);
}

function getByName(name) {
    let cookies = document.cookie.split('; ');

    for(let i=0; i<cookies.length; i++){
	let cookiePair = cookies[i].split('=');
	if (cookiePair[0] == name) {
	    return cookiePair[1];
	}
    }
    return null;
}

function getBpmData() {
    // Create new XMLHttpRequest for the resource server
    let xhr = new XMLHttpRequest();
    // Initialize response for the dashboard
    let res = {};
    // Use current time minus 2 seconds for the date parameter of the url
    let date = new Date().getTime();
    date = new Date(date-2000+(240*60000)).toISOString(); // Had to adjust for creation date times in UTC

    let url = "https://mdash.cs.vassar.edu:8083/v1.0.M1/dash/heartRate?created_on_or_after=" + date;

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
	for (let i=0; i<dataPoints.length; i++) {	
	    document.getElementById("bpm-box").innerHTML = dataPoints[i].body.heart_rate.value + " bpm";
	}
    } else {
	document.getElementById("bpm-box").innerHTML = "-- bpm";
    }
}


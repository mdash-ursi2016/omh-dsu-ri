function getByName(name) {

    let cookies = document.cookie.split('; ');

    for(let i=0; i<cookies.length; i++){
	let cookiePair = cookies[i].split('=');
	if (cookiePair[0] == name) {
	    return cookiePair[1];
	}
    }
    return "";
}

function getDataAndChart(callback) {
    // Create new XMLHttpRequest for the resource server
    let xhr = new XMLHttpRequest();
    // Initialize response for the dashboard
    let res = {};
    let val = chart_form.elements["schema_name"].value.split("_"); // heart_rate -> ["heart","rate"]
    
    let url = "http://143.229.6.40:443/v1.0.M1/dash/dataPoints?schema_namespace=omh&schema_name="
	+ val[0] + "-" + val[1] + "&schema_version=1.0" // "heart-rate"

    // When the request has completed (readyState === 4), parse the responseText from the server and getBPM
    xhr.withCredentials = true;
    xhr.addEventListener("readystatechange", function () {
	if (this.readyState===4) {
	    res = JSON.parse(xhr.responseText);
	    callback(res);
	}});

    // Send request with the necessary headers
    xhr.open("GET", url, true);
    xhr.setRequestHeader("accept", "application/json");
    xhr.setRequestHeader("Authorization", "Bearer " + getByName("token"));
    xhr.setRequestHeader("cache-control", "no-cache");
    xhr.send();
}


// Function that reads the heart_rate values from the response
function printData(dataPoints) {
    document.getElementById("test3").innerHTML = "";
    if (dataPoints.length>0) {
	for (let i=0; i<dataPoints.length; i++) {
	    document.getElementById("test3").innerHTML = JSON.stringify(dataPoints[i]);
	}
    } else {
	document.getElementById("test3").innerHTML = "no data?";
    }
}


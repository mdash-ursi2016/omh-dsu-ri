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

function getDataAndChart(callback) {
    let token = getByName("token");
    
    if (token) {
	// Create new XMLHttpRequest for the resource server
	let xhr = new XMLHttpRequest();
	// Initialize response for the dashboard
	let res = {};
	let measureSplit = chart_form.elements["schema_name"].value.split("_"); // heart_rate -> ["heart","rate"]
	let name = "";
	for (let i = 0; i < measureSplit.length; i++) {
	    if (i < measureSplit.length-1) {
		name += measureSplit[i] + "-";
	    } else { // i == len-1
		name += measureSplit[i];
	    }
	}
	
	
	let url = "https://mdash.cs.vassar.edu:8083/v1.0.M1/dash/dataPoints?schema_namespace=omh&schema_name="
	    + name + "&schema_version=1.0" // "heart-rate"
	
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
	xhr.setRequestHeader("Authorization", "Bearer " + token);
	xhr.setRequestHeader("cache-control", "no-cache");
	xhr.send();
    }
}

function getMax(dataPoints) {
    let max = 0;
    if (dataPoints.length>0) {
    let name = dataPoints[0].header.schema_id.name;
    let len = dataPoints.length;
    let val = 0;
    for (let i=0; i<len; i++) {
	if (name === "step-count") {
	    val = dataPoints[i].body.step_count;
	}
	if (name === "heart-rate") {
	    val = dataPoints[i].body.heart_rate.value;
	}
	if (name === "minutes-moderate-activity") {
	    val = dataPoints[i].body.minutes_moderate_activity.value;
	}
	max = (max <= val) ? val : max;
    }
    }
    return max;
}

function createMeasureName(measureSplitArray) {
    let name = "";
    let len = measureSplitArray.length;
    for (let i = 0; i < len; i++) {
	if (i < len-1) {
	    name += measureSplitArray[i] + "-";
	} else { // i == len-1
	    name += measureSplitArray[i];
	}
    }
}



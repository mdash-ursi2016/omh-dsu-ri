var thisVar = setInterval(getData, 2000);


function getData() {
    // Create new XMLHttpRequest for the resource server
    var xhr = new XMLHttpRequest();
    // Initialize response for the dashboard
    var res = {};
    // Use current time minus 2 seconds for the date parameter of the url
    var date = new Date().getTime();
    date = new Date(date-2000).toISOString();

    var url = "http://143.229.6.40:443/v1.0.M1/dataPoints?schema_namespace=omh&schema_name=heart-rate&schema_version=1.0&created_on_or_after=" + date;

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
    //xhr.setRequestHeader("authorization", "Bearer 4805b0b6-8036-438a-aec7-9ed6f593e82e");
    xhr.setRequestHeader("cache-control", "no-cache");
    xhr.send();
}

// Function that reads the heart_rate values from the last object in the response array
function getBPM(arr) {
    len = arr.length;
    if (len>0) {
	document.getElementById("content").innerHTML = arr[len-1].body.heart_rate.value;
    } else {
	document.getElementById("content").innerHTML = "-- BPM";
    }
}


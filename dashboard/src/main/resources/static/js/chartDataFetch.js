/* ====== Based on OMH Web Visualizations ====== */

/* ====== Variable Definitions ====== */
var chart = null;
var measure = chart_form.elements['schema_name'].value;
var loadingMessage = d3.select( '.loading_message' );
var dataPointDetails = d3.select( '.dataPoint_details' );
var clickInteraction = null;
var clickInteractionComponent = null;

// Custom chart appearance
var settings = {
    'interface': {
	'axes': {
	    'yAxis': {
		'visible': true
	    },
	    'xAxis': {
		'visible': true
	    }
	},
	'tooltips': {
	    'decimalPlaces': 4
	}
    },
    'measures': {
	'heart-rate': {
	    'seriesName': 'Heart Rate',
	    'yAxis': {
		'range': { 'min': 0, 'max': 255 }
	    },
	    'chart': {
		'styles': [
		    {
			'name': 'blue-lines',
			'plotType': 'Line',
			'attributes': {
			    'stroke': '#4BC0C0'
			}
		    }
		]
	    }
	},
	'step-count': {
	    'seriesName': 'Step Count',
	    'yAxis' : {
		'range': undefined
	    },
	    'data': {
		'xValueQuantization': {
		    'period': OMHWebVisualizations.DataParser.QUANTIZE_MONTH
		}
	    },
	    'chart': {
		'type': 'Line',
		'daysShownOnTimeline': undefined
	    }
	}
    }
};


/* ====== UI Helper Functions ====== */

function cloneObject ( object ) {

    return JSON.parse( JSON.stringify( object ) );
}

function hideLoadingMessage() {

    loadingMessage.classed( 'hidden', true );
}

function showLoadingMessage() {

    loadingMessage.html( 'Loading data... ' );
}

function showLoadingError( error ) {
    loadingMessage.classed( 'hidden', false );
    loadingMessage.html( 'There was an error while trying to load the data: <pre>' +
			 JSON.stringify( error ) + '</pre>' );
}

function hideChart() {

    d3.select( '.demo_chart' ).classed( 'hidden', true );
}

function showChart() {

    d3.select( '.demo_chart' ).classed( 'hidden', false );
}

function disableUI() {
    d3.select( '.schema_name' ).property( 'disabled', true );
    d3.select( '.select_button' ).property( 'disabled', true );
}

function enableUI() {
    d3.select( '.schema_name' ).property( 'disabled', false );
    d3.select( '.select_button' ).property( 'disabled', false );
}

function showDatapointDetailsMessage( message ) {

    dataPointDetails.html( '<h3>Data Point Details</h3> ' + message );
}
*/

function getCookie( name ) {
    var cookies = document.cookie.split('; ');
    
    for(var i=0; i<cookies.length; i++){
	var cookiePair = cookies[i].split('=');
	if (cookiePair[0] == name) {
	    return cookiePair[1];
	}
    }
    return "";
}

function getChartData() {
    // Create new XMLHttpRequest for the resource server
    let xhr = new XMLHttpRequest();
    // Initialize response for the dashboard
    let res = {};

    let url = 'http://143.229.6.40:443/v1.0.M1/dash/dataPoints?schema_namespace=omh&schema_name='
        + measure
        + '&schema_version=1.0';

    // When the request has completed (readyState === 4), parse the responseText from the server and getBPM
    xhr.withCredentials = true;
    xhr.addEventListener("readystatechange", function () {
	if (this.readyState<4) {
	    showLoadingMessage();
	} else {
	    if (this.status === 200) {
		res = JSON.parse(xhr.responseText);
		printData(res);
	    } else {
		d3.select('.test2').html = xhr.responseText;
		res = {error: xhr.responseText};
	    }
	    return res;
	}
    }
			);

    // Send request with the necessary headers
    xhr.open("GET", url, true);
    xhr.setRequestHeader("accept", "application/json");
    xhr.setRequestHeader("Authorization", "Bearer " + getCookie("token"));
    xhr.setRequestHeader("cache-control", "no-cache");
    xhr.send();
}

// Prints data in test1
function printData( dataPoints ) {
    if (dataPoints.length>0) {
	for (var i=0; i<dataPoints.length; i++) {
	    d3.select('.test1').html = dataPoints[i] + ' a data point';
	}
    } else {
	d3.select('.test1').html = 'no data?';
    }
}


/* ====== Chart Construction ====== */

function customizeChartComponents( components ) {


}

function chartData() {
    let data = getChartData();
    let chartSettings = cloneObject(settings);

    if (measure === "heart-rate") {
	chartSettings.interface.tooltips.visible = false;
    }

    disableUI();

    if (data.hasOwnProperty('error')) {
	hideChart();
	showLoadingError(data.error);
	enableUI();
    } else {
	makeChart(data, chartSettings);
    }
}

function makeChart( data, chartSettings ) {
    let element = d3.select('.demo_chart_container');

    // Data points should either be an array or a JSON object {body: [array_of_dataPoints]}
    if (data.hasOwnProperty( 'body' )) {
	data = data.body;
    }

    // Destroy chart if one is already present
    if (chart) {
	chart.destroy();
	if (clickInteraction && clickInteractionComponent) {
	    clickInteraction.detachFrom( clickInteractionComponent );
	}
    }

    // Build new chart
    chart = new OMHWebVisualizations.Chart(data, element, measure, configSettings);
    if (chart.initialized) {
	hideLoadingMessage();
	//customizeChartComponents(chart.getComponents());
	showChart();
	chart.renderTo(element.select('svg').node());
    } else {
	hideChart();
	showLoadingError("Chart could not be initialized. There's  a glitch in the matrix!");
    }
    enableUI();
}
*/

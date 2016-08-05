/* Modified version of Emerson Farrugia's example visualization */

function updateDatapointDetails( datum ) {
    // use the replacer to hide fields from the output
    let replacer = function ( key, value ) {
	if ( key === "groupName" ) {
	    return undefined;
	} else {
	    return value;
	}
    };
    if ( datum.aggregationType && datum.aggregatedData) {
	// if the point is an aggregation of more than one point
	// then display the aggregation type and the points that were used
	let dataString = "";
	for ( let i in datum.aggregatedData ) {
	    dataString += JSON.stringify( datum.aggregatedData[ i ].body, replacer, 4 ) + "\n\n";
	    let datumLength = datum.aggregatedData.length - 1;
	    if (i < datumLength) {
		dataString += "<hr>\n";
	    }
	}
	document.getElementById("dataPoint_details").innerHTML = "<h3>Data Point Details: " + datum.aggregationType + " of points</h3> " + dataString;
    } else {
	// otherwise just show the point
	document.getElementById("dataPoint_details").innerHTML = "<h3>Data Point Details: single point</h3> " +
	    JSON.stringify( datum.omhDatum.body, replacer, 4);
    }
}

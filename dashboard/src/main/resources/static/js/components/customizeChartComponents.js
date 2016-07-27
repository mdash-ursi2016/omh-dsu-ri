function customizeChartComponents( components ) {
    //move any label overlayed on the bottom right
    //of the chart up to the top left
    var plots = components.plots;
    plots.forEach( function ( component ) {

	if ( component instanceof Plottable.Components.Label &&
	     (component.yAlignment() === 'bottom') &&
	     (component.xAlignment() === 'right') ) {
	    component.yAlignment( 'top' );
	    component.xAlignment( 'left' );
	}

	if ( component instanceof Plottable.Plots.Scatter && (component.datasets().length > 0) ) {
	    scatterPlot = component;
	    if ( !clickInteraction ) {
		clickInteraction = new Plottable.Interactions.Click()
		    .onClick( function ( point ) {
			var nearestEntity;
			try {
			    nearestEntity = scatterPlot.entityNearest( point );
			    updateDatapointDetails( nearestEntity.datum );
			} catch ( e ) {
			    return;
			}
		    } );
	    }

	    clickInteraction.attachTo( scatterPlot );
	    clickInteractionComponent = scatterPlot;
	    showDatapointDetailsMessage( 'Click on a point to see details here...' );
	}
    } );
}

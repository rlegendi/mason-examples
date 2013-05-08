package ai.aitia.contrib.mason.demo;

import javax.swing.JFrame;

import org.jfree.data.xy.XYSeries;

import sim.display.Console;
import sim.display.Controller;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.portrayal.Inspector;
import sim.util.media.chart.ChartGenerator;
import sim.util.media.chart.TimeSeriesChartGenerator;

/**
 * @author rlegendi
 */
public class ElFarolGui
		extends GUIState
		implements Steppable {
	
	private static final long serialVersionUID = 1L;
	
	public JFrame displayFrame = null;
	private TimeSeriesChartGenerator attendance;
	
	public ElFarolGui() {
		super( new ElFarol( System.currentTimeMillis() ) );
	}
	
	@Override
	public void step(final SimState state) {
		// Update the displayed graph
		displayFrame.repaint();
	}
	
	@Override
	public void init(final Controller controller) {
		super.init( controller );
		
		displayFrame = new JFrame( "El Farol Statistics" );
		controller.registerFrame( displayFrame );
		
		attendance = new TimeSeriesChartGenerator();
		setMetadata( attendance, "Consumption", "Time", "consumption" );
		displayFrame.add( attendance.getChartPanel() );
		
		displayFrame.pack();
		displayFrame.setLocationByPlatform( true );
		displayFrame.setVisible( true ); // We are in the event dispatch thread
	}
	
	@Override
	public void start() {
		super.start();
		setupPortroyals();
		
		final double startTime = 0.0; // Start in the first tick
		final int order = Integer.MAX_VALUE; // After other actions
		
		state.schedule.scheduleRepeating( startTime, order, this );
	}
	
	@Override
	public void load(final SimState state) {
		super.load( state );
		setupPortroyals();
	}
	
	@Override
	public void quit() {
		super.quit();
		closePortroyals();
	}
	
	private void setupPortroyals() {
		final ElFarol model = (ElFarol) state;
		final XYSeries totalProductionSeries = initSeriesFor( attendance );
		
		scheduleRepeatingImmediatelyAfter( new Steppable() {
			private static final long serialVersionUID = 1L;
			
			public void step(final SimState state) {
				totalProductionSeries.add( model.schedule.getTime(), model.getAttendance(), true );
			}
		} );
	}
	
	private void setMetadata(final ChartGenerator chart, final String title, final String xLabel, final String yLabel) {
		chart.setTitle( title );
		chart.setXAxisLabel( xLabel );
		chart.setYAxisLabel( yLabel );
	}
	
	private XYSeries initSeriesFor(final TimeSeriesChartGenerator chart) {
		chart.removeAllSeries();
		
		final String uniqueChartId = chart.getTitle();
		final XYSeries ret = new XYSeries( uniqueChartId, false );
		chart.addSeries( ret, null );
		return ret;
	}
	
	private void closePortroyals() {
		if ( displayFrame != null ) {
			displayFrame.dispose();
		}
	}
	
	@Override
	public Object getSimulationInspectedObject() {
		return state;
	}
	
	@Override
	public Inspector getInspector() {
		final Inspector i = super.getInspector();
		i.setVolatile( true );
		return i;
	}
	
	public static String getName() {
		return "MASON El Farol";
	}
	
	public static void main(final String[] args) {
		final ElFarolGui guiModel = new ElFarolGui();
		final Console c = new Console( guiModel );
		c.setVisible( true );
	}
}

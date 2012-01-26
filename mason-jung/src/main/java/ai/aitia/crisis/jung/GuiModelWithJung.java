package ai.aitia.crisis.jung;

import java.awt.Dimension;

import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.engine.Steppable;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

public class GuiModelWithJung
		extends GUIState
		implements Steppable {
	
	private static final long serialVersionUID = 1L;
	private static final long DEFAULT_SEED = 0;
	
	public JFrame displayFrame = null;
	
	public GuiModelWithJung() {
		super( new ModelWithJung( DEFAULT_SEED ) );
	}
	
	@Override
	public void step(final SimState state) {
		// Update the displayed graph
		displayFrame.repaint();
	}
	
	@Override
	public void init(final Controller controller) {
		super.init( controller );
	}
	
	@Override
	public void start() {
		super.start();
		setupPortroyals();
		
		final double startTime = 0.0; // Start in the first tick
		final int order = 1; // After all the bank actions
		
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
		displayFrame = new JFrame( "Network Frame" );
		final ModelWithJung model = (ModelWithJung) state;
		
		{ // Standard JUNG usage
			final Layout<Agent, Edge> layout = new CircleLayout<Agent, Edge>( model.getNetwork() );
			layout.setSize( new Dimension( 300, 300 ) );
			
			final BasicVisualizationServer<Agent, Edge> vv = new BasicVisualizationServer<Agent, Edge>( layout );
			vv.setPreferredSize( new Dimension( 300, 300 ) );
			
			displayFrame.add( vv );
		}
		
		controller.registerFrame( displayFrame );
		
		displayFrame.pack();
		displayFrame.setLocationByPlatform( true );
		displayFrame.setVisible( true ); // We are in the event dispatch thread
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
	
	public static String getName() {
		return "MASON JUNG integration demo";
	}
	
	public static void main(final String[] args) {
		final GuiModelWithJung guiModel = new GuiModelWithJung();
		final Console c = new Console( guiModel );
		c.setVisible( true );
	}
	
}

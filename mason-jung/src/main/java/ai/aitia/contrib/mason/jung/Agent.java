package ai.aitia.contrib.mason.jung;

import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import edu.uci.ics.jung.graph.SparseGraph;

public class Agent
		implements Steppable {
	private static final long serialVersionUID = 1L;
	private static final double CHANCE_FOR_ACTION = 0.2;
	
	public void step(final SimState state) {
		final ModelWithJung model = (ModelWithJung) state;
		
		// New random edge
		if ( model.random.nextDouble() < CHANCE_FOR_ACTION ) {
			model.addRandomEdgeTo( this );
		}
		
		// Remove random edge
		if ( model.random.nextDouble() < CHANCE_FOR_ACTION ) {
			final SparseGraph<Agent, Edge> network = model.getNetwork();
			final ArrayList<Edge> edges = new ArrayList<Edge>( network.getEdges() );
			final int N = network.getEdgeCount();
			
			if ( N > 0 ) { // Only if there's an edge to remove
				final Edge edgeToRemove = edges.get( model.random.nextInt( N ) );
				network.removeEdge( edgeToRemove );
			}
		}
	}
}

package ai.aitia.contrib.mason.jung;

import java.util.ArrayList;

import sim.engine.SimState;
import edu.uci.ics.jung.graph.SparseGraph;

public class ModelWithJung
		extends SimState {
	private static final long serialVersionUID = 1L;
	private SparseGraph<Agent, Edge> network = new SparseGraph<Agent, Edge>();
	
	public ModelWithJung(final long seed) {
		super( seed );
	}
	
	public SparseGraph<Agent, Edge> getNetwork() {
		return network;
	}
	
	@Override
	public String toString() {
		return "MASON JUNG integration demo";
	}
	
	@Override
	public void start() {
		super.start();
		network = new SparseGraph<Agent, Edge>(); // Drop data from previous run
		
		for (int i = 0; i < 10; ++i) {
			final Agent agent = new Agent();
			schedule.scheduleRepeating( agent );
			network.addVertex( agent );
		}
		
		// Iterate over all of the agents, and add them a new random edge
		for (final Agent agent : network.getVertices()) {
			addRandomEdgeTo( agent );
		}
	}
	
	public void addRandomEdgeTo(final Agent agentFrom) {
		final ArrayList<Agent> agents = new ArrayList<>( network.getVertices() );
		
		// Random weight from [0, 99]
		final Edge edge = new Edge( random.nextInt( 100 ) );
		
		// Get a random agent (self links possible)
		final Agent agentTo = agents.get( random.nextInt( agents.size() ) );
		
		// Parameters: from, to, information
		network.addEdge( edge, agentFrom, agentTo );
	}
	
}

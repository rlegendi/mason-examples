package ai.aitia.contrib.mason.jung;

public class Edge {
	private final int weight; // Unused, but may come handy later
	
	public Edge(final int weight) {
		this.weight = weight;
	}
	
	protected int getWeight() {
		return weight;
	}
}

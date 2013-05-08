package ai.aitia.contrib.mason.demo.strategies;

import java.util.Arrays;

/**
 * @author rlegendi
 */
public abstract class AStrategy {
	protected final double[] weights;
	
	protected AStrategy(final double[] weights) {
		super();
		this.weights = weights;
	}
	
	public int size() {
		return weights.length;
	}
	
	public double getWeight(final int i) {
		return weights[i];
	}
	
	@Override
	public String toString() {
		return "AStrategy [weights=" + Arrays.toString( weights ) + "]";
	}
}

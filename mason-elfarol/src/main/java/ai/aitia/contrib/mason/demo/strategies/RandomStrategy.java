package ai.aitia.contrib.mason.demo.strategies;

import ai.aitia.contrib.mason.demo.ElFarol;

/**
 * @author rlegendi
 */
public class RandomStrategy
		extends AStrategy {
	
	private static double[] createRandomWeights(final ElFarol model) {
		final double[] ret = new double[model.getMemorySize() + 1];
		
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = model.random.nextDouble() * 2 - 1;
		}
		
		return ret;
	}
	
	public RandomStrategy(final ElFarol model) {
		super( createRandomWeights( model ) );
	}
}

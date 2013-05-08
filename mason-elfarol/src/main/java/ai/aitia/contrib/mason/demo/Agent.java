package ai.aitia.contrib.mason.demo;

import java.util.ArrayList;
import java.util.List;

import ai.aitia.contrib.mason.demo.strategies.AStrategy;
import ai.aitia.contrib.mason.demo.strategies.RandomStrategy;


/**
 * @author rlegendi
 */
public class Agent {
	private final List<AStrategy> strategies = new ArrayList<AStrategy>();
	private final ElFarol model;
	
	private AStrategy bestStrategy = null;
	private boolean attend = false;
	
	public Agent(final ElFarol model) {
		this.model = model;
		for (int i = 0, n = model.getStrategiesNumber(); i < n; ++i) {
			strategies.add( new RandomStrategy( model ) );
		}
		
		bestStrategy = strategies.get( 0 ); // Choose the first one initially
		updateStrategies();
	}
	
	public boolean isAttending() {
		return attend;
	}
	
	private double score(final AStrategy strategy) {
		double ret = 0.0;
		for (int i = 0; i < model.getMemorySize(); ++i) {
			final int week = i + 1;
			final double currentAttendance = model.getHistory().getAttendance( i );
			final double prediction = predictAttendance( strategy, model.getHistory().getSubHistory( week ) );
			
			ret += Math.abs( currentAttendance - prediction );
		}
		
		return ret;
	}
	
	private double predictAttendance(final AStrategy strategy, final List<Integer> subhistory) {
		// Last one is considered with a weight of 1.0
		double ret = strategy.getWeight( 0 );
		
		// Start from the second one (where index is 1)
		for (int i = 1, n = strategy.size(); i < n; ++i) {
			ret += strategy.getWeight( i ) * subhistory.get( i - 1 );
		}
		
		return ret;
	}
	
	public void updateStrategies() {
		double minScoreThreshold = model.getMemorySize() * model.getAgentsNumber() + 1;
		
		for (final AStrategy strategy : strategies) {
			final double score = score( strategy );
			if ( score < minScoreThreshold ) {
				minScoreThreshold = score;
				bestStrategy = strategy;
			}
		}
	}
	
	public void updateAttendance() {
		final double prediction = predictAttendance( bestStrategy, model.getHistory().getMemoryBoundedSubHistory() );
		
		attend = ( prediction <= model.getOvercrowdingThreshold() );
	}
}

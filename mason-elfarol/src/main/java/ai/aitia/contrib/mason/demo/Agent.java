package ai.aitia.contrib.mason.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.aitia.contrib.mason.demo.strategies.AStrategy;
import ai.aitia.contrib.mason.demo.strategies.RandomStrategy;
import ai.aitia.crisis.game.adaptor.agents.IPlayerActionAwareAgent;
import ai.aitia.crisis.game.adaptor.agents.IPlayerIdAwareAgent;


/**
 * @author rlegendi
 */
public class Agent implements IPlayerActionAwareAgent, IPlayerIdAwareAgent {
	private final List<AStrategy> strategies = new ArrayList<AStrategy>();
	private final ElFarol model;
	
	private AStrategy bestStrategy = null;
	private boolean attend = false;
	
	private int score = 0;
	
	private boolean hasActed = false;
	private String playerId;
	
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
	
	public int getScore(){
		return score;
	}
	
	public Map<String, Integer> getAgentScore(){
		Map<String, Integer> scoreMap = new HashMap<>();
		scoreMap.put("score", getScore());
		
		return scoreMap;
	}
	
	public void increaseScore(){
		score++;
	}
	
	private double scoreStrategy(final AStrategy strategy) {
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
			final double score = scoreStrategy( strategy );
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
	
	public void updateAttendance(boolean attend){
		this.attend = attend;
	}

	@Override
	public boolean hasActedInCurrentTurn(String actionId) {
		return hasActed;
	}

	@Override
	public void setActedInCurrentTurn(String actionId, boolean hasActed) {
		// there is only one action in this model that is controlled by the player
		this.hasActed = hasActed;
	}
	
	public Map<String, Object> getState(){
		Map<String, Object> result = new HashMap<>();
		
		result.put("tick", model.schedule.getTime());
		result.put("score", getScore());
		result.put("hasActed", hasActed);
		result.put("isAttending", isAttending());
		result.put("attendance", model.getAttendance());
		
		return result;
	}

	@Override
	public void setControllingPlayerId(String playerId) {
		this.playerId = playerId;
	}

	@Override
	public void unregisterControllingPlayer() {
		playerId = null;
	}
	
	public String getPlayerId(){
		return playerId;
	}
}

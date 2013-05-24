package ai.aitia.contrib.mason.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.aitia.crisis.game.adaptor.common.events.ActionEvaluationEvent;

import sim.engine.SimState;
import sim.engine.Steppable;

/**
 * @author rlegendi
 */
public class ElFarol
		extends SimState
		implements Steppable {
	
	private static final long serialVersionUID = 1L;
	
	// Parameters
	private int agentsNumber = 100;
	private int memorySize = 5;
	private int strategiesNumber = 10;
	private int overcrowdingThreshold = 60;
	
	final List<Agent> agents = new ArrayList<>();
	History history = null;
	
	public ElFarol(final long seed) {
		super( seed );
		
		initAgents();
	}
	
	public int getAgentsNumber() {
		return agentsNumber;
	}
	
	public void setAgentsNumber(final int agentsNumber) {
		this.agentsNumber = agentsNumber;
	}
	
	public int getMemorySize() {
		return memorySize;
	}
	
	public void setMemorySize(final int memorySize) {
		this.memorySize = memorySize;
	}
	
	public int getStrategiesNumber() {
		return strategiesNumber;
	}
	
	public void setStrategiesNumber(final int strategiesNumber) {
		this.strategiesNumber = strategiesNumber;
	}
	
	public int getOvercrowdingThreshold() {
		return overcrowdingThreshold;
	}
	
	public void setOvercrowdingThreshold(final int overcrowdingThreshold) {
		this.overcrowdingThreshold = overcrowdingThreshold;
	}
	
	public int getAttendance() {
		int ret = 0;
		
		for (final Agent act : agents) {
			if ( act.isAttending() ) {
				ret++;
			}
		}
		
		return ret;
	}
	
	@Override
	public void step(final SimState state) {
		for (final Agent act : agents) {
			act.updateAttendance();
		}
		
		history.updateHistory();
		
		boolean shouldHaveGone = getAttendance() <= overcrowdingThreshold;
		
		for (final Agent act : agents) {
			if (act.isAttending() && shouldHaveGone || !act.isAttending() && !shouldHaveGone){
				act.increaseScore();
			}
			act.updateStrategies();
		}
	}
	
	public History getHistory() {
		return history;
	}
	
	public List<Integer>getMemoryBoundedSubHistory(){
		return history.getMemoryBoundedSubHistory();
	}
	
	@Override
	public void start() {
		super.start();
		
		schedule.scheduleRepeating( this );
	}

	public void initAgents() {
		agents.clear();
		
		history = new History( this );
		
		for (int i = 0; i < getAgentsNumber(); ++i) {
			final Agent agent = new Agent( this );
			agents.add( agent );
		}
	}

	/**
	 * @return the agents
	 */
	public List<Agent> getAgents() {
		return agents;
	}
	
	public Map<String, Integer> getModelParameters(){
		Map<String, Integer> parameters = new HashMap<String, Integer>();
		
		parameters.put("agentsNumber", agentsNumber);
		parameters.put("memorySize", memorySize);
		parameters.put("strategiesNumber", strategiesNumber);
		parameters.put("overcrowdingThreshold", overcrowdingThreshold);
		
		return parameters;
	}
	
	public Map<String, Map<String, ? extends Number>> getScores(){
		Map<String, Map<String, ? extends Number>> result = new HashMap<>();
		int averageAgentScore = 0;
		int averageAIScore = 0;
		
		for (Agent agent : getAgents()) {
			String playerId = agent.getPlayerId();
			averageAgentScore += agent.getScore();
			if (playerId == null){
				averageAIScore += agent.getScore();
			} else {
				result.put(playerId, agent.getAgentScore());
			}
		}
		
		Map<String, Double> score = new HashMap<>();
		score.put("score", (double)averageAIScore / agentsNumber);
		result.put("average AI score", score);
		
		score.put("score", (double)averageAgentScore / agentsNumber);
		result.put("average score", score);
		
		
		return result;
	}
	
	public String playerActed(ActionEvaluationEvent event){
		return event.getPlayerId();
	}
	
}

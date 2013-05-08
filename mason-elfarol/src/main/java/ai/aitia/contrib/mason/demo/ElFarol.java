package ai.aitia.contrib.mason.demo;

import java.util.ArrayList;
import java.util.List;

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
		
		for (final Agent act : agents) {
			act.updateStrategies();
		}
	}
	
	public History getHistory() {
		return history;
	}
	
	@Override
	public void start() {
		super.start();
		agents.clear();
		
		history = new History( this );
		
		for (int i = 0; i < getAgentsNumber(); ++i) {
			final Agent agent = new Agent( this );
			agents.add( agent );
		}
		
		schedule.scheduleRepeating( this );
	}
}

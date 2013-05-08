package ai.aitia.contrib.mason.demo;

import java.util.LinkedList;
import java.util.List;

/**
 * @author rlegendi
 */
public class History {
	private final LinkedList<Integer> history = new LinkedList<Integer>();
	private final ElFarol model;
	
	public History(final ElFarol model) {
		this.model = model;
		
		fillWithRandomInitialData( model );
	}
	
	private void fillWithRandomInitialData(final ElFarol model) {
		for (int i = 0; i < 2 * model.getMemorySize(); ++i) {
			history.add( model.random.nextInt( model.getAgentsNumber() ) );
		}
	}
	
	public void updateHistory() {
		history.removeLast();
		history.addFirst( Integer.valueOf( model.getAttendance() ) );
	}
	
	public int getAttendance(final int idx) {
		return history.get( idx );
	}
	
	public List<Integer> getSubHistory(final int week) {
		return history.subList( week, week + model.getMemorySize() );
	}
	
	public List<Integer> getMemoryBoundedSubHistory() {
		return history.subList( 0, model.getMemorySize() );
	}
	
	@Override
	public String toString() {
		return "History [history=" + history + "]";
	}
}

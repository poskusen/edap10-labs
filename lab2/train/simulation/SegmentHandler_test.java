package train.simulation;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Queue;

import train.model.Route;
import train.model.Segment;
import train.view.TrainView;

public class SegmentHandler_test {
	private HashSet<Segment> upptagna;
	
	public SegmentHandler_test() {
		upptagna = new HashSet<Segment>();
	}
	public void enter(Segment del) throws InterruptedException {
		while (upptagna.contains(del)) {
			//wait();
		}
		upptagna.add(del);
		del.enter();
	}
	public void leave(Segment del) {
		upptagna.remove(del);
		//notifyAll();
	}
	public void take_step(Segment fram, Segment bak) throws InterruptedException {
		enter(fram);
		bak.exit();
		leave(bak);
		
		
	}
}

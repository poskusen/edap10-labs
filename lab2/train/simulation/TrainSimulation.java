package train.simulation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

import train.model.Route;
import train.model.Segment;
import train.view.TrainView;

public class TrainSimulation {

    public static void main(String[] args) {

        TrainView view = new TrainView();
        SegmentHandler hand = new SegmentHandler();
        Runnable runnable = () ->  {
        	Route route = view.loadRoute();
        	ArrayDeque<Segment> tåg = new ArrayDeque<Segment>();
        	for (int i = 0; i < 3; i++) {
        		tåg.add(route.next());
        	}
            try {
            	for (Segment del: tåg) {
					hand.enter(del);
            	}
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
        	while (true){
        		tåg.addFirst(route.next());
        		try {
					hand.take_step(tåg.peek(),tåg.pollLast());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
        };
        for (int i = 0 ;i <= 19; i++) {
        	Thread t = new Thread(runnable);
        	t.start();
        }
        
        	
        	
    }

}

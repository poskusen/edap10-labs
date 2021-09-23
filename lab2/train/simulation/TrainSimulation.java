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
        	ArrayDeque<Segment> t�g = new ArrayDeque<Segment>();
        	for (int i = 0; i < 3; i++) {
        		t�g.add(route.next());
        	}
            try {
            	for (Segment del: t�g) {
					hand.enter(del);
            	}
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
        	while (true){
        		t�g.addFirst(route.next());
        		try {
					hand.take_step(t�g.peek(),t�g.pollLast());
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

import java.util.Stack;

import lift.LiftView;
import lift.Passenger;

public class monitor {
	private int floor;
	private boolean moving;
	private int direction;
	private int[] waitEntry;
	private int[] waitExit;
	private int load;
	private LiftView hiss;
	private boolean door;
	private Stack<Integer> movement;
	
	public monitor(LiftView hiss) {
		this.hiss = hiss;
		this.floor = 0;
		this.moving = false;
		this.direction = 0; //not moving
		this.waitEntry = new int[]{0, 0, 0, 0, 0, 0, 0};
		this.waitExit = new int[]{0, 0, 0, 0, 0, 0, 0};
		this.load = 0;
		this.door = false;
		this.movement = new Stack<Integer>();

	}
	
	public synchronized void enter_start(int level_begin, int level_end) throws InterruptedException {
		this.waitEntry[level_begin]++;
		notifyAll();
		while ((level_begin != this.floor) || this.moving || load > 3 || !this.door) {
			wait();
		}
		this.load++;
		this.movement.add(0);
		
	}
	public synchronized void enter_end(int level_begin, int level_end) {
		this.waitExit[level_end]++;
		this.waitEntry[level_begin]--;
		this.movement.pop();
		notifyAll();
	}
	public synchronized void exit_start(int level_end) throws InterruptedException {
		while (level_end != this.floor || this.moving || !this.door) {
			wait();
		}
		this.movement.add(0);
		this.load--;
		notifyAll();
	}
	public synchronized void exit_end(int level_end) throws InterruptedException {
		this.waitExit[level_end]--;
		this.movement.pop();
		notifyAll();
	}
	public synchronized void move_start(int level) throws Exception {
		while ((((waitEntry[this.floor] != 0 & this.load < 4) || waitExit[this.floor] != 0)||(!someone_to_pick()))||!this.movement.isEmpty()) {
			wait();
		}
		if (level - floor == 0) {
			throw new Exception("Försöker åka till samma nivå");
		}
		if (this.door) {
			close();
		}
		this.direction = Math.abs(level - floor)/(level - floor);
		this.moving = true;
	}
	private boolean someone_to_pick() {
		boolean someone = false;
		for (int i = 0; i < 7; i++) {
			if (waitEntry[i] != 0 || waitExit[i] != 0) {
				someone = true;
				break;
			}
		}
		
		return someone;
	}
	public synchronized void move_end(int level) throws Exception{
		this.moving = false;
		this.floor = level;
		if ((waitEntry[level] > 0 & load < 4)|| waitExit[level] > 0 ) {
			open();
		}
		notifyAll();
	}
	public LiftView get_hiss() {
		return hiss;
	}
	public synchronized void open() {
		this.door = true;
		hiss.openDoors(this.floor);
	}
	public synchronized void close() {
		this.door = false;
		hiss.closeDoors();
	}
}

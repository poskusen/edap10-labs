import lift.LiftView;

public class lift_thread extends Thread{
	private monitor hiss;
	public lift_thread(monitor hiss) {
		this.hiss = hiss;
	}
	
	@Override
	public void run() {
		int floor = 0;
		boolean up = true;
		try {
			hiss.move_end(0);
			while (true) {
				if (floor == 0) { 
					up = true;
				}else if (floor == 6) {
					up = false;
				}
				if (up) {
					floor++;
					hiss.move_start(floor);
					hiss.get_hiss().moveLift(floor - 1, floor);
					hiss.move_end(floor);
				}else {
					floor--;
					hiss.move_start(floor);
					hiss.get_hiss().moveLift(floor + 1, floor);
					hiss.move_end(floor);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

import lift.LiftView;

public class mainclass {
	
	public static void main(String[] args) {
		LiftView  view = new LiftView();
		monitor mon = new monitor(view);
		lift_thread hiss = new lift_thread(mon);
		hiss.start();
		for (int i = 0; i < 40; i++) {
			passenger_thread pers = new passenger_thread(mon);
			pers.start();
		}
		
	}
}

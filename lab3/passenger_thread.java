import java.util.Random;

import lift.LiftView;
import lift.Passenger;

public class passenger_thread extends Thread{
	private Passenger pass;
	private monitor hiss;
	private int till;
	private int från;
	public passenger_thread(monitor hiss) {
		this.hiss = hiss;
		this.pass = hiss.get_hiss().createPassenger();
		this.till = pass.getDestinationFloor();
		this.från = pass.getStartFloor();
	}
	@Override
	public void run() {
		Random rand = new Random();
		double sova = rand.nextDouble()*45000;
		try {
			Thread.sleep((long) sova);
			while (true) {
				pass.begin();
				hiss.enter_start(från, till);
				pass.enterLift();
				hiss.enter_end(från, till);
				
				hiss.exit_start(till);
				pass.exitLift();
				hiss.exit_end(till);
				pass.end();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

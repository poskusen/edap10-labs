package Threads;
import java.util.concurrent.Semaphore;

import Data.UserData;
import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;

public class TimeThread extends Thread{
	private ClockOutput out;
	private UserData data;
	public TimeThread(ClockOutput clock, UserData data_in) {
		out = clock;
		data = data_in;
	}
	@Override
	public void run() {
		try { 
			long t0 = System.currentTimeMillis();
			int index = 0;
			while (true) {
				data.updateTime(0,0,1, out);
				long now = System.currentTimeMillis();
				Thread.sleep(1000*(index + 1) - (now - t0));	
				index += 1;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

package Threads;
import java.util.concurrent.Semaphore;

import Data.UserData;
import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;

public class SettingsThread extends Thread{
	private Semaphore sema;
	private ClockInput in;
	private ClockOutput out;
	private UserData data;
	public SettingsThread(ClockInput clock_in, ClockOutput clock_out, UserData data_in) {
		data = data_in;
		in = clock_in;
		out = clock_out;
		sema = in.getSemaphore();
	}
	@Override
	public void run() {
		try {
			while (true) {
				sema.acquire();
				UserInput userInput = in.getUserInput();
				int choice = userInput.getChoice();
				int h = userInput.getHours();
				int m = userInput.getMinutes();
				int s = userInput.getSeconds();
				if (choice == 1) {
					data.setTime(h, m, s, out);
				}else if (choice == 2){
					data.setAlarmTime(h, m, s);
				}else if (choice == 3) {
					data.setAlarm();
					out.setAlarmIndicator(data.getAlarm());
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

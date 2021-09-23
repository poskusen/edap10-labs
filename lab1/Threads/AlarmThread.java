package Threads;
import java.util.concurrent.Semaphore;

import Data.UserData;
import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;

public class AlarmThread extends Thread{
	private ClockOutput out;
	private UserData data;
	public AlarmThread(ClockOutput clock, UserData data_in) {
		out = clock;
		data = data_in;
	}
	@Override
	public void run() {
		try {
			while (true) {
				boolean alarm_on = data.getAlarm();
				if (alarm_on) {
					boolean alarm = data.isAlarm();
					if (alarm) {
						int index = 0;
						while (data.getAlarm()) {
							if (index >= 20) {
								break;
							}else {
								out.alarm();
								index++;
								Thread.sleep(1000);
							}
							
						}

					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

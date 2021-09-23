package Data;
import java.util.concurrent.Semaphore;

import Threads.SettingsThread;
import Threads.AlarmThread;
import Threads.TimeThread;
import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;

public class UserData {
	private int h;
	private int m;
	private int s;
	private int h_alarm = 0;
	private int m_alarm = 0;
	private int s_alarm = 0;
	private boolean alarm = false;
	private Semaphore mutextime = new Semaphore(1);
	private Semaphore alarmlock = new Semaphore(1);
	public UserData(int h_in, int m_in, int s_in) {
		h = h_in;
		m = m_in;
		s = s_in;	
	}

	// Ändrar och uppdaterar tiden
	public void updateTime(int h_in, int m_in, int s_in, ClockOutput Clock) throws InterruptedException {
		mutextime.acquire();
		h += h_in;
		m += m_in;
		s += s_in;
		if (s > 59) {
			m += 1;
			s = s - 60;
		}
		if (m > 59) {
			h += 1;
			m = m - 60;
		} 
		if (h > 23) {
			h = h - 24;
		} 
		Clock.displayTime(h, m, s);
		mutextime.release();
	}

	//Överskriver tidigare tid
	public void setTime(int h_in, int m_in, int s_in, ClockOutput Clock) throws InterruptedException {
		mutextime.acquire();
		h = h_in;
		m = m_in;
		s = s_in;
		Clock.displayTime(h, m, s);
		mutextime.release();
	}
	public void setAlarm() throws InterruptedException {
		alarmlock.acquire();
		alarm = !alarm;
		alarmlock.release();
	}
	public void setAlarmTime(int h_in, int m_in, int s_in) throws InterruptedException {
		alarmlock.acquire();
		h_alarm = h_in;
		m_alarm = m_in;
		s_alarm = s_in;
		alarmlock.release();
	}
	public boolean isAlarm() throws InterruptedException {
		alarmlock.acquire();
		if (h_alarm == h) {
			if (m_alarm == m) {
				if (s_alarm == s) {
					alarmlock.release();
					return true;
				}
			}
		}
		alarmlock.release();
		return false;
		
	}
	public boolean getAlarm() throws InterruptedException {
		alarmlock.acquire();
		boolean retur = alarm;
		alarmlock.release();
		return retur;
	}
	public int getH() throws InterruptedException {
		mutextime.acquire();
		int retur = h;
		mutextime.release();
		return retur;
	}
	public int getM() throws InterruptedException {
		mutextime.acquire();
		int retur = m;
		mutextime.release();
		return retur;
	}
	public int getS() throws InterruptedException {
		mutextime.acquire();
		int retur = s;
		mutextime.release();
		return retur;
	}
}

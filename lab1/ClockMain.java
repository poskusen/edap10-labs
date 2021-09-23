import java.util.concurrent.Semaphore;

import Data.UserData;
import Threads.SettingsThread;
import Threads.AlarmThread;
import Threads.TimeThread;
import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;

public class ClockMain {

    public static void main(String[] args) throws InterruptedException {
        AlarmClockEmulator emulator = new AlarmClockEmulator();
        ClockInput  in  = emulator.getInput();
        ClockOutput out = emulator.getOutput();
        UserData data = new UserData(23,59,55);
        AlarmThread alarm = new AlarmThread(out, data);
        TimeThread time = new TimeThread(out, data);
        SettingsThread settings = new SettingsThread(in, out, data);
        
        settings.start();
        time.start();
        alarm.start();
        
    }

    
}

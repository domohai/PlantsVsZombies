package abc;

import java.util.Timer;
import java.util.TimerTask;

public class TimerMachine {
	public static Timer sunTimer = new Timer();
	public static Timer zombieTimerPeriodOne = new Timer();
	public static Timer zombieTimerPeriodTwo = new Timer();
	
	public static TimerTask sunTimerTask = new TimerTask() {
		@Override
		public void run() {
			SpawnMachine.spawnSun();
		}
	};
	
	public static TimerTask zombieTimerTask = new TimerTask() {
		@Override
		public void run() {
			SpawnMachine.spawnZombie();
		}
	};

}

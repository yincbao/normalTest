package com.cpw.time;

import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {
	
	public static void main(String[] args) {
		Timer timer = new Timer();
		TimerTask task = new TestTimeTask();
		timer.schedule(task,100L,100L);
	}

}

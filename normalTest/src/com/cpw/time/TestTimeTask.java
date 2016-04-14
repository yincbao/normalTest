package com.cpw.time;

import java.util.Date;
import java.util.TimerTask;

public class TestTimeTask extends TimerTask {

	@Override
	public void run() {
		Date execyteTime = new Date(this.scheduledExecutionTime());
		System.out.println("current job finshed at: "+execyteTime);
		
	}

}

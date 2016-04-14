package com.cpw.desginpatten.command;

import org.junit.Test;

public class TetsCommand {

	@Test
	public void test() {
		Light light = new Light();
		Command lightOnCommand = new LightOnCommand(light);
		Command lightOffCommand = new LightOffCommand(light);
		Invoker invoker = new Invoker();
		invoker.setInvokerCommand(lightOnCommand, lightOffCommand);
		invoker.pressOn();
		invoker.PressOff();
	}

}

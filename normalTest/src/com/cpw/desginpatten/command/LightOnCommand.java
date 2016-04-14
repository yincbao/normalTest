package com.cpw.desginpatten.command;

public class LightOnCommand implements Command{
	private Light light;
	
	public LightOnCommand(Light light){
		this.light = light;
	}
	@Override
	public void execute() {
		light.BeforeLightOn();
		light.LightOn();
		
	}

}

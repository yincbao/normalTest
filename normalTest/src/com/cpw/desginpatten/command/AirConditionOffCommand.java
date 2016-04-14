package com.cpw.desginpatten.command;

public class AirConditionOffCommand {
	private AirCondition airCondition;
	
	public AirConditionOffCommand(AirCondition airCondition){
		this.airCondition = airCondition;
	}
	public void execute(){
		airCondition.AirConditionOff();
	}
}

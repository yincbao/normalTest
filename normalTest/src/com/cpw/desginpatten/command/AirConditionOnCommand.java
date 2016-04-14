package com.cpw.desginpatten.command;

public class AirConditionOnCommand implements Command{
	private AirCondition airCondition;
	public AirConditionOnCommand(AirCondition airCondition){
		this.airCondition = airCondition;
	}
	@Override
	public void execute() {
		airCondition.AirConditionOn();
		
	}

}

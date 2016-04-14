package com.cpw.desginpatten.command;

public class Invoker {
	private Command onCommand;
	private Command offCommand;
	
	public void setInvokerCommand(Command onCommand,Command offCommand){
		this.onCommand = onCommand;
		this.offCommand = offCommand;
	}
	 public void pressOn(){
		 this.onCommand.execute();
	 }
	 public void PressOff(){
		 this.offCommand.execute();
	 }
	
}

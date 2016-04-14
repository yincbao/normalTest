package com.cpw.desginpatten.command;

public class Light implements Device {
	
	public void LightOn(){
		System.out.println("light on!");
	}
	
	public void BeforeLightOn(){
		System.out.println("some operations must be done before on");
	}
	
	public void LightOff(){
		System.out.println("light off!");
	}
}

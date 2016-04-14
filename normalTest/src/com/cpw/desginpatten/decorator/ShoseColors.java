package com.cpw.desginpatten.decorator;

public enum ShoseColors {
	Green("green"),Yellow("yellow"),RED("red");
	
	private String color;
	
	private ShoseColors(String color) {
		this.color = color;
	}
	
	public ShoseColors getColors(String name){
		return valueOf(name);
	}
	public String color(){
		return color;
	}
	
}

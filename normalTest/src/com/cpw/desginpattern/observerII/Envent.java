package com.cpw.desginpattern.observerII;

public class Envent extends Observable  {
	
	private String name;

	public String getName() {
		return name;
	}

	public void updateNameNotifyObervers(String name){
		this.name = name;
		System.out.println("name updated");
		setChanged();
	}

	public void setName(String name) {
		this.name = name;
	}
}

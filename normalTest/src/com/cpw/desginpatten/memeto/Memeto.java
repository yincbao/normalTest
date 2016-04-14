package com.cpw.desginpatten.memeto;

public class Memeto {
	private int state;
	private String name;
	public Memeto(Entity entity){
		
		state = entity.getState();
		name  = entity.getName();
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

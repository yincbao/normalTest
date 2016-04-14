package com.cpw.desginpattern.state;

public class Context {
	private State state;
	public void setState(State state){
		this.state = state;
	}
	public State getState(){
		
		return this.state;
	}
	public void pull(){
		this.state.handlePull(this);
		this.state.getcolor();
		
	}
	public void push(){
		this.state.handlePush(this);
		this.state.getcolor();
	}

}

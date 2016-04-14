package com.cpw.desginpattern.state;

public class RedState extends State {

	@Override
	public void handlePush(Context c) {
		c.setState(new BlueState());

	}

	@Override
	public void handlePull(Context c) {
		c.setState(new YellowState());

	}

	@Override
	public String getcolor() {
		// TODO Auto-generated method stub
		return "red";
	}

}

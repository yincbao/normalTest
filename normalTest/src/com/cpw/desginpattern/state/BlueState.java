package com.cpw.desginpattern.state;

public class BlueState extends State {

	@Override
	public void handlePush(Context c) {
		c.setState(new RedState());

	}

	@Override
	public void handlePull(Context c) {
		c.setState(new YellowState());

	}

	@Override
	public String getcolor() {
		return "blue";

	}

}

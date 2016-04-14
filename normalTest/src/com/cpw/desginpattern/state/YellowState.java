package com.cpw.desginpattern.state;

public class YellowState extends State {

	@Override
	public void handlePush(Context c) {
		c.setState(new BlueState());

	}

	@Override
	public void handlePull(Context c) {
		c.setState(new RedState());

	}

	@Override
	public String getcolor() {
		// TODO Auto-generated method stub
		return "yellow";
	}

}

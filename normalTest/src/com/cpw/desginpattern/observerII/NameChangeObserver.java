package com.cpw.desginpattern.observerII;

public class NameChangeObserver implements Observer {

	@Override
	public void takeAction(Observable observable) {
		System.out.println("observer was registered, whitout parameter");
		
	}

	@Override
	public void takeAction(Observable observable, Object arg) {
		System.out.println("observer was registered, getting parameter:"+arg);
		
	}

}

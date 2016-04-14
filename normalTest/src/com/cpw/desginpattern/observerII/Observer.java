package com.cpw.desginpattern.observerII;

public interface Observer {
	public void takeAction(Observable observable);
	public void takeAction(Observable observable,Object arg);
}

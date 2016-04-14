package com.cpw.desginpattern.observerII;

import org.junit.Test;

public class TestObserverPartten {

	@Test
	public void test() {
		Envent envent = new Envent();
		Observer observer =  new NameChangeObserver();
		envent.addObserver(observer);
		envent.setName("KKK");
		envent.notifyObserver(observer);
		
	}

}

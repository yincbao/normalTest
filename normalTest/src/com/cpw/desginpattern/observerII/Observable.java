package com.cpw.desginpattern.observerII;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Observable {

	public  List<Observer> observers = Collections.synchronizedList(new ArrayList<Observer>());
	private  boolean status; 
	public void addObserver(Observer observer){
		observers.add(observer);
	}
	public void addObservers(Collection<Observer> c){
		observers.addAll(c);
	}
	public void removeObserver(Observer observer){
		observers.remove(observer);
	}
	public synchronized void setChanged(){
		this.status = true;
	}
	public synchronized void clearChanged(){
		this.status = false;
	}
	
	public void notifyObserver(Observer observer){
		if(!status){
			observer.takeAction(this,"parameter :notyfy");
			clearChanged();
		}
	}
	public void notyfyObservers(List<Observer> observers){
		if(!status){
			for(Observer obs:observers){
				obs.takeAction(this,"parameter :notyfy All");
			}
			clearChanged();
		}
	}
	
	public  synchronized int countObservers(){
		return observers.size();
	}
	
	
	
}

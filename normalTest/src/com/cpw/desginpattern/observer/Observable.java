package com.cpw.desginpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {
	  //state
	  private boolean changed = false;
	  //observer collection
	  private List<Observer> observers;
	  public Observable() {
	    observers = new ArrayList<Observer>(0);
	  }
	  //attach a oberver
	  public synchronized void addObserver(Observer o) {
	    if (o == null)
	      throw new NullPointerException();
	    if (!observers.contains(o)) {
	      observers.add(o);
	    }
	  }
	 
	  //detach a oberver
	  public synchronized void deleteObserver(Observer o) {
	    observers.remove(o);
	  }
	  //trigger all observers attached to this object observer to work
	  public void notifyObservers() {
	    notifyObservers(null);
	  }
	  //trigger all observers attached to this object observer to work
	  public void notifyObservers(Object arg) {
	    synchronized (this) {
	      if (!changed)
	        return;
	      clearChanged();
	    }
	   
	    for (Observer observer : observers) {
	      observer.update(this, arg);
	    }
	  }
	  //Clears the observer list so that this object no longer has any observers.
	  public synchronized void deleteObservers() {
	    observers.clear();
	  }
	  //Marks this Observable object as having been changed;
	  protected synchronized void setChanged() {
	    changed = true;
	  }
	  //Indicates that this object has no longer changed, or that it has already
	  //notified all of its observers of its most recent change
	  protected synchronized void clearChanged() {
	    changed = false;
	  }
	  public synchronized boolean hasChanged() {
	    return changed;
	  }
	  public synchronized int countObservers() {
	    return observers.size();
	  }
	}
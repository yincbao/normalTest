package com.cpw.spring.propertyEditor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TorderPropertyChangeListener implements PropertyChangeListener {
	private String properties;
	private String oldValues;
	private String newValues;
	
	public TorderPropertyChangeListener(){
		this.properties = "";
		this.oldValues = "";
		this.newValues = "";
	}
	
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getPropertyName();
		String oldValue = String.valueOf(event.getOldValue());
		String newValue = String.valueOf(event.getNewValue());
		if(!oldValue.equals(newValue) && (oldValue!=null && !newValue.equals(""))){
			this.properties=this.properties+","+property;
			this.oldValues = this.oldValues+","+oldValue;
			this.newValues = this.newValues+","+newValue;
		}
		System.out.println(this.properties+" changed from "+ this.oldValues+" to "+this.newValues);
	}

	public String getNewValues() {
		return newValues;
	}

	public String getOldValues() {
		return oldValues;
	}

	public String getProperties() {
		return properties;
	}
	
}

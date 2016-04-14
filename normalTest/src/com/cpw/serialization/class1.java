package com.cpw.serialization;

import java.io.Serializable;

public class class1 implements Serializable{
	String clazz ;

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public class1(String clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public String toString() {
		return "class1 [clazz=" + clazz + "]";
	}
	
}

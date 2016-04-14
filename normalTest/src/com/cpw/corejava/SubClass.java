package com.cpw.corejava;

public class SubClass extends AbstractClass{
	
	public SubClass(){
		this.comm  = new Commponent();
	}
	
public static void main(String args[]){
	AbstractClass  c = new SubClass();
	c.doSome();
	System.out.println(c.comm.getCount());
}
}


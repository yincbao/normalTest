package com.cpw.hash;

public class ObjectHash {

	
	private String name;
	private int id;
	
	public static void main(String[] args) {
		ObjectHash ob = new ObjectHash();
		ObjectHash ob1 = new ObjectHash();
		System.out.println(ob.hashCode()+"  "+ob1.hashCode());
	}
}



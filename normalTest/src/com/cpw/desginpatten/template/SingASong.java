package com.cpw.desginpatten.template;

public abstract class SingASong {
	
	public abstract void song();
	
	public void sing(){
		System.out.print("I am sing: ");
		for(int i=0;i<3;i++)
			song();
		System.out.print(" \r\n 3 times!");
	}


}

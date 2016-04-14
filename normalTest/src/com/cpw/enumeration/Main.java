package com.cpw.enumeration;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		Set<Entity> set = new HashSet<Entity>();
		for(int i = 0; i<10;i++){
			set.add(new Entity(null,i));
		}
		EnumerHelper.addEntry(set);
		Enumeration<? extends Entity> e = EnumerHelper.getEntry();
		while(e.hasMoreElements()){
			System.out.println(e.nextElement().getId());
		}
	}
	
	
}

package com.cpw.classloade;

import com.cpw.classloade.myClassloader.ClazzLoader;

public class LoadRefectMain {
	
	
	static {
		
		System.out.println("main class loaded");
		
	}
	
 {
		
		System.out.println("class loaded2");
		
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		System.out.println(Thread.currentThread().getContextClassLoader());
		ClazzLoader nl = new ClazzLoader();
		System.out.println(nl.getParent());
		LoadRefect obj1 = (LoadRefect) Class.forName("com.cpw.classloade.LoadRefect", true, Thread.currentThread().getContextClassLoader()).newInstance();
		Thread.currentThread().setContextClassLoader(nl);
		LoadRefect obj2 =  (LoadRefect)Class.forName("com.cpw.classloade.LoadRefect", true,Thread.currentThread().getContextClassLoader()).newInstance();
		obj1 = obj2;
		
	}

}

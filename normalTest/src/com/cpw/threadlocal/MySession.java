package com.cpw.threadlocal;

public class MySession {
	
	public MySession(){
		System.out.println("session was create in thread: "+Thread.currentThread().getName());
	}
}

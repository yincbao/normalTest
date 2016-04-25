package com.jdk8.newinterface;

public interface InterfaceA {
	
	default String foo(String arg){
		return arg+"handled By A";
	}
	
	static String fooStatic(){
		return "foo static method";
	}

}

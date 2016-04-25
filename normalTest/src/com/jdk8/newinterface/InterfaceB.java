package com.jdk8.newinterface;

public interface InterfaceB {
	
	default String foo(String arg){
		return arg+"handled By B";
	}

}

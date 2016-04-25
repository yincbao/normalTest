package com.jdk8.newinterface;

public interface InterfaceC extends InterfaceA{
	
	@Override
	default String foo(String args){
		return "";
	}


}

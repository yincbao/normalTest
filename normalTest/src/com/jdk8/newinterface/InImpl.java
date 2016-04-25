package com.jdk8.newinterface;

public class InImpl implements InterfaceA, InterfaceB{

	/**
	 * 只能选择重写一个
	 */
	@Override
	public String foo(String args){
		return InterfaceB.super.foo(args);
	}
}

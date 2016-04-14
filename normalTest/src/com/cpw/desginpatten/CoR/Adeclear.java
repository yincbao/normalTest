package com.cpw.desginpatten.CoR;

public class Adeclear {
	/**
	 * 责任链模式，其实就是if()...else if()..else()
	 * 是用一系列类(classes)试图处理一个请求request,这些类之间是一个松散的耦合,唯一共同点是在他们之间传递request. 
	 * 也就是说，来了一个请求，A类先处理，如果没有处理，就传递到B类处理，如果没有处理，就传递到C类处理，就这样象一个链条(chain)一样传递下去。
	 * request有两类read和print
	 * 在concretehandle中，if else
	 */

}

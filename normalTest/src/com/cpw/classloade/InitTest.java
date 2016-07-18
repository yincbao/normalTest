package com.cpw.classloade;

/**
 * @ClassName InitTest.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 20, 2016 4:03:41 PM
 *
 */
public class InitTest {
	
	private String name;
	
	{
		System.out.println("初始化语句块执行"+this.hashCode());
	}
	
	public InitTest(){
		System.out.println("默认构造方法");
	}
	public InitTest(String name){
		this.name = name;
		System.out.println("构造方法执行"+this.hashCode());
	}
	
	public static void main(String args[]){
		new InitTest("aa");
	}

}

package com.cpw.classloade;

public class StaticTest {
	public static int k=0;
	public static StaticTest s1=new StaticTest("s1");
	public static StaticTest s2=new StaticTest("s2");
	public static int i=print("i");
	public static int n=99;
	public int j=print("j");
	
	{
		print("构造块");
	}
	
	static
	{
		print("静态块");
	}
	
	public static int print(String s)
	{
		System.out.println(++k+":"+s+"\ti="+i+"\tn="+n);
		++n;
		return ++i;
	}
	
	public StaticTest(String s)
	{
		System.out.println(++k+":"+s+"\ti="+i+"\tn="+n);
		++i;
		++n;
	}

	public static void main(String[] args) {
		new StaticTest("init");
	}
	
}

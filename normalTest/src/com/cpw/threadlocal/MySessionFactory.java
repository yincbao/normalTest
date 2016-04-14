package com.cpw.threadlocal;

public class MySessionFactory {
	/*
	 * 完成饥饿模式的单例，线程安全
	 * 缺点，无法给给构造方法传参数
	 */
	private static MySessionFactory mySessionFactory=null;
	
	static{
		if(mySessionFactory==null){
			mySessionFactory = new MySessionFactory();
		}
	}
	
	private MySessionFactory(){
		System.out.println("sessionFactory was created in thread: "+Thread.currentThread().getName());
		
	}
	
	public static MySessionFactory getSessionFactory(){
		return mySessionFactory;
	}
	private MySession creatSession(){
		return new MySession();
	}
	
	
	/**
	 * 开始实现ThreadLocal对session对象的管理
	 * 达到，避免县城内重复频繁的创建销毁动作，
	 */
	public static final ThreadLocal<MySession> session = new ThreadLocal<MySession>();
	
	public MySession currentSession(){
		MySession mySession = session.get();
		if(mySession==null){
			mySession = getSessionFactory().creatSession();
			session.set(mySession);
		}
		return mySession;
		
	} 
}

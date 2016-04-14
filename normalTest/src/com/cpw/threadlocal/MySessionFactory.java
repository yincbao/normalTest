package com.cpw.threadlocal;

public class MySessionFactory {
	/*
	 * ��ɼ���ģʽ�ĵ������̰߳�ȫ
	 * ȱ�㣬�޷��������췽��������
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
	 * ��ʼʵ��ThreadLocal��session����Ĺ���
	 * �ﵽ�������س����ظ�Ƶ���Ĵ������ٶ�����
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

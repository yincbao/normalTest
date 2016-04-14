package com.cpw.threadlocal;

public class Main {
	
	
	static class UserThread implements Runnable{

		public void run() {
			MySessionFactory.getSessionFactory().currentSession();
		}
		
	}
	public static void main(String[] args) {
		MySessionFactory.getSessionFactory().currentSession();
		UserThread ut = new UserThread();
		Thread th1 = new Thread(ut);
		Thread th2 = new Thread(ut);
		Thread th3 = new Thread(ut);
		Thread th4 = new Thread(ut);
		Thread th5 = new Thread(ut);
		Thread th6 = new Thread(ut);
		Thread th7 = new Thread(ut);
		Thread th8 = new Thread(ut);
		th1.start();
		th2.start();
		th3.start();
		th4.start();
		th5.start();
		th6.start();
		th7.start();
		th8.start();
	}
}

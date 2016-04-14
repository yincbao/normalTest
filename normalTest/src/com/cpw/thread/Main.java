package com.cpw.thread;

public class Main {
	
	public SynchronizeTest st = new SynchronizeTest();
	
	class thread implements Runnable{

		public void run() {
			System.out.println("run");
			st.synchronizeDescribMethod();
			st.synchronizeBlock();
			
		}
		
	}
	public Runnable getRunnable(){
		return new thread();
	}
	public static void main(String[] args) {
		Main m = new Main();
		System.out.println(m.st);
		Thread th1 = new Thread(m.getRunnable(),"thread one");
		Thread th2 = new Thread(m.getRunnable(),"thread two");
		th1.setPriority(Thread.MIN_PRIORITY);
		th2.setPriority(Thread.MAX_PRIORITY);
		th1.start();
		th2.start();
	}
}

package com.cpw.learn.thread;

/**
 * 
 * @author U123191
 * understand conception of Thread and get distinguish from process
 * know 1. setDeamon() thread
 *      2. thread join(有效时间)
 *      3. 线程同步：synchronized(this) block and key word
 *      4. 线程间通信  wait， notify，notifyAll
 *        
 */

public class ThreadDemo1 {
	
	int i = 100;
	
	class innerClass extends Thread{
		public void run(){
			while(i>0)
				System.out.println("inner thread count No."+i--);
		}
	}
	
	public void tt(){
		
		new innerClass().start();
		new innerClass().start();
		new innerClass().start();
		new innerClass().start();
		
	}
	
	public static void main(String[] args) {
		ThreadDemo1 td = new ThreadDemo1();
		td.tt();
		TestThread  runnableInstance = new TestThread();
		new Thread(runnableInstance).start();
		new Thread(runnableInstance).start();
		new Thread(runnableInstance).start();
		new Thread(runnableInstance).start();
//		System.out.println("main: "+Thread.currentThread().getName());
	}
	
	
	public synchronized void tts1(){
		
		
	}

}

class TestThread implements Runnable{
	int ticket =100;
	public void run(){
		while(ticket>0)
		System.out.println("run: "+Thread.currentThread().getName()+"  is saling ticket No."+ticket--);
	}
}

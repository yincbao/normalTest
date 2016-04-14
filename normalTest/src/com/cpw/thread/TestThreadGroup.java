package com.cpw.thread;

public class TestThreadGroup {
	public class run implements Runnable{
		public run(){
			System.out.println(Thread.currentThread().getName());
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
	
	public Runnable getRun(){
		return new run();
	}
	
	public static void main(String[] args) {
		TestThreadGroup ttg = new TestThreadGroup();
		ThreadGroup tg = new ThreadGroup("group one");
		Thread th1 = new Thread(tg,ttg.getRun());
		Thread th2 = new Thread(tg,ttg.getRun());
		Thread th3 = new Thread(tg,ttg.getRun());
		Thread th4 = new Thread(tg,ttg.getRun());
		th1.start();
		th2.start();
		th3.start();
		th4.start();
		
		Thread[] ths = new Thread[100];//group size is 100
		tg.enumerate(ths);
		for(Thread t:ths){
			System.out.println(t.getName());
		}
	}

}

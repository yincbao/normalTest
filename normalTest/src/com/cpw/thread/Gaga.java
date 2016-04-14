package com.cpw.thread;

public class Gaga {
	
	
	class InnerThread implements Runnable{

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName()+" starting");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+" done");
		}
		
	}
	
	
	public static void main(String args[]) throws InterruptedException{
		Gaga g = new Gaga();
		Thread t1 = new Thread(g.new InnerThread(),"t1");
		Thread t2 = new Thread(g.new InnerThread(),"t2");
		Thread t3 = new Thread(g.new InnerThread(),"t3");
		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();
		System.out.println("all task done, main thread continue");
	}

}

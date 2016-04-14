package com.cpw.thread;

public class JoinThread {
	
	boolean emergency = false;
	int count = 1;
	
	class lowProrityThread implements Runnable{

		@Override
		public void run() {
			while(true){
				System.out.println("lowProrityThread is running.");
				if(emergency){
					Thread high = 	new Thread(new HighPriorityThread());
					high.start();
					try {
						high.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
					
			}
		}
		
	}
	
	 class HighPriorityThread implements Runnable{

		@Override
		public void run() {
			while(count!=10){
				System.out.println("HighPriorityThread is running. "+count);
				count++;
			}
			
		}
	}
	
	public static void main(String args[]) throws InterruptedException{
		JoinThread t = new JoinThread();
		Thread low = new Thread(t.new lowProrityThread());
		low.start();
		Thread.sleep(1000);
		
		t.emergency = true;
		
	}

}

package com.cpw.concurrent;

import java.util.LinkedList;
import java.util.Queue;

public class ObjectWaitNotify {

	final Queue<Object> queue = new LinkedList<Object>();
	final int maxSize = 10;
	int   count;
	
	public Object take(){
		
		synchronized(this){
			try{
				while(queue.size()==0){
					System.out.println("take Thread No."+Thread.currentThread().getId()+" in waiting");
					this.wait();
					
				}
				System.out.println("take Thread No."+Thread.currentThread().getId()+" working");
				Object x = queue.poll();
				--count;
				this.notifyAll();
				return x;
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	public void put(Object element){
		synchronized(this){
			try{
				while(queue.size()==maxSize){
					System.out.println("put Thread No."+Thread.currentThread().getId()+" in waiting");
					this.wait();
					
				}
				System.out.println("put Thread No."+Thread.currentThread().getId()+" working");
				queue.offer(element);
				++count;
				this.notifyAll();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) {
		int threadCount = 5;

		ObjectWaitNotify b = new ObjectWaitNotify();

		for (int i = 0; i < threadCount; i++) {
			if (i % 2 == 0)
				new PutThread(b).start();
			new TakeThread(b).start();
		}

	}

	public static class PutThread extends Thread {
		private final ObjectWaitNotify b;
		private int put = 0;

		public PutThread(ObjectWaitNotify b) {
			this.b = b;
		}

		@Override
		public void run() {
			try {
				
				String data = null;
				while (true) {
					data = "data " + (++put);
					
					System.out.println("Queue size:"+b.queue.size()+"  put thread No."+Thread.currentThread().getName()+"  "+data);
					b.put(data);
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				// e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

	public static class TakeThread extends Thread {
		private final ObjectWaitNotify b;

		public TakeThread(ObjectWaitNotify b) {
			this.b = b;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (true) {
				
				System.out.println(b.take());
			}
		}
	}
}

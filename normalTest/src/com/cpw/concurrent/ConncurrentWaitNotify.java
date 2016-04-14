package com.cpw.concurrent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class ConncurrentWaitNotify {
	// 可重入的互斥锁
	final Lock lock = new ReentrantLock();
	// 表示"缓冲区没满"条件
	final Condition notFull = lock.newCondition();
	// 表示"缓冲区非空"条件
	final Condition notEmpty = lock.newCondition();
	// 存储空间
	final Queue<Object> queue = new LinkedList<Object>();
	final int maxSize = 10;
	int count;

	public void put(Object x) throws InterruptedException {

		lock.lock();
		try {
			while (count == maxSize){
				System.out.println("put: thread No."+Thread.currentThread().getId()+" wait");
				notFull.await();
			}
				
			queue.offer(x);
			++count;

			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0){
				System.out.println("take: thread No."+Thread.currentThread().getId()+" wait");
				notEmpty.await();
			}
				
			Object x = queue.poll();
			--count;
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		int threadCount = 5;

		ConncurrentWaitNotify b = new ConncurrentWaitNotify();

		for (int i = 0; i < threadCount; i++) {
			if (i % 2 == 0)
				new PutThread(b).start();
			new TakeThread(b).start();
		}

	}

	public static class PutThread extends Thread {
		private final ConncurrentWaitNotify b;
		private int put = 0;

		public PutThread(ConncurrentWaitNotify b) {
			this.b = b;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(2000);
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
		private final ConncurrentWaitNotify b;

		public TakeThread(ConncurrentWaitNotify b) {
			this.b = b;
		}

		@Override
		public void run() {
			try {
				while (true) {
					System.out.println(b.take());
				}
			} catch (InterruptedException e) {
				// e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
}

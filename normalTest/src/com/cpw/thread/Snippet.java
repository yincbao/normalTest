package com.cpw.thread;

public class Snippet {
		public static void main(String[] args) throws InterruptedException {
	
			final Thread thread1 = new Thread() {
				public void run() {
					System.out.println("我是第一个");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("我虽然睡了一会，但我是第二个");
				};
			};
			thread1.start();
			//thread1.join();		//在这阻塞主线程
			Thread thread2 = new Thread() {
				public void run() {
					try {
						thread1.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}// 等待t1线程 执行完结，才继续向下执行	在这阻塞子线程
					System.out.println("我是第三个");
				};
			};
			thread2.start();
		}
}


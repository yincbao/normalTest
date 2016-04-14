package com.cpw.myExecutor;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest2 {

	public static void main(String[] args) {
		
		// 创建线程数量为固定5个的线程池
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
		// 向线程池提交任务即可
		for(int i = 0; i<2; i++){
			
			scheduledThreadPool.schedule(new MyInnerTask(), 5, TimeUnit.SECONDS);
		}
		// 主线程给出计时
		for(int i=1; i<=5; i++){
			
			System.out.println("等待 " + i + " 秒！");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		scheduledThreadPool.shutdown();
	}
	
	static class MyInnerTask implements Runnable {

		@Override
		public void run() {
			
			System.out.println(Thread.currentThread().getName() + " come !!!");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
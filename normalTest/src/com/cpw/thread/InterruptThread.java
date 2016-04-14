package com.cpw.thread;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 停止一个线程:
 * thread.stop、resume supend等方法已经被废弃，原因之一是，stop以后，线程已经获得的对象锁无法释放，可能导致死锁。
 * thread.interrupt(),该方法只是把线程的interrpuptable状态置为可以终止。Thread.interrupted可以获得当前线程是够可以被阻断标识
 * 但是，该方法不会真的去阻断一个线程。
 * 配置Thread.sleep,Object.wait,thread.join()等会抛出InterruptedException异常的方法使用才能阻断一个线程执行。
 * jvm规定，以上会抛出InterruptedException的方法在线程状态是 可阻断 是，抛出异常。
 * ClassName: InterruptThread
 * @description
 * @author yin_changbao
 * @Date   Aug 17, 2015
 *
 */
public class InterruptThread {

	AtomicBoolean flag = new AtomicBoolean(false);
	
	class NormalThread implements Runnable {

		@Override
		public void run() {

			try {
				while(!flag.get()){
					System.out.println("some condition not fill, busniess thread sleep for a while.");
					
					Thread.sleep(5*1000);
				}
				
			} catch (InterruptedException e) {
				System.out.println("some one stopped me!.");
			}
		}
	}
	
	class StopThread extends  Thread{

		@Override
		public void run() {
			Thread busniess = new Thread( new NormalThread());
			busniess.start();
			System.out.println("only set thread NormalThread interrupt to  interruptable, when to stop it determined by "
					+ "jvm");
			busniess.interrupt();
			
		}
		
	}
	
	
	public static void main(String args[]){
		InterruptThread t = new InterruptThread();
		Thread stopper = t.new StopThread( );
		stopper.start();
	}
}

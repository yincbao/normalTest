package com.cpw.learn.thread;

/**
 * 
 * @author U123191
 * 
 *         ��֤��synchronized���η���ʱ���ǲ���ȥthis���� ����synchronized
 *         �����������߳�һ���Ե���һ������ѭ�������ǲ��Ǵ�������߳�����
 */

public class SynchronizedObjLock {
	private ThreadClass tc = new ThreadClass();
	private ThreadClass tc1 = new ThreadClass();

	class innerThread1 extends Thread {
		public void run() {
			tc.method1();
		}
	}

	class innerThread2 extends Thread {
		public void run() {
			System.out.println("instance 1");
			tc1.method1();
		}
	}

	public void t() {
		new innerThread1().start();
		new innerThread2().start();
	}

	public static void main(String[] args) {

		SynchronizedObjLock s = new SynchronizedObjLock();
		s.t();

	}
}

class ThreadClass {
	public synchronized void method1() {
		while (true)
			System.out.println(Thread.currentThread() + "is running method 1");
	}

	public synchronized void method2() {
		while (true)
			System.out.println(Thread.currentThread() + "is running method 2");
	}
}
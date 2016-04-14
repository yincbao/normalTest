package com.cpw.thread;
/**
 * wait notify 必须在明确获得对象（object.wait）锁的地方使用，否则会报java.lang.IllegalMonitorStateException
 * 即wait notify只能出现在synchronized方法、语句块
 * 
 * 其次，不能在synchronized或方法内部改变所对象的引用，因为锁是在家对象上的而不是引用上
 * Student stu = new Student();
 * synchronized (stu){
 * 		stu =new Student();
 * }
 * 
 * ClassName: WaiteNotify
 * @description
 * @author yin_changbao
 * @Date   Aug 17, 2015
 *
 */
public class WaiteNotify {
	private  String[] flag = {"true"};

	class Notifier extends Thread{
		public Notifier(String name) {
			super(name);
		}
		public void run() {		
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (flag){
				flag[0] = "false";
				flag.notifyAll();
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(getName() + " release object lock @ "+System.currentTimeMillis());
			}
		}
	};

	class Waiter extends Thread {
		public Waiter(String name) {
			super(name);
		}

		public void run() {
			synchronized (flag){
				System.out.println(getName() + " got object lock @ "+System.currentTimeMillis());
				while (flag[0]!="false") {
					System.out.println(getName()+" wait") ;
					long waitTime = System.currentTimeMillis();
					try {
						flag.wait();
						//flag[0]="true";
						System.out.println(getName()+" wait1") ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					waitTime = System.currentTimeMillis() - waitTime;
					System.out.println("wait time :"+waitTime);
				}
				System.out.println(getName() + " end waiting!");
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		WaiteNotify test = new WaiteNotify();
		Notifier notifier =test.new Notifier("notify01");
		Waiter w01 = test.new Waiter("waiter01");
		Waiter w02 = test.new Waiter("waiter02");
		Waiter w03 = test.new Waiter("waiter03");
		notifier.start();
		w01.start();
		w02.start();
		w03.start();
	}

}

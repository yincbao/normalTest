package com.cpw.learn.thread;

/**
 * 
 * 线程同步，synchronized 解决了，线程不同步造成的线程不安全问题，张三可能被输出成female(刚刚把人名改为张三，还没有来得及改变male，时间片用完，这时cusumer时间片开始读，张三是新的的性别就变成之前的旧数据female)
 * 线程间通信，用wait和notify解决了cusumer刚刚放开锁，时间片还没有用完，结果又抢到锁了，再一次读了一遍，出现连续输出多次zhangsan male，而不是预期的一次zhangsan male一次lisi female。
 *
 */

public class optimizingThreadSynchronized {
	
	public static void main(String[] args) {
		Cache1 ca = new Cache1();
		new Thread(new cusmuer1(ca)).start();
		new Thread(new producer1(ca)).start();
	}
}

class Cache1{
	
	private String key = "Unknown";
	private String value = "Inknown";
	private boolean bFull = true;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public synchronized void get(){
		if(bFull)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println(Thread.currentThread().getName()+"-"+this.key+" "+this.value);
		this.bFull = true;
		notify();
		
	}
	
	public synchronized void set(String key, String value){
		if(!bFull)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		this.key = key;
		this.value = value;
		this.bFull = false;
		notify();
		}
}

class producer1 implements Runnable{
	Cache1 c;
	 public producer1(Cache1 c){
		 this.c = c;
	 }
	

	public void run() {
		int i = 0;
		while(true){
			if(i==0)
				c.set("Zhangsan", "male");
			else
				c.set("Lisi", "female");
			i=(i+1)%2;
		}
	}
	
	
}

class cusmuer1 implements Runnable{
	Cache1 ca ;
	public cusmuer1(Cache1 ca){
		this.ca = ca;
	}
	public void run(){
		while(true)
		ca.get();
	}
}

package com.cpw.learn.thread;

/**
 * 
 * �߳�ͬ����synchronized ����ˣ��̲߳�ͬ����ɵ��̲߳���ȫ���⣬�������ܱ������female(�ոհ�������Ϊ��������û�����ü��ı�male��ʱ��Ƭ���꣬��ʱcusumerʱ��Ƭ��ʼ�����������µĵ��Ա�ͱ��֮ǰ�ľ�����female)
 * �̼߳�ͨ�ţ���wait��notify�����cusumer�ոշſ�����ʱ��Ƭ��û�����꣬������������ˣ���һ�ζ���һ�飬��������������zhangsan male��������Ԥ�ڵ�һ��zhangsan maleһ��lisi female��
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

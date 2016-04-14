package com.cpw.learn.thread;

class ThreadSyncronized {
	public static void main(String[] args) {
		Cache cache = new Cache();
		new Thread(new producer(cache)).start();
		new Thread(new cusumer(cache)).start();
	}
	
	
}

class cusumer implements Runnable {
	
	Cache cahce;
	public cusumer(Cache cahce){
		this.cahce = cahce;
	}
	
	public void run(){
		while(true){
		synchronized(cahce){
			
				if(cahce.bFull)
					try {
						cahce.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"-"+cahce.name+" "+cahce.sex);
					cahce.bFull=true;
					cahce.notify();
			}
		}
	}
	
}

class producer implements Runnable{
	
	Cache cahce;
	public producer(Cache cahce){
		this.cahce = cahce;
	}
	int i=0;
	public void run() {
		while(true){
			synchronized(cahce) {
				if(!cahce.bFull)
					try {
						cahce.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(i==0){
					cahce.name="Zhangsan";
					cahce.sex="male";
					System.out.println(Thread.currentThread().getName());
				}else{
					cahce.name="lisi";
					cahce.sex="female";
					System.out.println(Thread.currentThread().getName());
				}
				i=(i+1)%2;
				cahce.bFull = false;
				cahce.notify();
				
			}
			}
		}
		
}

class Cache{
	public String name="nukown";
	public String sex="nukown";
	public boolean bFull;
}

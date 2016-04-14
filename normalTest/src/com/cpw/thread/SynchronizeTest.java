package com.cpw.thread;

public class SynchronizeTest {
	
	public int age;
	public  String synString  = new String();
	
	public synchronized void synchronizeDescribMethod(){
		while(true)
			System.out.println(Thread.currentThread ().getName()+" get the lock! ");
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSynString() {
		return synString;
	}

	public void setSynString(String synString) {
		this.synString = synString;
	}
	public void synchronizeBlock(){
		synchronized(String.class){
			while(true)
				System.out.println(Thread.currentThread ().getName()+" get the lock! ");
		}
	}

	
}

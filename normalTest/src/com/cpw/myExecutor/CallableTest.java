package com.cpw.myExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
	
	public class CallableClass implements Callable{

		public Object call() throws Exception {
			System.out.println("1111");
			return "ok";
			
		}
		
	}
	
	public CallableClass getIns(){
		return new CallableClass();
	}
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CallableTest ca = new CallableTest();
		CallableClass cc = ca.getIns();
		FutureTask ft = new FutureTask(cc);
		Thread t = new Thread(ft);
		t.start();
		Thread.sleep(2000);
		if(ft.isDone()){
			System.out.println("222");
			String str = (String) ft.get();
			System.out.println(str);
		}
	}
	
}

package com.cpw.myExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

	static ExecutorService es = ExecutorServiceFactory.getExecutorService();
	static CompletionService<Job> completionService = new ExecutorCompletionService<Job>(es);
	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		
		String result = "a";
		List<Future<Job>> re = new ArrayList<Future<Job>>();
		for(int i=0;i<5;i++){
			CustomlizedJob job = new CustomlizedJob();
			job.setID(String.valueOf(i));
			re.add(es.submit(job));
			
		}
		for(Future<Job> f:re){
			try {
				System.out.println("#####  "+f.get(10, TimeUnit.SECONDS).getID());
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	
			
		System.out.println("~~~~~~~  "+result);	
	}

}

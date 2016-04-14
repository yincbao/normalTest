package com.cpw.myExecutor;


import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletionServiceTest1 {

	public static void main(String[] args) {
		
		// 创建具有固定两个线程的线程池！
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		// 创建CompletionService，需要传递一个线程池，具体任务的执行还是由这个线程池去执行
		/*
		 * 在大多数用到future的场景中，比如，主线程等子线程返回，然后继续业务逻辑的情况下，是不能用CompletionService的
		 * 因为，CompletionService。take()会一在等当前完成的job，也就是说，while((future = completionService.take()) != null)
		 * 是永远退步出来的
		 */
		CompletionService<String> completionService = new ExecutorCompletionService<String>(threadPool);
		completionService.submit(new MyLongTask());
		completionService.submit(new MyShortTask());
		try {
			Future<String> future = completionService.take();
			while(future != null){
				
				System.out.println(future.get());
				 future = completionService.take();
				System.out.println("%%%%%%%%");
			}
			System.out.println(" main done");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	// 需要长时间执行的任务
	static class MyLongTask implements Callable<String>{

		@Override
		public String call() throws Exception {
			
			// 睡眠6秒，代表执行某段复杂业务逻辑
			Thread.sleep(3000);
			String result = "大大大大大订单下单成功！";
			return result;
		}
		
		
	}
	
	// 短时间就可执行完毕的任务
	static class MyShortTask implements Callable<String>{

		@Override
		public String call() throws Exception {
			
			// 睡眠3秒，代表执行某段复杂业务逻辑
			Thread.sleep(1000);
			String result = "小小小订单下单成功！";
			return result;
		}
		
		
	}

}

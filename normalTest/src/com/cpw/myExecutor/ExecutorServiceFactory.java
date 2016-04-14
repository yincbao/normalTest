package com.cpw.myExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceFactory {
	private static ExecutorService es = Executors.newFixedThreadPool(40);

	public static ExecutorService getExecutorService() {
		return es;
	}
}

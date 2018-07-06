package com.hey.java.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyTaskTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long startTime = System.currentTimeMillis();

		//ExecutorService es = Executors.newFixedThreadPool(2);
		//ExecutorService es = Executors.newSingleThreadExecutor();
		ExecutorService es = Executors.newCachedThreadPool();
		
		List<MyTask> taskList = new ArrayList<MyTask>();
		for (int i = 0; i < 5; i++) {
			taskList.add(new MyTask(i));
		}

		int sum = 0;
		List<Future<Integer>> listFuture = es.invokeAll(taskList);
		for (Future<Integer> future : listFuture) {
			sum += future.get();
			System.out.println(future.get());
		}

		es.shutdown();
		long endTime = System.currentTimeMillis();
		System.out.println("求和："+sum);
		System.out.println("耗时：" + (endTime - startTime) + "ms");
	}
}

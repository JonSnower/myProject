package com.hey.java.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompleteServiceTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long sTime = System.currentTimeMillis();

		//ExecutorService executorService = Executors.newFixedThreadPool(10);
		ExecutorService executorService = Executors.newCachedThreadPool();

		CompletionService<String> completionService = new ExecutorCompletionService<String>(executorService);

		/**
		 * 产生一个随机数，模拟不同的任务的处理时间不同
		 */
		for (int i = 0; i < 10; i++) {
			completionService.submit(new Callable<String>() {
				public String call() {
					int rnt = new Random().nextInt(5);
					//int rnt = 3;

					try {
						Thread.sleep(rnt * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("run rnt = " + rnt);
					return String.valueOf(rnt * 1000);
				}
			});
		}

		/**
		 * 获取结果时，总是先拿到队列上已经存在的对象，这样不用依次等待结果 显然效率更高
		 */
		for (int i = 0; i < 10; i++) {
			Future<String> future = completionService.take();
			System.out.println(future.get());
		}
		executorService.shutdown();
		System.out.println("总耗时 ：" + (System.currentTimeMillis() - sTime));
	}
}
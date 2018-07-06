package com.hey.java.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class NonCompleteServiceTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		Future<String>[] futures = new FutureTask[10];
		/**
		 * 产生一个随机数，模拟不同的任务的处理时间不同
		 */
		for (int i = 0; i < 10; i++) {
			futures[i] = executorService.submit(new Callable<String>() {
				public String call() {
					int rnt = new Random().nextInt(5);

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
		 * 获取结果时，如果任务没有完成，则阻塞,在顺序获取结果时， 可能别的任务已经完成，显然效率不高
		 */
		for (int i = 0; i < futures.length; i++) {
			System.out.println("结果：" + futures[i].get());
		}
		executorService.shutdown();
	}

}
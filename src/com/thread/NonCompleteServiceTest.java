package com.thread;

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
		 * ����һ���������ģ�ⲻͬ������Ĵ���ʱ�䲻ͬ
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
		 * ��ȡ���ʱ���������û����ɣ�������,��˳���ȡ���ʱ�� ���ܱ�������Ѿ���ɣ���ȻЧ�ʲ���
		 */
		for (int i = 0; i < futures.length; i++) {
			System.out.println("�����" + futures[i].get());
		}
		executorService.shutdown();
	}

}
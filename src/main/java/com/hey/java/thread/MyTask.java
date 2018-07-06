package com.hey.java.thread;

import java.util.concurrent.Callable;

public class MyTask implements Callable<Integer> {

	private int num;
	
	public MyTask(int num) {
		this.num = num;
	}

	@Override
	public Integer call() throws Exception {
		Thread.sleep(1000);
		System.out.println(Thread.currentThread().getName());
		return this.num;
	}

}

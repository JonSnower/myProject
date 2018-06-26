package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolDemo {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		for (int i = 0; i < 50; i++) {
			pool.submit(new ThreadRunner((i + 1)));
		}
		pool.shutdown();
	}

}
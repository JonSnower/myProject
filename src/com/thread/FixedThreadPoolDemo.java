package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
�ܽ᣺ 
- �����߳������̶������ᷢ���仯 
- ʹ���޽��LinkedBlockingQueue��Ҫ�ۺϿ����������������������ɹ�ʣ�����ܵ��¶��ڴ������ 
- ����һЩ���ȶ��̶ܹ������沢���̣߳������ڷ�����
*/
public class FixedThreadPoolDemo {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 50; i++) {
			pool.submit(new ThreadRunner((i + 1)));
		}
		pool.shutdown();
	}
	
}
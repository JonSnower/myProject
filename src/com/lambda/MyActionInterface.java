package com.lambda;

public interface MyActionInterface {
	void SaySomeThing(String str);
	
	// jdk8�����ԣ��ӿڷ�����deault���ο�д�������ݣ�ʵ����Ҳֱ�ӵ��ã�default�����ɴ��ڶ��
	default void say(String str) {
		System.out.println(str);
	}
}

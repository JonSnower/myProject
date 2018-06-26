package com.lambda;

public interface MyActionInterface {
	void SaySomeThing(String str);
	
	// jdk8新特性，接口方法用deault修饰可写方法内容，实现类也直接调用，default方法可存在多个
	default void say(String str) {
		System.out.println(str);
	}
}

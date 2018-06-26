package com.lambda;

public class MyAction {
	
	private static void excuteSay(MyActionInterface action,String str) {
		action.SaySomeThing(str);
	}
	
	public static void main(String[] args) {
		
		excuteSay(new MyActionInterface() {
			@Override
			public void SaySomeThing(String s) {
				System.out.println(s);
			}},"help world£¡");
		
		excuteSay((item) -> System.out.println(item),"hello world!");
	}
	
}

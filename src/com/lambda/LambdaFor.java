package com.lambda;

import java.util.Arrays;
import java.util.List;

public class LambdaFor {
	public static void main(String[] args) {
		String[] strArr = { "abc", "cba", "bba" };

		List<String> strList = Arrays.asList(strArr);
		
		// 之前的循环方式
		for (String str : strList) {
			System.out.print(str + ";");
		}
		System.out.println();
		
		//lambda表达式
		strList.forEach((str) -> System.out.print(str + ";"));
		System.out.println();
		
		strList.forEach(System.out::println);

	}
}

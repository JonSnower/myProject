package com.hey.java.test;

import java.util.HashMap;
import java.util.Map;

public class Test001 {
	public static void main(String[] args) {
		Map map = new HashMap<>();
		long i = 1;
		while(true) {
			map.put("key-"+i, "value-"+i);
			System.out.println(i);
			i++;
		}
	}
}

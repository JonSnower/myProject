package com.hey.java;

import java.util.HashMap;
import java.util.Map;

public class test {
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("abc", "aa");

		map.keySet().forEach(s -> {
			System.out.println(map.get(s));
		});

	}

}

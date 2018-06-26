package com.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortList {
	
	
	private static List<String> list = Arrays.asList("abc", "nba", "cba");

	// �ɵ�д��
	private static void oldSort() {
		Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				if (a.charAt(0) <= b.charAt(0)) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}

	// �µ�д��
	private static void newSort() {
		Collections.sort(list, (a, b) -> a.charAt(0) < b.charAt(0) ? 1 : -1);
	}

	public static void main(String[] args) {
		newSort();
		list.forEach(item -> System.out.println(item));
	}
}

package com.regexp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {
	public static void main(String[] args) {
		String str = "1,3,4,6";
		String[] intArr = str.split(",");
		
		Set set = new HashSet<String>(Arrays.asList(intArr));
		
		
		System.out.println(intArr.length == set.size());
		
		
		String s = "";
		String s1 = null;
		System.out.println(s == "");
		System.out.println(s1 == "");
		
	}
}

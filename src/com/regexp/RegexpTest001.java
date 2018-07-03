package com.regexp;

import java.util.regex.Pattern;

public class RegexpTest001 {
	public static void main(String[] args) {

		String content = "I am noob from runoob com";
		String pattern = ".*runoob.*";
		regexpTest(content, pattern);

		// 匹配其中一个字符M/F/m/f
		content = "m";
		pattern = "[MFmf]";
		regexpTest(content, pattern);

		// 匹配1-120的字符
		content = "120";
		pattern = "^[1-9]\\d?$|^1[01]\\d$|^120$";
		regexpTest(content, pattern);
		// 1-99范围的数字
		// ^[1-9]\d?$ //\d表示[0-9]即0到9任意数字，\d?表示数字（0到9范围）最多只能出现一次（可以不出现）
		// 100-119范围的数字
		// ^1[01]\d$ //[01]表示0或者1，\d表示[0-9]，同上。
		// 120的正则表达式
		// ^120$
		// 所以1-120范围数字的正则可以写成如下（|表示或者的意思）：
		// ^[1-9]\d?$|^1[01]\d$|^120$
		
		// 0-9
		content = "0,3,9,9,";
		pattern = "^(\\d,)+$";
		regexpTest(content, pattern);
		
		// 0-9
		content = "1,f";
		pattern = "^([1-7],)+$";
		regexpTest(content, pattern);
		
	}

	public static void regexpTest(String content, String pattern) {
		boolean isMatch = Pattern.matches(pattern, content);
		System.out.println(content + "与 " + pattern + " 是否匹配？" + isMatch);
	}

}

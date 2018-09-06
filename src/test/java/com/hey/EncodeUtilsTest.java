package com.hey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hey.java.file.EncodeUtils;

public class EncodeUtilsTest {

	@Test
	public void Test001() {
		System.out.println(EncodeUtils.stringToUnicode("平安"));
	}

	@Test
	public void Test002() {
		System.out.println(EncodeUtils.unicodeToString("\\ufeff\\u5e73\\u5b89"));
	}
	
	@Test
	public void Test003() {
		System.out.println(EncodeUtils.convertStringToUTF8("平安"));
	}
	
	@Test
	public void Test004() {
		System.out.println(EncodeUtils.convertUTF8ToString("E5B9B3E5AE89"));
	}
	
	@Test
	public void Test005() {
		String str = EncodeUtils.unicodeToUtf8("\ufeff\u5e73\u5b89");
		System.out.println(str);
		System.out.println(EncodeUtils.convertUTF8ToString(str));
	}
	
	@Test
	public void Test006() {
		String str = EncodeUtils.utf8ToUnicode("EFBBBFE5B9B3E5AE89");
		System.out.println(str);
		System.out.println(EncodeUtils.unicodeToString(str));
	}
	
	@Before
	public void beforeExecute() {
		System.out.println("程序开始执行~~~~~~~~~~~~~~");
	}
	
	@After
	public void afterExecute() {
		System.out.println("程序线束执行~~~~~~~~~~~~~~");
	}
	
}
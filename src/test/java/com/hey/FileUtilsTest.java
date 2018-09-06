package com.hey;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hey.java.file.FileUtils;

public class FileUtilsTest {

	@Test
	public void Test001() {
		String content = FileUtils.readFile("D:\\data\\wc.txt");
		System.out.println(content);
		FileUtils.fileLinesWrite("C:\\Users\\Administrator\\Desktop\\abc.txt", content, true);
	}
	
	@Test
	public void Test002() {
		
	}
	
	@Test
	public void Test003() {
		FileUtils.copyFile("abc.txt","C:\\Users\\Administrator\\Desktop","C:\\Users\\Administrator\\Desktop\\abc");
	}
	
	@Test
	public void Test004() {
		FileUtils.readFileByBytes("C:\\Users\\Administrator\\Desktop\\abc\\abc.txt");
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
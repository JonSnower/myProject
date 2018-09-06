package com.hey;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hey.java.file.RandomAccessFileUtil;

public class RandomAccessFileUtilTest {

	
	@Test
	public void Test001() {
		RandomAccessFileUtil.WriteContent();
	}
	
	@Test
	public void Test00() {
		RandomAccessFileUtil.ReadContent();
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
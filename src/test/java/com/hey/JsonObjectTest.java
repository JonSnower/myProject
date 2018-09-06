package com.hey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.hey.java.other.JsonUtils;

public class JsonObjectTest {

	@Test
	public void Test000() {
		System.out.println(JsonUtils.isJSONValid(null));
	}

	@Test
	public void Test001() {
		String jsonStr = "{'a':'aaa','b':'bbb'}";
		JSONObject jo = JSONObject.parseObject(jsonStr);
		System.out.println(jo);
		jo.replace("a", "abc");
		System.out.println(jo);
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
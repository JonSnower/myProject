package com.hey.searchMatching.hanlp;

import org.nlpcn.commons.lang.finger.SimHashService;

public class HanLPTest {
	public static void main(String[] args) {
		
		String s1 = "abc";
		String s2 = "55";
		String s3 = "";
		String s4 = "";
		
		SimHashService hash1 = new SimHashService();
		
		System.out.println(hash1.hmDistance(s1, s2));
		
	}
	
	
	
}

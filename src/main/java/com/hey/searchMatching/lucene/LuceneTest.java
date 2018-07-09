package com.hey.searchMatching.lucene;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;

public class LuceneTest {

	@Test
	public void createIndex() {
		LuceneUtils.createIndex("user_info", false);
		// LuceneUtils.createIndex("student_info", true);
	}

	@Test
	public void query() throws IOException, ParseException {
		LuceneUtils.queryByColoumName("userName", "辽阔");
	}

	@Test
	public void updateIndex() throws IOException {
		LuceneUtils.updateIndexById("table_id_104", "userName", "谷歌地图", "user_info");
	}

	@Test
	public void query2() throws IOException, ParseException {
		LuceneUtils.queryByColoumName("userName", "辽阔");
	}

	@Test
	public void query3() throws IOException, ParseException {
		LuceneUtils.queryByColoumName("userName", "谷歌");
	}

	@Test
	public void deleteIndexById() throws IOException {
		LuceneUtils.deleteIndexById("table_id_104");
	}
	
	@Test
	public void query4() throws IOException, ParseException {
		LuceneUtils.queryByColoumName("userName", "谷歌");
	}
	
	@Test
	public void query5() throws IOException, ParseException {
		LuceneUtils.queryByColoumName("userName", "中华");
	}
	
	@Test
	public void deleteIndexByTable() throws IOException {
		LuceneUtils.deleteIndexByTable("user_info");
	}
	
	@Test
	public void query6() throws IOException, ParseException {
		LuceneUtils.queryByColoumName("userName", "中华");
	}
	
	
}

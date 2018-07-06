package com.hey.searchMatching.hanlp;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
 
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
 
import com.hankcs.lucene.HanLPAnalyzer;
import com.hankcs.lucene.HanLPIndexAnalyzer;
 
/**
 * 索引数据库字段建立索引文件
 * 
 * @author zhengzhen
 *
 */
public class JdbcIndexDemo {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/mydb?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";
			String password ="heyujiao";
			String userName ="root";
			String sql ="select * from user_info";
			try (
					Connection conn = DriverManager.getConnection(url,userName,password);
					PreparedStatement sta =conn.prepareStatement(sql);
					ResultSet rs = sta.executeQuery();
					){
				/**
				 * 1.设置索引文件保存路径
				 */
					Directory directory = FSDirectory.open(Paths.get("xxxx_index"));
					/**
					 * 2.创建分词器
					 */
					Analyzer analyzer = new HanLPIndexAnalyzer();
					/**
					 * 3.分词器配置
					 */
					IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
					indexWriterConfig.setOpenMode(OpenMode.CREATE);
					/**
					 * 4.创建索引输出流
					 */
					IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
					/**
					 * 5.循环遍历创建索引文档
					 */
					while (rs.next()) {
						/**
						 * 5.1.创建文档
						 */
						Document document = new Document();
						/**
						 * 5.2.添加字段
						 */
						
						IndexableField tableName = new TextField("tableName", "user_info", Store.YES);
						document.add(tableName);
					
						String id = rs.getString("id");
						if( id != null) {
							IndexableField idField = new TextField("id", id, Store.YES);
							document.add(idField);
						}
						
						String name = rs.getString("name");
						if( name != null) {
							IndexableField sectionNameField = new TextField("userName", name, Store.YES);
							document.add(sectionNameField);
						}
						
						indexWriter.addDocument(document);
						
					}
					indexWriter.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
		}
		
	}
	/**
	 * HanLPAnalyzer
	 * 这个分词器对于长词不会切割 ，例如 “中华人民共和国” 是一个长词会保留下来
	 * @throws IOException
	 */
	@Test
	public void hanLPAnalyzerTest() throws IOException {
		String text = "中华人民共和国很辽阔";
		for (int i = 0; i < text.length(); ++i)
		{
		    System.out.print(text.charAt(i) + "" + i + " ");
		}
		System.out.println();
		Analyzer analyzer = new HanLPAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream("field", text);
		tokenStream.reset();
		while (tokenStream.incrementToken())
		{
		    CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
		    // 偏移量
		    OffsetAttribute offsetAtt = tokenStream.getAttribute(OffsetAttribute.class);
		    // 距离
		    PositionIncrementAttribute positionAttr = tokenStream.getAttribute(PositionIncrementAttribute.class);
		    System.out.println(attribute + " " + offsetAtt.startOffset() + " " + offsetAtt.endOffset() + " " + positionAttr.getPositionIncrement());
		}
		/* 输出：
		 * 中0 华1 人2 民3 共4 和5 国6 很7 辽8 阔9 
		 * 中华人民共和国 0 7 1
		 * 很 7 8 1
		 * 辽阔 8 10 1
		 */
	}
	/**
	 * HanLPIndexAnalyzer
	 * 这个分词器会对长词进行分割 “中华人民共和国” 会切分成“中华人民共和国” “中华” “人民”等等
	 * @throws IOException
	 */
	@Test
	public void hanLPIndexAnalyzerTest() throws IOException {
		String text = "中华人民共和国很辽阔";
		for (int i = 0; i < text.length(); ++i)
		{
		    System.out.print(text.charAt(i) + "" + i + " ");
		}
		System.out.println();
		Analyzer analyzer = new HanLPIndexAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream("field", text);
		tokenStream.reset();
		while (tokenStream.incrementToken())
		{
		    CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
		    // 偏移量
		    OffsetAttribute offsetAtt = tokenStream.getAttribute(OffsetAttribute.class);
		    // 距离
		    PositionIncrementAttribute positionAttr = tokenStream.getAttribute(PositionIncrementAttribute.class);
		    System.out.println(attribute + " " + offsetAtt.startOffset() + " " + offsetAtt.endOffset() + " " + positionAttr.getPositionIncrement());
		}
		/* 输出：
		 * 中0 华1 人2 民3 共4 和5 国6 很7 辽8 阔9 
		 * 中华人民共和国 0 7 1
		 * 中华人民 0 4 1
		 * 中华 0 2 1
		 * 华人 1 3 1
		 * 人民共和国 2 7 1
		 * 人民 2 4 1
		 * 共和国 4 7 1
		 * 共和 4 6 1
		 * 很 7 8 1
		 * 辽阔 8 10 1
		 */
	}
}
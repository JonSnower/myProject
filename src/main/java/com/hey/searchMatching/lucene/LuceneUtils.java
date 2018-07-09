package com.hey.searchMatching.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

import com.hankcs.lucene.HanLPIndexAnalyzer;

/**
 * 索引工具类，增删改查
 * 
 * @author Administrator
 *
 */
public class LuceneUtils {

	private static final String FILE_PATH = "xxxx_index";

	private static FSDirectory directory;

	private static IndexWriterConfig indexWriterConfig;

	static {
		try {
			// 创建目录对象
			directory = FSDirectory.open(Paths.get(FILE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 创建索引写出器配置对象
		indexWriterConfig = new IndexWriterConfig(new HanLPIndexAnalyzer());
	}

	/**
	 * 读取数据库表数据并创建索引
	 * 
	 * @param tableName
	 *            表名
	 * @param isAppend
	 *            追加更新或是覆盖
	 */
	public static void createIndex(String tableName, Boolean isAppend) {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/mydb?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";
			String password = "heyujiao";
			String userName = "root";
			String sql = "select * from " + tableName;

			try (Connection conn = DriverManager.getConnection(url, userName, password);
					PreparedStatement sta = conn.prepareStatement(sql);
					ResultSet rs = sta.executeQuery();) {

				// 追加模式(APPEND)还是覆盖模式(CREATE)
				if (isAppend) {
					indexWriterConfig.setOpenMode(OpenMode.APPEND);
				} else {
					indexWriterConfig.setOpenMode(OpenMode.CREATE);
				}

				// 4.创建索引输出流
				IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

				// 5.循环遍历创建索引文档
				while (rs.next()) {

					// 5.1.创建文档
					Document document = new Document();

					// 5.2.添加字段
					// StringField不做分词处理
					IndexableField tabName = new StringField("tableName", tableName, Store.YES);
					document.add(tabName);

					String id = rs.getString("id");
					if (id != null) {
						IndexableField idField = new StringField("id", id, Store.YES);
						document.add(idField);
					}

					// TextField需要做分词处理
					String name = rs.getString("name");
					if (name != null) {
						IndexableField sectionNameField = new TextField("userName", name, Store.YES);
						document.add(sectionNameField);
					}

					indexWriter.addDocument(document);

				}
				indexWriter.commit();

				System.out.println("=====创建索引成功=====");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 根据表id和字段更新索引
	 * 
	 * @param id
	 *            数据库表id
	 * @param field
	 *            数据库表字段
	 * @param fieldContent
	 *            具体内容
	 * @param tableName
	 *            对应哪张表
	 * @throws IOException
	 */
	public static void updateIndexById(String id, String field, String fieldContent, String tableName)
			throws IOException {

		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		// 创建索引写出器
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

		// 创建更新后的文档对象
		Document document = new Document();
		document.add(new StringField("tableName", tableName, Field.Store.YES));
		document.add(new StringField("id", id, Field.Store.YES));
		document.add(new TextField(field, fieldContent, Field.Store.YES));
		// 只能通过 Term 词条更新指定文档
		indexWriter.updateDocument(new Term("id", id), document);

		indexWriter.commit();
		indexWriter.close();

		System.out.println("=====更新索引成功=====");
	}

	/**
	 * 根据字段名称，查找数据
	 * 
	 * @param coloumName
	 * @param strName
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void queryByColoumName(String coloumName, String strName) throws IOException, ParseException {

		DirectoryReader directoryReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

		/*
		 * 创建查询解析器 1.查询字段名称 2.分词解析器
		 */
		QueryParser queryParser = new QueryParser(coloumName, new HanLPIndexAnalyzer());
		// 获取查询对象
		Query query = queryParser.parse(strName);

		/*
		 * 搜索数据 1.查询解析器解析后的查询结果 2.查询结果的最大条数
		 */
		TopDocs topDocs = indexSearcher.search(query, 10);

		// 获取总条数
		int totalHits = topDocs.totalHits;
		System.out.println("本地搜索共查询到 " + totalHits + " 匹配记录");

		/*
		 * 得分文档数组 1.doc 文档编号（ID） 2.score 文档得分数
		 */
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;

		for (ScoreDoc scoreDoc : scoreDocs) {
			// 文档编号
			int docID = scoreDoc.doc;
			// 通过索引读取器 根据文档编号获取文档
			Document document = directoryReader.document(docID);

			System.out.println("tableName: " + document.get("tableName"));
			System.out.println("id: " + document.get("id"));

			// 文档得分
			System.out.println("Score: " + scoreDoc.score);
		}
	}

	/**
	 * 根据表id来删除索引
	 * 
	 * @param id
	 * @throws IOException
	 */
	public static void deleteIndexById(String id) throws IOException {

		// 创建索引写出器
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

		// 通过Term词条删除指定文档
		indexWriter.deleteDocuments(new Term("id", id));

		indexWriter.commit();
		indexWriter.close();

		System.out.println("=====删除索引成功=====");
	}

	/**
	 * 根据表名来删除索引
	 * 
	 * @param talbe
	 * @throws IOException
	 */
	public static void deleteIndexByTable(String talbe) throws IOException {

		// 创建索引写出器
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

		// 通过Term词条删除指定文档
		indexWriter.deleteDocuments(new Term("tableName", talbe));

		indexWriter.commit();
		indexWriter.close();

		System.out.println("=====删除索引成功=====");
	}

}
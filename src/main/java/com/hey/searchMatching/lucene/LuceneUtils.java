package com.hey.searchMatching.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import com.hankcs.lucene.HanLPAnalyzer;
import com.hankcs.lucene.HanLPIndexAnalyzer;

/**
 * 索引工具类，增删改查
 * 
 * @author Administrator
 *
 */
public class LuceneUtils {

	private static final String FILE_PATH = "lucene_index";

	private static IndexWriter indexWriter = null;

	/**
	 * 获取IndexWriter 同一时间 ，只能打开一个 IndexWriter，独占写锁 。内建线程安全机制。
	 * 
	 * @throws Exception
	 */
	public static IndexWriter getIndexWriter() throws Exception {
		// 创建IdnexWriter
		FSDirectory fs = FSDirectory.open(Paths.get(FILE_PATH));
		// 判断资源是否占用
		if (indexWriter == null || !indexWriter.isLocked(fs)) {
			synchronized (LuceneUtils.class) {
				if (indexWriter == null || !indexWriter.isLocked(fs)) {
					IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new HanLPAnalyzer());
					// 追加模式(APPEND)还是覆盖模式(CREATE)
					// indexWriterConfig.setOpenMode(OpenMode.APPEND);
					indexWriterConfig.setOpenMode(OpenMode.CREATE);
					// 创建writer对象
					indexWriter = new IndexWriter(fs, indexWriterConfig);
				}
			}
		}
		return indexWriter;
	}

	public static DirectoryReader getDirectoryReader() throws Exception {
		FSDirectory fsDirectory = FSDirectory.open(Paths.get(FILE_PATH));
		File file = new File(fsDirectory.getDirectory().toString());
		if (!file.exists()) {
			file.mkdirs();
		}
		return DirectoryReader.open(fsDirectory);
	}

	private static IndexSearcher getIndexSearcher() throws Exception {
		return new IndexSearcher(getDirectoryReader());
	}

	public static IndexReader getIndexReader() throws Exception {
		return getIndexSearcher().getIndexReader();
	}

	private static void coloseIndexWriter(IndexWriter indexWriter) {
		try {
			if (indexWriter != null) {
				indexWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void coloseIndexReader(IndexReader indexReader) {
		try {
			if (indexReader != null) {
				indexReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void coloseDirectoryReader(DirectoryReader directoryReader) {
		try {
			if (directoryReader != null) {
				directoryReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 私有构造方法
	private LuceneUtils() {

	}

	/**
	 * 读取数据库表数据并创建索引
	 * 
	 * @param tableName
	 *            表名
	 * @param coloumName
	 *            字段名
	 * @param isAppend
	 *            追加更新或是覆盖
	 */
	public static void createIndex(String tableName, String coloumName, Boolean isAppend) {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/mydb?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";
			String password = "heyujiao";
			String userName = "root";
			String sql = "select * from " + tableName;

			try (Connection conn = DriverManager.getConnection(url, userName, password);
					PreparedStatement sta = conn.prepareStatement(sql);
					ResultSet rs = sta.executeQuery();) {

				// 4.创建索引输出流
				IndexWriter indexWriter = getIndexWriter();

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

					String province = rs.getString("province");
					if (province != null) {
						// 需要做group分组处理需要用SortedDocValuesField
						// IndexableField provinceField = new StringField("province", province,
						// Store.YES);
						IndexableField provinceField = new SortedDocValuesField("province", new BytesRef(province));
						document.add(provinceField);
						// 同时需要做查询时，也要用StringField
						document.add(new StringField("province", new BytesRef(province.getBytes()), Field.Store.YES));
					}

					// TextField需要做分词处理
					// StringField不需要做分词处理，可用于做精准查询TermQuery
					String name = rs.getString("name");
					if (name != null) {
						// IndexableField sectionNameField = new TextField("name", name, Store.YES);
						IndexableField sectionNameField = new StringField("name", name, Store.YES);
						document.add(sectionNameField);
					}

					indexWriter.addDocument(document);

				}
				indexWriter.commit();
				indexWriter.close();

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
		IndexWriter indexWriter = null;
		try {
			// 创建索引写出器
			indexWriter = getIndexWriter();
			// 创建更新后的文档对象
			Document document = new Document();
			document.add(new StringField("tableName", tableName, Field.Store.YES));
			document.add(new StringField("id", id, Field.Store.YES));
			document.add(new TextField(field, fieldContent, Field.Store.YES));
			// 只能通过 Term 词条更新指定文档
			indexWriter.updateDocument(new Term("id", id), document);
			indexWriter.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			indexWriter.close();
		}
		System.out.println("=====更新索引成功=====");
	}

	public static void query(String coloumName) throws IOException, ParseException {

		IndexSearcher indexSearcher;
		try {
			indexSearcher = getIndexSearcher();
			SortField[] sortField = new SortField[1];
			sortField[0] = new SortField(coloumName, SortField.Type.STRING, true);
			Sort sort = new Sort(sortField);
			// 查询所有结果
			Query query = new MatchAllDocsQuery();
			indexSearcher = getIndexSearcher();
			TopFieldDocs docs = indexSearcher.search(query, 10, sort);
			ScoreDoc[] scores = docs.scoreDocs;
			// 遍历结果
			for (ScoreDoc scoreDoc : scores) {
				System.out.println(indexSearcher.doc(scoreDoc.doc));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// indexReader = getIndexReader();
		//
		// SortedDocValues str =
		// DocValues.getSorted(indexReader.leaves().get(0).reader(), coloumName);
		//
		// for (int i = 0; i < str.getValueCount(); i++) {
		// System.out.println(str.get(i).utf8ToString());
		// }

	}

	/**
	 * 根据字段名称字段值做精准查询
	 * 
	 * @param coloumName
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void queryTermByColoumName(String coloumName, String coloumValue) throws IOException, ParseException {

		DirectoryReader directoryReader;
		IndexSearcher indexSearcher;
		try {
			directoryReader = getDirectoryReader();
			indexSearcher = getIndexSearcher();
			Query query = new TermQuery(new Term(coloumName, coloumValue));
			TopDocs topDocs = indexSearcher.search(query, 10);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {
				int docID = scoreDoc.doc;
				Document document = directoryReader.document(docID);
				System.out.println("tableName: " + document.get("tableName"));
				System.out.println("id: " + document.get("id"));
				System.out.println("name: " + document.get("name"));
				BytesRef ref = document.getBinaryValue("province");
				System.out.println("province: " + new String(ref.bytes));
				System.out.println("Score: " + scoreDoc.score);
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据字段名称分组查询
	 * 
	 * @param coloumName
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void queryGroupByColoumName(String coloumName) throws IOException, ParseException {

		IndexSearcher indexSearcher;
		try {
			indexSearcher = getIndexSearcher();

			// 如果要根据某个field分组，这个field必须为SortedDocValuesField类型
			GroupingSearch groupingSearch = new GroupingSearch(coloumName);

			SortField sortField = new SortField(coloumName, SortField.Type.STRING_VAL);
			Sort sort = new Sort(sortField);
			groupingSearch.setGroupSort(sort);
			groupingSearch.setFillSortFields(true);
			groupingSearch.setCachingInMB(4.0, true);
			groupingSearch.setAllGroups(true);

			Query query = new MatchAllDocsQuery();
			TopGroups<BytesRef> result = groupingSearch.search(indexSearcher, query, 0,
					indexSearcher.getIndexReader().maxDoc());

			GroupDocs<BytesRef>[] docs = result.groups;
			for (GroupDocs<BytesRef> groupDocs : docs) {
				System.out.println(new String(groupDocs.groupValue.bytes));
			}
			int totalGroupCount = result.totalGroupCount;
			System.out.println(totalGroupCount);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据字段名称，查找数据(模糊查询)
	 * 
	 * @param coloumName
	 * @param strName
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void queryByColoumName(String coloumName, String strName) throws IOException, ParseException {

		DirectoryReader directoryReader = null;
		IndexSearcher indexSearcher;
		try {
			directoryReader = getDirectoryReader();
			indexSearcher = getIndexSearcher();

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
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			coloseDirectoryReader(directoryReader);
		}

	}

	/**
	 * 查询所有数据
	 * 
	 * @throws IOException
	 */
	public static void queryAll() throws IOException {
		IndexSearcher indexSearcher;
		IndexReader indexReader = null;
		try {
			indexReader = getIndexReader();
			indexSearcher = getIndexSearcher();
			int count = indexReader.maxDoc();// 所有文档数
			for (int i = 0; i < count; i++) {
				Document doc = indexSearcher.doc(i);
				List<IndexableField> listField = doc.getFields();
				for (int j = 0; j < listField.size(); j++) {
					IndexableField index = listField.get(j);
					System.out.println(index.name() + ": " + index.stringValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			coloseIndexReader(indexReader);
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
		IndexWriter indexWriter = null;
		try {
			indexWriter = getIndexWriter();
			// 通过Term词条删除指定文档
			indexWriter.deleteDocuments(new Term("id", id));
			indexWriter.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			coloseIndexWriter(indexWriter);
		}
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
		IndexWriter indexWriter = null;
		try {
			indexWriter = getIndexWriter();
			// 通过Term词条删除指定文档
			indexWriter.deleteDocuments(new Term("tableName", talbe));
			indexWriter.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			coloseIndexWriter(indexWriter);
		}
		System.out.println("=====删除索引成功=====");
	}

}
package com.hey.searchMatching.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
  
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
  
  /**
 * Created by MoSon on 2017/7/1.
 */
  public class Search {
  
    public static void main(String[] args) throws IOException {
        //定义索引目录
        Path path = FileSystems.getDefault().getPath("index");
        Directory directory = FSDirectory.open(path);
        //定义索引查看器
        IndexReader indexReader = DirectoryReader.open(directory);
        //定义搜索器
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //搜索内容
        //定义查询字段
        Term term = new Term("sellPoint","存");
        Query query = new TermQuery(term);
        //命中前10条文档
        TopDocs topDocs = indexSearcher.search(query,10);
        //打印命中数
        System.out.println("命中数："+topDocs.totalHits);
        //取出文档
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //遍历取出数据
        for (ScoreDoc scoreDoc : scoreDocs){
            //通过indexSearcher的doc方法取出文档
            Document doc = indexSearcher.doc(scoreDoc.doc);
            System.out.println("id:"+doc.get("id"));
            System.out.println("sellPoint:"+doc.get("sellPoint"));
        }
  
        //关闭索引查看器
        indexReader.close();
    }
}
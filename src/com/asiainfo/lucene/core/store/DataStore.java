package com.asiainfo.lucene.core.store;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

public interface DataStore {
	void save(IndexWriter indexWriter, Document document) throws IOException;

	void saveBatch(IndexWriter indexWriter, Document[] documents) throws IOException;

	void saveBean(IndexWriter indexWriter, Object object) throws Exception;

	void saveBatch(IndexWriter indexWriter, Iterable<? extends Iterable<? extends IndexableField>> documents) throws IOException;

	void saveBatchBean(IndexWriter indexWriter, Object[] objects) throws Exception;

	ScoreDoc[] retrieveScoreDoc(IndexSearcher indexSearcher, Query query, int top) throws Exception;

	Document[] retrieveDocument(IndexSearcher indexSearcher, Query query, int top) throws Exception;
//	createQuery
//	criteria 
//	http://www.cnblogs.com/xusir/archive/2013/06/09/3130210.html
}

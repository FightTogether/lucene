package com.asiainfo.lucene.core.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

import com.asiainfo.lucene.common.LuceneDataTypeHelper;

public class DataStoreImpl implements DataStore {

	@Override
	public void save(IndexWriter indexWriter, Document document) throws IOException {
		RecordHelper.save(indexWriter, document);
	}

	@Override
	public void saveBatch(IndexWriter indexWriter, Document[] documents) throws IOException {
		RecordHelper.saveBatch(indexWriter, documents);
	}

	@Override
	public void saveBatch(IndexWriter indexWriter, Iterable<? extends Iterable<? extends IndexableField>> documents) throws IOException {
		RecordHelper.saveBatch(indexWriter, documents);
	}

	@Override
	public void saveBean(IndexWriter indexWriter, Object object) throws Exception {
		if(object instanceof Object[]){
			saveBatchBean(indexWriter, (Object[])object);
		}else{
			Document document=LuceneDataTypeHelper.createDocument(object);
			save(indexWriter, document);
		}
	}
	@Override
	public void saveBatchBean(IndexWriter indexWriter, Object[] objects) throws Exception {
		List<Document> documents=new ArrayList<Document>();
		for(int i=0,n=objects.length;i<n;i++){
			documents.add(LuceneDataTypeHelper.createDocument(objects[i]));
		}
		saveBatch(indexWriter, documents);
	}

	@Override
	public ScoreDoc[] retrieveScoreDoc(IndexSearcher indexSearcher, Query query,int top) throws Exception {
		return indexSearcher.search(query, top).scoreDocs;
	}
	@Override
	public Document[] retrieveDocument(IndexSearcher indexSearcher, Query query,int top) throws Exception {
		ScoreDoc[] scoreDocs= retrieveScoreDoc(indexSearcher, query, top);
		if(scoreDocs==null||scoreDocs.length==0)return null;
		Document[] documents=new Document[scoreDocs.length];
		for(int i=0,n=scoreDocs.length;i<n;i++){
			documents[i]=indexSearcher.doc(scoreDocs[i].doc);
		}
		return documents;
	}
	@Override
	public Object[] retrieve(IndexSearcher indexSearcher, Query query,int top) throws Exception {
		Document[] documents= retrieveDocument(indexSearcher, query, top);
		if(documents==null||documents.length==0)return null;
		Object[] objs=new Object[documents.length];
		for(int i=0,n=documents.length;i<n;i++ ){
			objs[i]=LuceneDataTypeHelper.wrap(documents[i]);			 
		}
		return objs;
	}
	@Override
	public Object[] retrieve(IndexSearcher indexSearcher, Query query) throws Exception {
		return retrieve(indexSearcher, query, Integer.MAX_VALUE);
	}

	@Override
	public void deleteDocuments(IndexWriter indexWriter, Query... queries) throws Exception {
		indexWriter.deleteDocuments(queries);
	}
}

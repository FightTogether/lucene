package com.asiainfo.lucene.test;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;

import com.asiainfo.lucene.bean.MinxBean;
import com.asiainfo.lucene.common.IndexServiceFactory;
import com.asiainfo.lucene.common.LuceneServer;
import com.asiainfo.lucene.common.ServiceManager;



public class LuceneTest {
	public static void main(String[] args) throws Exception {
		LuceneServer.startServer();
		IndexWriter indexWriter=IndexServiceFactory.getDefaultIdxWriter();
		MinxBean obj=new MinxBean();
		obj.setId(1);
		obj.setName("≤‚ ‘∑÷¥ field");
		obj.setCreateTime(new Timestamp(System.currentTimeMillis()));
		obj.setEffectiveDate(new Date());
		obj.setNoAllow("≤ª‘ –Ì");
		ServiceManager.getDataStore().saveBean(indexWriter, obj);
		indexWriter.commit();
		indexWriter.close();
		IndexSearcher indexSearcher=IndexServiceFactory.getDefaultIndexSearcher();
//		ServiceManager.getDataStore().retrieveDocument(indexSearcher, query, top);
	}
}

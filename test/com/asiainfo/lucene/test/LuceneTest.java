package com.asiainfo.lucene.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;

import com.asiainfo.lucene.bean.MinxBean;
import com.asiainfo.lucene.common.CacheFactory;
import com.asiainfo.lucene.common.IndexServiceFactory;
import com.asiainfo.lucene.common.LuceneServer;
import com.asiainfo.lucene.common.ServiceManager;
import com.asiainfo.lucene.core.criteria.BeanCriteria;
import com.asiainfo.lucene.core.criteria.Criteria;
import com.asiainfo.lucene.core.criteria.LucenceExpression;



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
		Criteria criteria=BeanCriteria.forClass(MinxBean.class);
		criteria.addEqual("name", "≤‚ ‘∑÷¥ π˛π˛1");
		Document[] documents=ServiceManager.getDataStore().retrieveDocument(indexSearcher, LucenceExpression.parseQuery(criteria, CacheFactory.getInstance().getAnalyzer()), 100);
		for(Document document:documents ){
			Iterator<IndexableField> iterator=document.iterator();
			while(iterator.hasNext()){
				IndexableField field=iterator.next();
				System.out.println(field.name()+field.stringValue());
			}
		}
	}
}

package com.asiainfo.lucene.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

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
	/*	IndexWriter indexWriter = IndexServiceFactory.getDefaultIdxWriter();
		MinxBean obj1 = new MinxBean();
		obj1.setId(1);
		obj1.setName("ϵͳ�������ֲ�");
		obj1.setCreateTime(new Timestamp(System.currentTimeMillis()));
		obj1.setEffectiveDate(new Date());
		obj1.setNoAllow("������");
		obj1.setDesc("Ӧ��ϵͳ");
		MinxBean obj2 = new MinxBean();
		obj2.setId(1);
		obj2.setName("�߼����������");
		obj2.setCreateTime(new Timestamp(System.currentTimeMillis()));
		obj2.setEffectiveDate(new Date());
		obj2.setNoAllow("������");
		obj2.setDesc("Ӧ�ù���");
		MinxBean obj3 = new MinxBean();
		obj3.setId(1);
		obj3.setName("�߼�Ӧ�������ѧ");
		obj3.setCreateTime(new Timestamp(System.currentTimeMillis()));
		obj3.setEffectiveDate(new Date());
		obj3.setNoAllow("������");
		obj3.setDesc("����ϵͳ");
		ServiceManager.getDataStore().saveBean(indexWriter, new MinxBean[] { obj1, obj2, obj3 });
		indexWriter.commit();
		indexWriter.close();*/
		IndexSearcher indexSearcher = IndexServiceFactory.getDefaultIndexSearcher();
		Criteria criteria = BeanCriteria.forClass(MinxBean.class);
		criteria.addEqual("name", "����");
		Object[] objs = ServiceManager.getDataStore().retrieve(indexSearcher, LucenceExpression.parseQuery(criteria, CacheFactory.getInstance().getAnalyzer()), 100);
		if (objs == null || objs.length == 0) {
			System.out.println("search null ");
			return;
		}
		for (Object obj : objs) {
			System.out.println(((MinxBean) obj).toString());
		}
	/*	IndexWriter indexWriter1 = IndexServiceFactory.getDefaultIdxWriter();
		Term terms1 = new Term("com.asiainfo.lucene.bean.MinxBean.name","�߼�");
		Term terms2 = new Term("com.asiainfo.lucene.bean.MinxBean.desc","Ӧ��");
		ServiceManager.getDataStore().deleteDocuments(indexWriter1, LucenceExpression.parseQuery(criteria, CacheFactory.getInstance().getAnalyzer()));
		indexWriter1.commit();*/
	}
}

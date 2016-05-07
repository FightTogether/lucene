package com.asiainfo.lucene.test;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;

import com.asiainfo.lucene.bean.MinxBean;
import com.asiainfo.lucene.common.LuceneDataTypeHelper;

public class DataTypeTest {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MinxBean obj=new MinxBean();
		obj.setId(1);
		obj.setName("≤‚ ‘∑÷¥ field");
		obj.setCreateTime(new Timestamp(System.currentTimeMillis()));
		obj.setEffectiveDate(new Date());
		obj.setNoAllow("≤ª‘ –Ì");
		Document document=LuceneDataTypeHelper.createDocument(obj);
		Iterator<IndexableField> iterator=document.iterator();
		while(iterator.hasNext()){
			IndexableField field=iterator.next();
			System.out.println(field.name()+field.stringValue());
		}
	}
}

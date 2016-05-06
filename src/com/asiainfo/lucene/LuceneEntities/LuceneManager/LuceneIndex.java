package com.asiainfo.lucene.LuceneEntities.LuceneManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.asiainfo.lucene.LuceneEntities.LuceneManager.LuceneIndexContants.DATE;
import com.asiainfo.lucene.LuceneEntities.LuceneManager.LuceneIndexContants.FieldType;
import com.asiainfo.lucene.LuceneEntities.LuceneManager.LuceneIndexContants.TIMESTAMP;
/*
 * 设计成单例即可
 */
/**
 * 索引创建、删除、更新、添加
 * @author wenghy
 *
 */
public class LuceneIndex {
//	private static final Object INSTANCE_LOCK = new Object();
	private LuceneIndexSettings indexSettings;
	private Directory ramDirectory;
	private static LuceneIndex INSTANCE; 
	/** Used when obtaining a reference to the singleton instance. */ 
	private static Object INSTANCE_LOCK = new Object(); 
	/**
	 * 构造方法
	 */
	private LuceneIndex(){}
	private void init(LuceneIndexSettings luceneIndexSettings){
		this.indexSettings = luceneIndexSettings;
	}
	/** 
	* Get singleton instance. 
	*/ 
	public static LuceneIndex getInstance(LuceneIndexSettings luceneIndexSettings) { 
	synchronized (INSTANCE_LOCK) { 
	if (INSTANCE == null) {
	INSTANCE = new LuceneIndex(); 
	} 
	INSTANCE.init(luceneIndexSettings); 
	return INSTANCE; 
	} 
	}

	public LuceneIndexSettings getIndexSettings() {
		return indexSettings;
	}

	public void setIndexSettings(LuceneIndexSettings indexSettings) {
		this.indexSettings = indexSettings;
	}

	/**
	 * 刷新内存索引
	 */
	public void flushRAMDirectory(){
		synchronized (INSTANCE_LOCK) {
			IndexWriterConfig indexWriterConfig = null;
			IndexWriter indexWriter = null;
			ramDirectory = new RAMDirectory();
			try{
				indexWriterConfig = new IndexWriterConfig(Version.LUCENE_40, this.indexSettings.getAnalyzer());
				indexWriter = new IndexWriter(this.indexSettings.getDirectory(), indexWriterConfig);
				indexWriter.addIndexes(new Directory[]{this.ramDirectory});
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					indexWriter.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
	}
	
	/**
	 * 创建索引
	 * @param obj
	 */
	public void createIndex(Object obj){
			synchronized (INSTANCE_LOCK) {
			
				IndexWriterConfig indexWriterConfig = null;
				IndexWriter indexWriter = null;
				try{
					indexWriterConfig = new IndexWriterConfig(Version.LUCENE_40, this.indexSettings.getAnalyzer());
					indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
					indexWriter = new IndexWriter(this.indexSettings.getDirectory(), indexWriterConfig);
					//调用创建document的方法
					 Document doc = createDocument(obj);
					 indexWriter.addDocument(doc);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try{
						indexWriter.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
	}
	
	
	
	/**
	 * 批量创建索引
	 * @param List
	 */
	public void createIndexALL(List list){
			synchronized (INSTANCE_LOCK) {
				
				IndexWriterConfig indexWriterConfig = null;
				IndexWriter indexWriter = null;
				try{
					indexWriterConfig = new IndexWriterConfig(Version.LUCENE_40, this.indexSettings.getAnalyzer());
					indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
					indexWriter = new IndexWriter(this.indexSettings.getDirectory(), indexWriterConfig);
					//调用创建document的方法
					 List docs = createDocumentAll(list);
					 System.out.println("一共有：:"+docs.size());
					 for(int i=0; i<docs.size(); i++){
						 indexWriter.addDocument((Document)docs.get(i));
					 }
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try{
						indexWriter.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
	}
	
	
	/**
	 * 更新索引：先删除对应的索引，再更新。
	 * @param obj
	 */
	public void updateIndex(Object obj){
		if(delIndex(obj)){
			createIndex(obj);
		}
	}
	
	
	/**
	 * 删除索引
	 * @param p 实体对象
	 * @return isOK删除结果
	 */
	public boolean delIndex(Term term){
		boolean isOK = false;
		synchronized (INSTANCE_LOCK) {
			IndexWriter indexWriter = null;
			IndexWriterConfig indexWriterConfig = null;
			try{
				indexWriterConfig = new IndexWriterConfig(Version.LUCENE_40, this.indexSettings.getAnalyzer());
				indexWriter = new IndexWriter(this.indexSettings.getDirectory(), indexWriterConfig);
				indexWriter.deleteDocuments(term);
				isOK = true;
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					indexWriter.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return isOK;
	}
	/**
	 * 删除当前Object下的索引
	 * 遍历Object的所有对象以确保删除的索引数量最少
	 * @param obj
	 * @return
	 */
//	未验证
	public boolean delIndex(Object obj){
		synchronized (INSTANCE_LOCK) {
			Term minTerm=null;
			int num=Integer.MAX_VALUE;
			try{
				List<java.lang.reflect.Field> fieldList =new ArrayList<java.lang.reflect.Field>();
//				Field[] superFields = clazz.getSuperclass().getDeclaredFields();

				for(Class<?> _clazz = obj.getClass() ;_clazz!= Object.class;_clazz = _clazz.getSuperclass())
					for(java.lang.reflect.Field field: _clazz.getDeclaredFields())
						fieldList.add(field);
				
//				java.lang.reflect.Field[] classFields = obj.getClass().getDeclaredFields(); 
				for (java.lang.reflect.Field field : fieldList) {
					String name = field.getName();
					String getterMethodName="get"+name.substring(0, 1).toUpperCase() + name.substring(1);
					Method m = obj.getClass().getMethod(getterMethodName);
					/**
					 * 由于最终要转换成String,故不必判断类型并转换，直接转成String
					 */
					String value = String.valueOf( m.invoke(obj)); 
					Term term=new Term(name,value);
//					num=getIndexNumber(term);
					int indexNum=getIndexNumber(term);
					if(indexNum>0&&indexNum<num){
						num=indexNum;
						minTerm=term;
						}
					if(num==1)break;
					
				}
				if(minTerm!=null){
					return delIndex(minTerm);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
			}
		}
//		当前没有此索引
		return true;
	}
	/**
	 * 创建document对象
	 * @param Object 实体类
	 * @return document document对象
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public Document createDocument(Object obj) throws Exception{
		Document document = new Document();
		// 获取实体类的所有属性，返回Field数组
		Class<?> clazz=obj.getClass();
		do {
			java.lang.reflect.Field[] classFields = clazz.getDeclaredFields();
			for (java.lang.reflect.Field field : classFields) {
				String name = field.getName();
				String getterMethodName = "get"
						+ name.substring(0, 1).toUpperCase()
						+ name.substring(1);
				String type = field.getGenericType().toString();
				Field luceneField = null;
				String shortTypeName = (type.lastIndexOf(".") > -1 ? type
						.substring(type.lastIndexOf(".") + 1) : type)
						.toUpperCase();
				Method m = clazz.getMethod(getterMethodName);
				switch (FieldType.toFieldType(shortTypeName)) {
				case BYTE: {
					Byte value = (Byte) m.invoke(obj);
					if (value != null) {
						luceneField = new StringField(name,
								String.valueOf(value), Store.YES);
					}
				}
					break;
				case SHORT: {
					Short value = (Short) m.invoke(obj);
					if (value != null) {
						luceneField = new StringField(name,
								String.valueOf(value), Store.YES);
					}
				}
					break;
				case INT: {
					Integer value = (Integer) m.invoke(obj);
					if (value != null) {
						luceneField = new StringField(name,
								String.valueOf(value), Store.YES);
					}
				}
					break;
				case LONG: {
					Long value = (Long) m.invoke(obj);
					if (value != null) {
						luceneField = new StringField(name,
								String.valueOf(value), Store.YES);
					}
				}
					break;
				case FLOAT: {
					Float value = (Float) m.invoke(obj);
					if (value != null) {
						luceneField = new StringField(name,
								String.valueOf(value), Store.YES);
					}
				}
					break;
				case DOUBLE: {
					Double value = (Double) m.invoke(obj);
					if (value != null) {
						luceneField = new StringField(name,
								String.valueOf(value), Store.YES);
					}
				}
					break;
				case CHAR: {
					String value = (String) m.invoke(obj);
					if (value != null) {
						luceneField = new StringField(name, value, Store.YES);
					}
				}
					break;
				case BOOLEAN: {
					Boolean value = (Boolean) m.invoke(obj);
					if (value != null) {
						luceneField = new StringField(name,
								String.valueOf(value), Store.YES);
					}
				}
					break;
				case STRING: {
					String value = (String) m.invoke(obj);
					if (value != null) {
						// 默认对String类型全部分词
						luceneField = new TextField(name, value, Store.YES);
					}
				}
					break;
				case INTEGER: {
					Integer value = (Integer) m.invoke(obj);
					if (value != null) {
						// Integer不必分词
						luceneField = new StringField(name, value.toString(),
								Store.YES);
					}
				}
					break;
				case TIMESTAMP: {
					java.sql.Timestamp value = (java.sql.Timestamp) m
							.invoke(obj);
					if (value != null) {
						String tsStr = "";
						DateFormat sdf = new SimpleDateFormat(
								TIMESTAMP.FORMAT_TEMPLATE);
						tsStr = sdf.format(value);
						luceneField = new StringField(name, tsStr, Store.YES);
					}
				}
					break;
				case DATE: {
					Date value = (Date) m.invoke(obj);
					if (value != null) {
						String tsStr = "";
						DateFormat sdf = new SimpleDateFormat(
								DATE.FORMAT_TEMPLATE);
						tsStr = sdf.format(value);
						luceneField = new StringField(name, tsStr, Store.YES);
					}
				}
					break;
				}
				if (luceneField != null)
					document.add(luceneField);
			}
			clazz = clazz.getSuperclass();
		} while (!clazz.equals(Object.class));
		return document;
	}
	
	/**
	 * 批量创创建document
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	public List createDocumentAll(List list) throws Exception {
		List documents = new ArrayList();
		for (int i = 0, n = list.size(); i < n; i++) {
			Object obj = list.get(i);
			documents.add(createDocument(obj));
		}
		return documents;
	}
	
	/**
	 * 判断索引是否存在
	 * @param Object
	 * @return
	 */
//	此方法待改善
	public boolean exist(Term term){
		boolean isExits = false;
		int count=0;
		count = getIndexNumber(term);
		isExits = count > 0;
		return isExits;
	}
	/**
	 * 遍历Map是否存在该索引
	 * @param map
	 * @return
	 */
	public boolean exist(Map map){
		boolean isExits = false;
		if (map.size() != 1) {
			new Exception("Map 的size必须为1.");
		} else {
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Term term = new Term(key, (String) map.get(key));
				isExits = exist(term);
			}
		}
		return isExits;
	}
	/**
	 * 搜索当前Term下的索引数量
	 * @param term
	 * @return 
	 */
	public int getIndexNumber(Term term){
		int count=0;
		IndexReader indexReader = null;
		try {
			indexReader = DirectoryReader.open(this.indexSettings.getDirectory());
			count = indexReader.docFreq(term);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				indexReader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return count;
	}
}

package com.asiainfo.lucene.LuceneEntities.LuceneManager;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import com.asiainfo.lucene.LuceneEntities.LuceneManager.LuceneIndexContants.BYTE;
import com.asiainfo.lucene.LuceneEntities.LuceneManager.LuceneIndexContants.DATE;
import com.asiainfo.lucene.LuceneEntities.LuceneManager.LuceneIndexContants.FieldType;

public class LuceneResultCollector {
	private LuceneIndexSettings luceneIndexSettings;  //lucene设置类
	
	/**
	 * 初始化方法
	 * @param luceneIndexSettings
	 */
	public LuceneResultCollector(LuceneIndexSettings luceneIndexSettings){
		this.luceneIndexSettings = luceneIndexSettings;
	}
	/**
	 * 转换类型
	 * @param shortTypeName
	 * @param value
	 * @return
	 */
	private Object transType(String shortTypeName,String value)throws Exception{
		Object transVal = null;
		switch(FieldType.toFieldType(shortTypeName)){
		case STRING:{
			if(value==null||value.equals(""))
				return "";
			transVal = String.valueOf(value);
			}break;
		case INTEGER:{
			if(value==null||value.equals(""))
				return null;
			transVal = Integer.valueOf(value);
		}break;
		case DATE:{
			if(value==null||value.equals(""))
				return null;
				Date date = new SimpleDateFormat(DATE.FORMAT_TEMPLATE).parse(value);
				transVal = date;
		}break;
		case TIMESTAMP:{
			if(value==null||value.equals(""))
				return null;
			Timestamp timestamp = Timestamp.valueOf(value);
			transVal = timestamp;
		}break;
		case BOOLEAN:{
			if(value==null||value.equals(""))
				return null;
			transVal = Boolean.valueOf(value);
		}break;
		case BYTE:{
			if(value==null||value.equals(""))
				return null;
			transVal = value.getBytes(BYTE.BYTE_ENCODING);
		}break;
		case SHORT:{
			if(value==null||value.equals(""))
				return null;
			transVal = Short.valueOf(value);
		}break;
		case INT:{
			if(value==null||value.equals(""))
				return null;
			transVal =  Integer.valueOf(value).intValue();
		}break;
		case LONG:{
			if(value==null||value.equals(""))
				return null;
			transVal = Long.valueOf(value);
		}break;
		case FLOAT:{
			if(value==null||value.equals(""))
				return null;
			transVal = Float.valueOf(value);
		}break;
		case DOUBLE:{
			if(value==null||value.equals(""))
				return null;
			transVal = Double.valueOf(value);
		}break;
		case CHAR:{
			if(value==null||value.equals(""))
				return null;
			transVal = value.toCharArray();
		}break;
		}
		return transVal;
	}
	public List collect(ScoreDoc[] result,IndexSearcher indexSearcher,Class clazz) throws Exception{
		List objList = new ArrayList();
		for(int i=0; i<result.length; i++){
				Object object=clazz.newInstance();
				Field[] fields = clazz.getDeclaredFields();  
				for(Field field:fields){
					String name = field.getName();
					String setterMethodName="set"+name.substring(0, 1).toUpperCase() + name.substring(1);
					String type = field.getGenericType().toString(); 
					String shortTypeName=(type.lastIndexOf(".")>-1?type.substring(type.lastIndexOf(".")+1):type).toUpperCase();
					String value=indexSearcher.doc(result[i].doc).get(name);
					Object transValue=transType(shortTypeName,value);
					Class[] parameterTypes = new Class[1];
					parameterTypes[0] = field.getType();
					Method method = clazz.getMethod(setterMethodName, parameterTypes);
					method.invoke(object, transValue);		
				}
				objList.add(object);
			
		}
		return objList;
	}
	public List collect(ScoreDoc[] result,String[] tokenFields,Highlighter highlight,IndexSearcher indexSearcher,Class clazz) throws Exception{
		List objList = new ArrayList();
		StringBuffer strBf=new StringBuffer();
		for(String strTemp:tokenFields)
			strBf.append(strTemp+"--");
		for(int i=0; i<result.length; i++){
				Object object=clazz.newInstance();
				List<Field> fieldList =new ArrayList<Field>();
//				Field[] superFields = clazz.getSuperclass().getDeclaredFields();

				for(Class<?> _clazz = object.getClass() ;_clazz!= Object.class;_clazz = _clazz.getSuperclass())
					for(Field field: _clazz.getDeclaredFields())
						fieldList.add(field);
				Document document=indexSearcher.doc(result[i].doc);
				for(Field field:fieldList){
					String name = field.getName();
					String setterMethodName="set"+name.substring(0, 1).toUpperCase() + name.substring(1);
					String type = field.getGenericType().toString(); 
					String shortTypeName=(type.lastIndexOf(".")>-1?type.substring(type.lastIndexOf(".")+1):type).toUpperCase();
					String value=document.get(name);
					if(strBf.toString().indexOf(name)>-1&&shortTypeName.equals("STRING")){
						value=transHighlight(highlight,name,value);
					}
					Object transValue=transType(shortTypeName,value);
					Class[] parameterTypes = new Class[1];
					parameterTypes[0] = field.getType();
					Method method = clazz.getMethod(setterMethodName, parameterTypes);
					method.invoke(object, transValue);
				}
				objList.add(object);
			
		}
		return objList;
	}
	public String transHighlight(Highlighter highlight,String tokenField,String value) throws IOException, InvalidTokenOffsetsException{
		String strHighlight="";
		 if (value != null) {
             TokenStream tokenStream = this.luceneIndexSettings.getAnalyzer().tokenStream(tokenField, new StringReader(value));    
             strHighlight = highlight.getBestFragment(tokenStream, value);    
         }
	
		return strHighlight; 
	}
}

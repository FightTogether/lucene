package com.asiainfo.lucene.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.ByteDocValuesField;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.index.IndexableField;

import com.asiainfo.lucene.core.field.LuceneFieldType;

public class LuceneDataTypeHelper {
	private static transient Log log = LogFactory.getLog(LuceneDataTypeHelper.class);

	public static Field getLuceneField(String luceneFieldName,Object value,org.apache.lucene.document.FieldType fieldType){
		Field luceneField=null;
		if(value instanceof String){
			luceneField=new Field(luceneFieldName,(String)value, fieldType);
		}else if(value instanceof Long){
			luceneField=new LongField(luceneFieldName,(long) value, fieldType);
		}else if(value instanceof Integer){
			luceneField=new IntField(luceneFieldName,(int) value, fieldType);
		}else if(value instanceof Date){
			luceneField=new Field(luceneFieldName,DateTools.dateToString((Date)value, DateTools.Resolution.MILLISECOND), fieldType);
		}else if(value instanceof Timestamp){
			luceneField=new Field(luceneFieldName,DateTools.dateToString((Timestamp)value, DateTools.Resolution.MILLISECOND), fieldType);
		}else if(value instanceof Double){
			luceneField=new DoubleField(luceneFieldName,(double) value, fieldType);
		}else if(value instanceof Float){
			luceneField=new FloatField(luceneFieldName,(float) value, fieldType);
		}else if(value instanceof Byte){
			luceneField=new ByteDocValuesField(luceneFieldName, (byte)value);
		}else{
			luceneField=new Field(luceneFieldName,value.toString(), fieldType);
		}
		return luceneField;
	}
	
	public static Object transfer(Object value, Class<?> type) {
		if (value == null) return null;
		if (((value instanceof String)) && (value.toString().trim().equals(""))) {
			if (String.class.equals(type)) {
				return value;
			}
			return null;
		}

		if ((type.equals(Short.class)) || (type.equals(Short.TYPE))) {
			if ((value instanceof Short)) {
				return value;
			}
			return new Short(new BigDecimal(value.toString()).shortValue());
		}
		if ((type.equals(Integer.class)) || (type.equals(Integer.TYPE))) {
			if ((value instanceof Integer)) {
				return value;
			}
			return new Integer(new BigDecimal(value.toString()).intValue());
		}
		if ((type.equals(Character.class)) || (type.equals(Character.TYPE))) {
			if ((value instanceof Character)) {
				return value;
			}
			return new Character(value.toString().charAt(0));
		}
		if ((type.equals(Long.class)) || (type.equals(Long.TYPE))) {
			if ((value instanceof Long)) {
				return value;
			}
			return new Long(new BigDecimal(value.toString()).longValue());
		}
		if (type.equals(String.class)) {
			if ((value instanceof String)) {
				return value;
			}
			return value.toString();
		}
		if (type.equals(java.sql.Date.class)) {
			if ((value instanceof java.sql.Date)) return value;
			if ((value instanceof java.util.Date)) {
				return new java.sql.Date(((java.util.Date) value).getTime());
			}
			try {
				return new java.sql.Date(DateTools.stringToTime(value.toString()));
			} catch (Exception e) {
				String msg = String.format("value[%s],transfer %s type error", new String[] { value.toString(), "Date" });
				log.error(msg, e);
				throw new RuntimeException(msg);
			}
		}
		if (type.equals(Time.class)) {
			if ((value instanceof Time)) return value;
			if ((value instanceof java.util.Date)) {
				return new Time(((java.util.Date) value).getTime());
			}
			try {
				return new Time(DateTools.stringToTime(value.toString()));
			} catch (Exception e) {
				String msg = String.format("value[%s],transfer %s type error", new String[] { value.toString(), "Time" });
				log.error(msg, e);
				throw new RuntimeException(msg);
			}
		}
		if (type.equals(Timestamp.class)) {
			if ((value instanceof Timestamp)) return value;
			if ((value instanceof java.util.Date)) {
				return new Timestamp(((java.util.Date) value).getTime());
			}
			try {
				return new Timestamp(DateTools.stringToTime(value.toString()));
			} catch (Exception e) {
				String msg = String.format("value[%s],transfer %s type error", new String[] { value.toString(), "DateTime" });
				log.error(msg, e);
				throw new RuntimeException(msg);
			}
		}
		if ((type.equals(Double.class)) || (type.equals(Double.TYPE))) {
			if ((value instanceof Double)) {
				return value;
			}
			return new Double(new BigDecimal(value.toString()).doubleValue());
		}
		if ((type.equals(Float.class)) || (type.equals(Float.TYPE))) {
			if ((value instanceof Float)) {
				return value;
			}
			return new Float(new BigDecimal(value.toString()).floatValue());
		}
		if ((type.equals(Byte.class)) || (type.equals(Byte.TYPE))) {
			if ((value instanceof Byte)) {
				return value;
			}
			return new Byte(new BigDecimal(value.toString()).byteValue());
		}
		if ((type.equals(Boolean.class)) || (type.equals(Boolean.TYPE))) {
			if ((value instanceof Boolean)) return value;
			if ((value instanceof Number)) {
				if (((Number) value).doubleValue() > 0.0D) {
					return new Boolean(true);
				}
				return new Boolean(false);
			}
			if ((value instanceof String)) {
				if ((((String) value).equalsIgnoreCase("true")) || (((String) value).equalsIgnoreCase("y"))) {
					return new Boolean(true);
				}
				return new Boolean(false);
			}
			String msg = String.format("value[%s],transfer %s type error", new String[] { value.toString(), "Boolean" });
			throw new RuntimeException(msg);
		}

		return value;
	}
	public static String gettingMethodName(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	public static String settingMethodName(String fieldName) {
		return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	public static Document createDocument(Object obj) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Document document = new Document();
		Class<?> clazz = obj.getClass();
		java.lang.reflect.Field[] classFields = clazz.getDeclaredFields();
		String className = clazz.getName();
		for (java.lang.reflect.Field field : classFields) {
			LuceneFieldType fieldTypeAnnotation = field.getAnnotation(LuceneFieldType.class);
			FieldType fieldType=transform(fieldTypeAnnotation);
			if(fieldType==null)continue;
			String fieldName = field.getName();
			String getterMethodName = gettingMethodName(fieldName);
			Method method = clazz.getMethod(getterMethodName);
			Object fieldValue = method.invoke(obj);
			Field luceneField = getLuceneField(className + "." + fieldName, fieldValue, fieldType);
			if (luceneField == null)continue;
			luceneField.setBoost(getBoost(fieldTypeAnnotation));
			document.add(luceneField);

		}
		return document;
	}
	public static float getBoost(LuceneFieldType fieldTypeAnnotation){
		if (fieldTypeAnnotation==null||!fieldTypeAnnotation.effectived()) return 1.0F;
		return fieldTypeAnnotation.boost();
	}
	public static org.apache.lucene.document.FieldType transform(LuceneFieldType fieldTypeAnnotation) {
		if (fieldTypeAnnotation==null||!fieldTypeAnnotation.effectived()) return null;
		org.apache.lucene.document.FieldType fieldType = new org.apache.lucene.document.FieldType();
		fieldType.setStored(fieldTypeAnnotation.stored());
		fieldType.setTokenized(fieldTypeAnnotation.tokenized());
		fieldType.setIndexed(fieldTypeAnnotation.indexed());
		fieldType.setStoreTermVectors(fieldTypeAnnotation.storeTermVectors());
		fieldType.setStoreTermVectorOffsets(fieldTypeAnnotation.storeTermVectorOffsets());
		fieldType.setStoreTermVectorPositions(fieldTypeAnnotation.storeTermVectorPositions());
		fieldType.setStoreTermVectorPayloads(fieldTypeAnnotation.storeTermVectorPayloads());
		fieldType.setOmitNorms(fieldTypeAnnotation.omitNorms());
		fieldType.setNumericPrecisionStep(fieldTypeAnnotation.numericPrecisionStep());
		fieldType.setIndexOptions(fieldTypeAnnotation.indexOptions());
		return fieldType;
	}
	public static Object wrap(Document document) throws Exception{
		if(document==null)return null;
		List<IndexableField> fieldList=document.getFields();
		if(fieldList==null||fieldList.size()==0)return null;
		String firstFieldName=fieldList.get(0).name();
		if(StringUtils.isBlank(firstFieldName)||firstFieldName.indexOf('.')==-1)throw new RuntimeException("field name error");
		Class<?> clazz=Class.forName(firstFieldName.substring(0, firstFieldName.lastIndexOf('.')));
		Object object =clazz.newInstance();
		for(int i=0,n=fieldList.size();i<n;i++){
			IndexableField field=fieldList.get(i);
			String luceneFieldName=field.name();
			String StringValue=field.stringValue();
			String beanFieldName=luceneFieldName.substring(luceneFieldName.lastIndexOf('.')+1);
			java.lang.reflect.Field javaBeanField =clazz.getDeclaredField(beanFieldName);
			Object value=transfer(StringValue, javaBeanField.getType());
			BeanUtils.setProperty(object, beanFieldName, value);
		}
		return object;
	}
}

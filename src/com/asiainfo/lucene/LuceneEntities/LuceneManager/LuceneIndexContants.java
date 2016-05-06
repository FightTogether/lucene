package com.asiainfo.lucene.LuceneEntities.LuceneManager;

/**
 * 关于lucene分词的基本类型
 * @author wenghy
 *
 */
public class LuceneIndexContants {
	/*
	 * STRING类型的属性配置
	 */
	public static class STRING{
		public static final String NULL = "NULL";
		public static final String PACKETS_PATH = "java.lang.Integer";
		public static final String CLASS_TYPE = "class java.lang.String";
	}
	/*
	 * INTEGER类型的属性配置
	 */
	public static class INTEGER{
		public static final String NULL = "NULL";
		public static final String PACKETS_PATH = "java.lang.Integer";
		public static final String CLASS_TYPE = "class java.lang.Integer";
	}
	/*
	 * TIMESTAMP类型的属性配置
	 */
	public static class TIMESTAMP{
		public static final String NULL = "NULL";
		public static final String PACKETS_PATH = "java.sql.Timestamp";
		public static final String CLASS_TYPE = "class java.sql.Timestamp";
		public static final String FORMAT_TEMPLATE="yyyy-MM-dd HH:mm:ss";
	}
	public static class DATE{
		public static final String NULL = "NULL";
//		public static final String PACKETS_PATH = "java.sql.Timestamp";
//		public static final String CLASS_TYPE = "class java.sql.Timestamp";
		public static final String FORMAT_TEMPLATE="yyyy-MM-dd HH:mm:ss";
	}
	public static class BYTE{
		public static final String NULL = "NULL";
//		public static final String PACKETS_PATH = "java.sql.Timestamp";
//		public static final String CLASS_TYPE = "class java.sql.Timestamp";
		public static final String BYTE_ENCODING="ISO-8859-1";
	}
	
	/*
	 * INT基本类型的属性配置
	 */
	public static class INT{
		public static final String NULL = "NULL";
		public static final String PACKETS_PATH = "";
		public static final String CLASS_TYPE = "int";
	}
	public static enum FieldType

	{
		BYTE,SHORT,INT,LONG,FLOAT,DOUBLE,CHAR,BOOLEAN,STRING,INTEGER,TIMESTAMP,DATE,NOTYPE;

		public static FieldType toFieldType(String str)

		{

			try {

				return valueOf(str);

			}

			catch (Exception ex) {

				return NOTYPE;

			}

		}

	}
}

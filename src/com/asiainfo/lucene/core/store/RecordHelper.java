package com.asiainfo.lucene.core.store;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
/**
 * 实现最基本的数据保存
 * @author wenghy
 *
 */
public class RecordHelper {
	private static transient Log log = LogFactory.getLog(RecordHelper.class);
	/** Used when obtaining a reference to the singleton instance. */ 
	private static Object INSTANCE_LOCK = new Object(); 
	public static void save(IndexWriter indexWriter,Document document) throws IOException{
		synchronized (INSTANCE_LOCK) {
			long startTime=System.currentTimeMillis();
			indexWriter.addDocument(document);
			if(log.isInfoEnabled()){
				log.info(MessageFormat.format("add {0} document,time: {1} ms",new Object[]{1,System.currentTimeMillis()-startTime}));
			}
		}
	}
	public static void saveBatch(IndexWriter indexWriter,Document[] documents) throws IOException{
		synchronized (INSTANCE_LOCK) {
			long startTime = System.currentTimeMillis();
			indexWriter.addDocuments(Arrays.asList(documents));
			if (log.isInfoEnabled()) {
				log.info(MessageFormat.format("add {0} document,time: {1} ms",new Object[] { documents.length,System.currentTimeMillis() - startTime }));
			}
		}
	}
	public static void saveBatch(IndexWriter indexWriter,Iterable<? extends Iterable<? extends IndexableField>> documents) throws IOException{
		synchronized (INSTANCE_LOCK) {
			long startTime = System.currentTimeMillis();
			indexWriter.addDocuments(documents);
			if (log.isInfoEnabled()) {
				log.info(MessageFormat.format("add documents,time: {0} ms",new Object[] { System.currentTimeMillis() - startTime }));
			}
		}
	}
}

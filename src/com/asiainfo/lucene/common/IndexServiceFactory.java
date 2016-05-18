package com.asiainfo.lucene.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.asiainfo.lucene.util.locale.LuceneLocaleFactory;

public class IndexServiceFactory {
	private static transient Log log = LogFactory.getLog(IndexServiceFactory.class);
	private IndexServiceFactory() {
	}

	public static IndexWriter getIndexWriter(IndexSettings indexSettings) throws IOException {
		IndexWriterConfig indexWriterConfig=null;
		if(CacheFactory.getInstance().idxWriterConfigCache.containsKey(indexSettings.analyzer)){
			indexWriterConfig=CacheFactory.getInstance().idxWriterConfigCache.get(indexSettings.getAnalyzer());
		}else{
			indexWriterConfig = new IndexWriterConfig(CommConst.LUCENE_VERSION, indexSettings.getAnalyzer());
		}
		return new IndexWriter(indexSettings.directory, indexWriterConfig);
	}
	public static IndexWriter getDefaultIdxWriter(){
		try {
			return getIndexWriter(CacheFactory.getInstance().getDefaultIdxSetting());
		} catch (IOException e) {
			log.error(LuceneLocaleFactory.getResource("getDefaultIdxWriter error"), e);
		}
		return null;
		
	}
	public static IndexReader getDefaultIndexReader() throws IOException{
		return getIndexReader(CacheFactory.getInstance().getDefaultIdxSetting().getDirectory());
	}
	public static IndexReader getIndexReader(Directory directory) throws IOException{
		return DirectoryReader.open(directory);
	}
	public static IndexSearcher getIndexSearcher(IndexReader indexReader){
		return new IndexSearcher(indexReader);
	}
	public static IndexSearcher getDefaultIndexSearcher() throws IOException{
		return getIndexSearcher(getDefaultIndexReader());
	}
	/**
	 * 主要构造分词器以及内存对象。
	 * 
	 */
	public static class IndexSettings {

		private Analyzer analyzer; // 分析器
		private Directory directory; // 目录位置

		/**
		 * 构造方法，初始化分析器。
		 * 
		 * @param analyzer
		 */
		public IndexSettings(Analyzer analyzer,Directory directory) {
			this.analyzer=analyzer;
			this.directory=directory;
			if (log.isDebugEnabled()) log.debug("index settings ...");

		}

		public IndexSettings(Analyzer analyzer, String path) throws IOException {
			if(!CacheFactory.getInstance().directoryCache.containsKey(path)){
			CacheFactory.getInstance().directoryCache.put(path, FSDirectory.open(new File(path)));
			}
			this.analyzer=analyzer;
			this.directory=CacheFactory.getInstance().directoryCache.get(path);
			if (log.isDebugEnabled()) log.debug("index settings and createFSDirectory...");
		}

		public void setAnalyzer(Analyzer analyzer) {
			this.analyzer = analyzer;
		}

		public void setDirectory(Directory directory) {
			this.directory = directory;
		}

		/**
		 * 创建内存目录
		 */
/*		public void createRAMDirectory() throws Exception {
			this.directory = new RAMDirectory();
			indexWriterConfig = new IndexWriterConfig(CommConst.LUCENE_VERSION, this.analyzer);
			IndexWriter indexWriter = new IndexWriter(this.directory, indexWriterConfig);
			indexWriter.close();
			if (log.isDebugEnabled()) log.debug("createRAMDirectory ...");
		}
*/
		/**
		 * 创建磁盘目录
		 * @return 
		 */
	/*	private Directory createFSDirectory(String path) throws Exception {
		
			IndexWriter indexWriter=getIndexWriter(this);
			indexWriter.close();

		}*/

		public Analyzer getAnalyzer() {
			return this.analyzer;
		}

		public Directory getDirectory() {
			return this.directory;
		}

	}
}

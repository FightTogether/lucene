package com.asiainfo.lucene.common;

import java.io.IOException;
import java.util.HashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.asiainfo.lucene.common.IndexServiceFactory.IndexSettings;

public class CacheFactory {
	private static CacheFactory instance;
	private IndexSettings indexSettings=null;
	private String defaultIdxPath;
	public HashMap<String,Directory> directoryCache=new HashMap<String,Directory>();
	public HashMap<Analyzer,IndexWriterConfig> idxWriterConfigCache=new HashMap<Analyzer,IndexWriterConfig>();
	private CacheFactory() {
	}

	public static CacheFactory getInstance() {
		if (instance == null) instance = new CacheFactory();
		return instance;
	}

	private Dictionary dictionary = null;
	private IKAnalyzer analyzer = null;

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	public IKAnalyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(IKAnalyzer analyzer) {
		this.analyzer = analyzer;
	}

	public String getDefaultIdxPath() {
		return defaultIdxPath;
	}
	public IndexSettings getDefaultIdxSetting() throws IOException{
		if(indexSettings==null){
			if(directoryCache.containsKey(defaultIdxPath)){
				indexSettings= new IndexSettings(analyzer,directoryCache.get(defaultIdxPath));
			}else{
				indexSettings= new IndexSettings(analyzer,defaultIdxPath);

			}
		}
		return indexSettings;
	}
	public void setDefaultIdxPath(String defaultIdxPath) {
		this.defaultIdxPath = defaultIdxPath;
	}
	
}

package com.asiainfo.lucene.common;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.asiainfo.lucene.bean.MinxBean;
import com.asiainfo.lucene.common.IndexServiceFactory.IndexSettings;
import com.asiainfo.lucene.common.util.LuceneCommon;

public class LuceneServer {
	private static String strPropertiesName = "lucene.base.properties";
	private static String analyzerClassName = LuceneCommon.getProertiesValue("index.Analyzer", strPropertiesName);
	private static String analyzerSmart = LuceneCommon.getProertiesValue("index.Analyzer.Smart", strPropertiesName);
	private static String path = LuceneCommon.getProertiesValue("index.path", strPropertiesName);
	private static String prefixHTML = LuceneCommon.getProertiesValue("index.prefixHTML", strPropertiesName);
	private static String suffixHTML = LuceneCommon.getProertiesValue("index.suffixHTML", strPropertiesName);
	private static Integer maxLength = Integer.valueOf(LuceneCommon.getProertiesValue("index.maxLength", strPropertiesName));
	private static Collection<String> addWords;
	private static Collection<String> disableWords;

	public static void startServer() throws Exception {
		initDict();
		indexInit();
	}

	private static void initDict() {
		Configuration cfg = DefaultConfig.getInstance();
		Dictionary.initial(cfg);
		CacheFactory.getInstance().setDictionary(Dictionary.getSingleton());
	}

	/*
	 * ��ʼ������
	 */
	private static void indexInit() throws Exception {
		if (addWords != null) CacheFactory.getInstance().getDictionary().addWords(addWords);
		if (disableWords != null) CacheFactory.getInstance().getDictionary().disableWords(disableWords);
		// ����IKAnalyzer���ķִʶ���
		CacheFactory.getInstance().setAnalyzer((IKAnalyzer) Class.forName(analyzerClassName).newInstance());
		// ������ģʽ����ֵ����д�����������ļ���С�ả������
		CacheFactory.getInstance().getAnalyzer().setUseSmart("0".equals(analyzerSmart));
		CacheFactory.getInstance().setDefaultIdxPath(path);
		new IndexSettings(CacheFactory.getInstance().getAnalyzer(), path);

	}

}

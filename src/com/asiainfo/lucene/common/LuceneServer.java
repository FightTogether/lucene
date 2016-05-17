package com.asiainfo.lucene.common;

import java.util.Collection;

import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.asiainfo.lucene.common.IndexServiceFactory.IndexSettings;
import com.asiainfo.lucene.util.CommonUtil;

public class LuceneServer {
	private static String strPropertiesName = "lucene.base.properties";
	private static String analyzerClassName = CommonUtil.getProertiesValue("index.Analyzer", strPropertiesName);
	private static String analyzerSmart = CommonUtil.getProertiesValue("index.Analyzer.Smart", strPropertiesName);
	private static String path = CommonUtil.getProertiesValue("index.path", strPropertiesName);
	private static String prefixHTML = CommonUtil.getProertiesValue("index.prefixHTML", strPropertiesName);
	private static String suffixHTML = CommonUtil.getProertiesValue("index.suffixHTML", strPropertiesName);
	private static Integer maxLength = Integer.valueOf(CommonUtil.getProertiesValue("index.maxLength", strPropertiesName));
	private static Collection<String> addWords;
	private static Collection<String> disableWords;

	public static void startServer() throws Exception {
		initDict();
		indexInit();
	}
	public static void stop() throws Exception {
	}

	private static void initDict() {
		Configuration cfg = DefaultConfig.getInstance();
		Dictionary.initial(cfg);
		CacheFactory.getInstance().setDictionary(Dictionary.getSingleton());
	}

	/*
	 * 初始化方法
	 */
	private static void indexInit() throws Exception {
		if (addWords != null) CacheFactory.getInstance().getDictionary().addWords(addWords);
		if (disableWords != null) CacheFactory.getInstance().getDictionary().disableWords(disableWords);
		// 创建IKAnalyzer中文分词对象
		CacheFactory.getInstance().setAnalyzer((IKAnalyzer) Class.forName(analyzerClassName).newInstance());
		// 非智能模式会出现单字切词情况，索引文件大小会海量增加
		CacheFactory.getInstance().getAnalyzer().setUseSmart("0".equals(analyzerSmart));
		CacheFactory.getInstance().setDefaultIdxPath(path);
		new IndexSettings(CacheFactory.getInstance().getAnalyzer(), path);

	}

}

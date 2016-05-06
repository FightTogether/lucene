package com.asiainfo.lucene.LuceneEntities.LuceneManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.cn.ChineseTokenizer;
import org.apache.lucene.analysis.cn.ChineseTokenizerFactory;
import org.apache.lucene.index.Term;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.asiainfo.lucene.LuceneEntities.properties.LuceneCommon;


/**
 *主要对index进行初始化，对索引进行更新、删除、添加方法。 
 * @author wenghy
 *
 */
public class LuceneIndexManager {
	private LuceneIndexSettings indexSettings;
	private LuceneIndex luceneIndex;
	private String strPropertiesName="lucene.base.properties";
	private String AnalyzerLoadPath=LuceneCommon.getProertiesValue("index.Analyzer", strPropertiesName);
	private String refreshCircle = LuceneCommon.getProertiesValue("index.refreshCircle", strPropertiesName);
	private String path=LuceneCommon.getProertiesValue("index.path", strPropertiesName);
    private String prefixHTML = LuceneCommon.getProertiesValue("index.prefixHTML", strPropertiesName);
    private String suffixHTML =LuceneCommon.getProertiesValue("index.suffixHTML", strPropertiesName);
    private Integer maxLength=Integer.valueOf(LuceneCommon.getProertiesValue("index.maxLength", strPropertiesName));
	private LuceneIndexSearch luceneIndexSearch;
	private Collection<String> addWords;
	private Collection<String> disableWords;
	private Dictionary dictionary=null;

	public LuceneIndexManager() throws Exception {
		indexInit();
	}
	
	/*
	 * 初始化方法
	 */
	private void indexInit()throws Exception {
		IKAnalyzer analyzer=null;
		Configuration cfg=DefaultConfig.getInstance();
		Dictionary.initial(cfg);
		dictionary =Dictionary.getSingleton();
		if(addWords!=null)dictionary.addWords(addWords);
		if(disableWords!=null)dictionary.disableWords(disableWords);
		//创建IKAnalyzer中文分词对象
		analyzer = new IKAnalyzer();
//		非智能模式会出现单字切词情况，索引文件大小会海量增加
		analyzer.setUseSmart(true);
			
		
		
		this.indexSettings = new LuceneIndexSettings(analyzer);
		this.indexSettings.createFSDirectory(path);
		this.luceneIndex = LuceneIndex.getInstance(this.indexSettings);
		this.luceneIndexSearch = new LuceneIndexSearch(indexSettings, new LuceneResultCollector(indexSettings));
		this.luceneIndexSearch.setPrefixHTML(prefixHTML);
		this.luceneIndexSearch.setSuffixHTML(suffixHTML);
	}
	
	/**
	 * 创建索引
	 * @param Object
	 */
	public void create(Object obj){
		this.luceneIndex.createIndex(obj);
	}
	
	/**
	 * 批量创建索引
	 * @param luceneBean
	 */
	public void createALL(List list){
		this.luceneIndex.createIndexALL(list);
		System.out.println("创建索引成功!");
	}
	
	/**
	 *更新索引 
	 * @return
	 */
	public void update(Object obj){
		this.luceneIndex.updateIndex(obj);
	}
	
	/**
	 * 删除索引
	 * @return
	 */
	public void delete(Object obj){
		this.luceneIndex.delIndex(obj);
	}
	
	/**
	 * 判断索引是否存在
	 * @return
	 */
	public boolean exist(Map map){
		return this.luceneIndex.exist(map);
	}
	
	/**
	 * 搜索
	 * @param searchString
	 * @return
	 * @throws Exception
	 */
	public List search(Map conditionMap,Class clazz) throws Exception {
		return this.luceneIndexSearch.serarchFile(conditionMap, clazz, maxLength);
	}
	/**
	 * 搜索
	 * @param searchString
	 * @return
	 * @throws Exception
	 */
	public List search(Map conditionMap,Class clazz,int maxLength) throws Exception {
		return this.luceneIndexSearch.serarchFile(conditionMap, clazz, maxLength);
	}
	public LuceneIndex getLuceneIndex() {
		return luceneIndex;
	}

	public LuceneIndexSearch getLuceneIndexSearch() {
		return luceneIndexSearch;
	}
	  /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public  boolean deleteDir() {
    	File dir=new File(path);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    public  boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    public void setAddWords(Collection<String> addWords) {
		this.addWords = addWords;
	}

	public Collection<String> getAddWords() {
		return addWords;
	}

	public Collection<String> getDisableWords() {
		return disableWords;
	}

	public void setDisableWords(Collection<String> disableWords) {
		this.disableWords = disableWords;
	}

	public int getIndexNumber(String name, String value) {
    	Term term = new Term(name, value);
    	return getLuceneIndex().getIndexNumber(term);
    }

	public String getRefreshCircle() {
		return refreshCircle;
	}

}

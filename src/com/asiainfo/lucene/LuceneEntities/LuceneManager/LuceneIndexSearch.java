package com.asiainfo.lucene.LuceneEntities.LuceneManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.util.Version;

public class LuceneIndexSearch {
	private  IndexSearcher indexSearcher;  //indexSearcher对象
	private LuceneIndexSettings indexSettings;
	private LuceneResultCollector contentCollector;
	 private String prefixHTML = "";
	    private String suffixHTML = "";
	/**
	 * 构造方法对：indexSearcher、indexSettings、contentCollector进行初始化
	 * @param indexSettings
	 * @param contentCollector
	 */
	public LuceneIndexSearch(LuceneIndexSettings indexSettings,LuceneResultCollector contentCollector){
		this.indexSettings = indexSettings;
		this.contentCollector = contentCollector;
		//调用初始化indexSearch对象的方法
		createIndexSearch();
	}
	
	
	public void setPrefixHTML(String prefixHTML) {
		this.prefixHTML = prefixHTML;
	}


	public void setSuffixHTML(String suffixHTML) {
		this.suffixHTML = suffixHTML;
	}


	/**
	 * 初始化indexSearch对象的方法
	 * @throws Exception
	 */
	public void createIndexSearch(){
		try{
			IndexReader indexReader = DirectoryReader.open(this.indexSettings.directory);
			this.indexSearcher = new IndexSearcher(indexReader);
			
			//输出现在的索引
//	        for(int i =0; i<indexReader.numDocs();i++){
//	        	System.out.println(indexReader.document(i));
//	        	System.out.println("文件名称："+indexReader.document(i).get("fileName")+"\t文件描述:"+indexReader.document(i).get("fileDesc")+"\t文件ID："+indexReader.document(i).get("fileId")+"\t创建者："+indexReader.document(i).get("fileCreator"));
//	        }
//	        System.out.println("索引版本:" + indexReader.getCoreCacheKey());
//	    	System.out.println("索引内文档数量："+indexReader.numDocs());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 查询方法
	 * @throws Exception 
	 */
	public List Search(Map conditionMap,LuceneResultCollector luceneResultCollector,Class clazz,int maxlength) throws Exception{
		List result=new ArrayList();
		String[] fields=new String[conditionMap.size()];
		 BooleanClause.Occur[] flags =new Occur[conditionMap.size()];
		String[] stringQuery=new String[conditionMap.size()];
		Object[] objs=conditionMap.keySet().toArray();
		for(int i=0,n=objs.length;i<n;i++){
			String field=(String)objs[i];
			String keyWord=(String)conditionMap.get(field);
			if(field!=null && !field.equals("")&&keyWord!=null &&!keyWord.trim().equals("")){
//				flags[i]=BooleanClause.Occur.SHOULD;
				String queryStr=keyWord.trim();
				System.out.println(queryStr);
				System.out.println(queryStr.lastIndexOf('+', queryStr.length()-1));
				if(queryStr.lastIndexOf('+', queryStr.length()-1)>-1){
					flags[i]=BooleanClause.Occur.MUST;
					queryStr=queryStr.substring(0,queryStr.length()-1);
				}else if(queryStr.lastIndexOf('-', queryStr.length()-1)>-1){
					flags[i]=BooleanClause.Occur.MUST_NOT;
					queryStr=queryStr.substring(0,queryStr.length()-1);
				}else{
					flags[i]=BooleanClause.Occur.SHOULD;
				}
				stringQuery[i]=queryStr;
				fields[i]=field;
			}
		}
		if(stringQuery!=null && stringQuery.length!=0 &&stringQuery[0]!=null&&fields !=null&&fields.length!=0){
			Query query =MultiFieldQueryParser.parse(Version.LUCENE_40,stringQuery, fields,flags, this.indexSettings.getAnalyzer());
	//		QueryParser queryParser=new MultiFieldQueryParser(Version.LUCENE_40, fields, this.indexSettings.getAnalyzer());
			ScoreDoc[] docs = this.indexSearcher.search(query,maxlength).scoreDocs;
			System.out.println("一共有:"+docs.length+"条记录");
			///
			  /**自定义标注高亮文本标签*/  
	        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(prefixHTML,suffixHTML);  
	        /**创建QueryScorer*/  
	        QueryScorer scorer=new QueryScorer(query);
	        /**创建Fragmenter*/  
	        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);  
	        Highlighter highlight=new Highlighter(formatter,scorer);
	        highlight.setTextFragmenter(fragmenter);
	       
			////
	        result= luceneResultCollector.collect(docs,fields,highlight,this.indexSearcher, clazz);
		}
		return result;
	}
	
	/**
	 * 调用查询方法
	 * @param searchString
	 * @return
	 * @throws Exception
	 */
	public List serarchFile(Map conditionMap,Class clazz,int maxLength ) throws Exception{
		return this.Search(conditionMap, this.contentCollector, clazz,maxLength);
	}
}

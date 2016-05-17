package com.asiainfo.lucene.common;

import com.asiainfo.lucene.util.locale.LuceneLocaleFactory;

public class LuceneI18NException extends Exception {
	private static final long serialVersionUID = -1328441759084936458L;

	public LuceneI18NException(String pKey)
		   {
	     super(LuceneLocaleFactory.getResource(pKey));
	  }
		 
	  public LuceneI18NException(String pKey, String pMsg) {
	     super(LuceneLocaleFactory.getResource(pKey) + pMsg);
		  }
		  
		 public LuceneI18NException(String pKey, Object[] pParams) {
		    super(LuceneLocaleFactory.getResource(pKey, pParams));
		   }
	   
		  public LuceneI18NException(String pKey, Throwable e) {
		    super(LuceneLocaleFactory.getResource(pKey), e);
		  }
		  
		   public LuceneI18NException(String pKey, String pMsg, Throwable e) {
		     super(LuceneLocaleFactory.getResource(pKey) + pMsg, e);
		  }
		  
		  public LuceneI18NException(String pKey, Object[] pParams, Throwable e) {
		    super(LuceneLocaleFactory.getResource(pKey, pParams), e);
		   }
}

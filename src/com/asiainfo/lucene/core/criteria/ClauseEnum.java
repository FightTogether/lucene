package com.asiainfo.lucene.core.criteria;

import java.io.Serializable;

import org.apache.lucene.search.BooleanClause;

public class ClauseEnum implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2406615273437587613L;

	private final BooleanClause.Occur occur;

	private ClauseEnum(BooleanClause.Occur occur) {
		this.occur = occur;
	}
	public final BooleanClause.Occur getOccur(){
		return this.occur;
	}
	public final String toString() {
		return this.occur.name();
	}
	public static final ClauseEnum EQUAL = new ClauseEnum(BooleanClause.Occur.MUST);

	public static final ClauseEnum NOT_EQUAL = new ClauseEnum(BooleanClause.Occur.MUST_NOT);
	
	public static final ClauseEnum SHOULD = new ClauseEnum(BooleanClause.Occur.SHOULD);


}

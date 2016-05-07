package com.asiainfo.lucene.core.criteria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;

import com.asiainfo.lucene.common.CommConst;
import com.asiainfo.lucene.core.criteria.Criteria.Criterion;

public class LucenceExpression {
	public LucenceExpression() {
	}

	public static Query parseQuery(Criteria criteria, Analyzer analyzer) throws ParseException {
		if (criteria == null || criteria.isEmpty()) return null;
		ArrayList<String> stringQuery = new ArrayList<String>(criteria.size());
		ArrayList<String> fields = new ArrayList<String>(criteria.size());
		String className=criteria.getClassName();
		ArrayList<BooleanClause.Occur> flags = new ArrayList<BooleanClause.Occur>(criteria.size());
		Iterator<Entry<String, Criterion>> iterator = criteria.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, Criterion> entry=iterator.next();
			Criterion criterion=entry.getValue();
			stringQuery.add(criterion.getValue().toString());
			fields.add(new StringBuffer(className).append('.').append(criterion.getField()).toString());
			flags.add(criterion.getClause().getOccur());
		}
		return MultiFieldQueryParser.parse(CommConst.LUCENE_VERSION, stringQuery.toArray(new String[0]), fields.toArray(new String[0]), flags.toArray(new BooleanClause.Occur[0]), analyzer);
	}
}

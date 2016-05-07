package com.asiainfo.lucene.core.criteria;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.lucene.core.criteria.Criteria.Criterion;

public interface Criteria extends Map<String,Criterion>{

	public HashMap getParameters();

	public void clear();
	
	public String getClassName();
	
	public Criterion getCriterion(String field);

	public Criterion getNewCriterionForEqual(String field, double value);

	public Criterion getNewCriterionForEqual(String field, float value);

	public Criterion getNewCriterionForEqual(String field, int value);

	public Criterion getNewCriterionForEqual(String field, long value);

	public Criterion getNewCriterionForNotEqual(String field, double value);

	public Criterion getNewCriterionForNotEqual(String field, float value);

	public Criterion getNewCriterionForNotEqual(String field, int value);

	public Criterion getNewCriterionForNotEqual(String field, long value);

	public Criterion getNewCriterionForGREATERThan(String field, double value);

	public Criterion getNewCriterionForGREATERThan(String field, float value);

	public Criterion getNewCriterionForGREATERThan(String field, int value);

	public Criterion getNewCriterionForGREATERThan(String field, long value);

	public Criterion getNewCriterionForGREATERThan(String field, Timestamp value);

	public Criterion getNewCriterionForLESSThan(String field, double value);

	public Criterion getNewCriterionForLESSThan(String field, float value);

	public Criterion getNewCriterionForLESSThan(String field, int value);

	public Criterion getNewCriterionForLESSThan(String field, long value);

	public Criterion getNewCriterionForLESSThan(String field, Timestamp value);

	public Criterion getNewCriterionForGREATEREqual(String field, double value);

	public Criterion getNewCriterionForGREATEREqual(String field, float value);

	public Criterion getNewCriterionForGREATEREqual(String field, int value);

	public Criterion getNewCriterionForGREATEREqual(String field, long value);

	public Criterion getNewCriterionForGREATEREqual(String field, Timestamp value);

	public Criterion getNewCriterionForLESSEqual(String field, double value);

	public Criterion getNewCriterionForLESSEqual(String field, float value);

	public Criterion getNewCriterionForLESSEqual(String field, int value);

	public Criterion getNewCriterionForLESSEqual(String field, long value);

	public Criterion getNewCriterionForLESSEqual(String field, Timestamp value);

	public Criterion getNewCriterionForLIKE(String field, String value);

	public Criterion getNewCriterionForNotLIKE(String field, String value);

	public Criterion getNewCriterionForIn(String field, int[] values);

	public Criterion getNewCriterionForIn(String field, long[] values);

	public Criterion getNewCriterionForIn(String field, List values);

	public Criterion getNewCriterionForNotIn(String field, int[] values);

	public Criterion getNewCriterionForNotIn(String field, long[] values);

	public Criterion getNewCriterionForNotIn(String field, List values);

	public Criterion getNewCriterionForIsNull(String field);

	public Criterion getNewCriterionForIsNotNull(String field);

	public Criteria add(String field, Object value,ClauseEnum clauseEnum);

	public Criteria addEqual(String field, double value);

	public Criteria addEqual(String field, float value);

	public Criteria addEqual(String field, int value);

	public Criteria addEqual(String field, String value);

	public Criteria addEqual(String field, String value, boolean isWrapperComma);

	public Criteria addEqual(String field, long value);

	public Criteria addNotEqual(String field, double value);

	public Criteria addNotEqual(String field, float value);

	public Criteria addNotEqual(String field, int value);

	public Criteria addNotEqual(String field, String value);

	public Criteria addNotEqual(String field, String value, boolean isWrapperComma);

	public Criteria addNotEqual(String field, long value);

	public Criteria addGREATERThan(String field, double value);

	public Criteria addGREATERThan(String field, float value);

	public Criteria addGREATERThan(String field, int value);

	public Criteria addGREATERThan(String field, long value);

	public Criteria addGREATERThan(String field, Timestamp value);

	public Criteria addLESSThan(String field, double value);

	public Criteria addLESSThan(String field, float value);

	public Criteria addLESSThan(String field, int value);

	public Criteria addLESSThan(String field, long value);

	public Criteria addLESSThan(String field, Timestamp value);

	public Criteria addGREATEREqual(String field, double value);

	public Criteria addGREATEREqual(String field, float value);

	public Criteria addGREATEREqual(String field, int value);

	public Criteria addGREATEREqual(String field, long value);

	public Criteria addGREATEREqual(String field, Timestamp value);

	public Criteria addLESSEqual(String field, double value);

	public Criteria addLESSEqual(String field, float value);

	public Criteria addLESSEqual(String field, int value);

	public Criteria addLESSEqual(String field, long value);

	public Criteria addLESSEqual(String field, Timestamp value);

	public Criteria addLIKE(String field, String value);

	public Criteria addNotLIKE(String field, String value);

	public Criteria addIn(String field, int[] values);

	public Criteria addIn(String field, long[] values);

	public Criteria addIn(String field, List values);

	public Criteria addNotIn(String field, int[] values);

	public Criteria addNotIn(String field, long[] values);

	public Criteria addNotIn(String field, List values);

	public Criteria addIsNull(String field);

	public Criteria addIsNotNull(String field);

	public Criteria add(Criterion c);

	public Criteria and(Criterion c);

	public Criteria or(Criterion c);
	public final class Criterion implements Serializable {

		private Object value;

		private ClauseEnum occur;

		private String field;

		/*
		 * private boolean ignoreStringCase = false;
		 * 
		 * private List clauses = new ArrayList(); private List conjunctions =
		 * new ArrayList();
		 */

		private Criterion(Object val, ClauseEnum occur) {
			this.value = val;
			this.occur = occur;
		}

	/*	Criterion(String className, String fieldName, Object val, ClauseEnum clauseEnum) {
			this(val, clauseEnum);
			this.className = (className == null ? "" : className);
			this.fieldName = (fieldName == null ? "" : fieldName);
		}*/

		Criterion(String field, Object val, ClauseEnum occur) {
			this(val, occur);
			this.field = field;
		}

		Criterion(String field, Object val) {
			this(field, val, ClauseEnum.EQUAL);
		}

		public String getField() {
			return this.field;
		}
		
		public ClauseEnum getClause() {
			return this.occur;
		}

		public Object getValue() {
			return this.value;
		}

	}

}

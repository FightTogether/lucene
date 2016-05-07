package com.asiainfo.lucene.core.criteria;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BeanCriteria extends HashMap<String, Criteria.Criterion> implements Criteria {

	/**
	 * BeanCriteria serialVersionUID
	 */
	private static final long serialVersionUID = 7591447607591396543L;
	public static final ClauseEnum EQUAL = ClauseEnum.EQUAL;

	public static final ClauseEnum NOT_EQUAL = ClauseEnum.NOT_EQUAL;

	public static final ClauseEnum SHOULD = ClauseEnum.SHOULD;

	private static transient Log log = LogFactory.getLog(BeanCriteria.class);
	private String className;

	@Override
	public HashMap getParameters() {
		return null;
	}

	@Override
	public String getClassName() {
		return this.className;
	}

	public BeanCriteria(String className) {
		this.className = className;
	}

	public static BeanCriteria forClass(Class clazz) {
		return new BeanCriteria(clazz.getName());
	}

	@Override
	public Criterion getCriterion(String field) {
		return (Criterion) super.get(field);

	}

	@Override
	public Criterion getNewCriterionForEqual(String field, double value) {
		return new Criterion(field, new Double(value), EQUAL);
	}

	@Override
	public Criterion getNewCriterionForEqual(String field, float value) {
		return new Criterion(field, new Float(value), EQUAL);
	}

	@Override
	public Criterion getNewCriterionForEqual(String field, int value) {
		return new Criterion(field, new Integer(value), EQUAL);

	}

	@Override
	public Criterion getNewCriterionForEqual(String field, long value) {
		return new Criterion(field, new Long(value), EQUAL);
	}

	@Override
	public Criterion getNewCriterionForNotEqual(String field, double value) {
		return new Criterion(field, new Double(value), NOT_EQUAL);

	}

	@Override
	public Criterion getNewCriterionForNotEqual(String field, float value) {
		return new Criterion(field, new Float(value), NOT_EQUAL);

	}

	@Override
	public Criterion getNewCriterionForNotEqual(String field, int value) {
		return new Criterion(field, new Integer(value), NOT_EQUAL);

	}

	@Override
	public Criterion getNewCriterionForNotEqual(String field, long value) {
		return new Criterion(field, new Long(value), NOT_EQUAL);

	}

	@Override
	public Criterion getNewCriterionForGREATERThan(String field, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForGREATERThan(String field, float value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForGREATERThan(String field, int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForGREATERThan(String field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForGREATERThan(String field, Timestamp value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForLESSThan(String field, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForLESSThan(String field, float value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForLESSThan(String field, int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForLESSThan(String field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForLESSThan(String field, Timestamp value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForGREATEREqual(String field, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForGREATEREqual(String field, float value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForGREATEREqual(String field, int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForGREATEREqual(String field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForGREATEREqual(String field, Timestamp value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForLESSEqual(String field, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForLESSEqual(String field, float value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForLESSEqual(String field, int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForLESSEqual(String field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForLESSEqual(String field, Timestamp value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForLIKE(String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForNotLIKE(String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForIn(String field, int[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForIn(String field, long[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForIn(String field, List values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForNotIn(String field, int[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForNotIn(String field, long[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForNotIn(String field, List values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForIsNull(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criterion getNewCriterionForIsNotNull(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria add(String field, Object value, ClauseEnum clauseEnum) {
		super.put(field, new Criterion(field, value, clauseEnum));
		return this;
	}

	@Override
	public Criteria addEqual(String field, double value) {
		return add(field, new Double(value), EQUAL);
	}

	@Override
	public Criteria addEqual(String field, float value) {
		return add(field, new Float(value), EQUAL);
	}

	@Override
	public Criteria addEqual(String field, int value) {
		return add(field, new Integer(value), EQUAL);
	}

	@Override
	public Criteria addEqual(String field, String value) {
		return add(field, new String(value), EQUAL);
	}

	@Override
	public Criteria addEqual(String field, String value, boolean isWrapperComma) {
		if (isWrapperComma) {
			value = "'" + value + "'";
		}
		return add(field, new String(value), EQUAL);
	}

	@Override
	public Criteria addEqual(String field, long value) {
		return add(field, new Long(value), EQUAL);
	}

	@Override
	public Criteria addNotEqual(String field, double value) {
		return add(field, new Double(value), NOT_EQUAL);
	}

	@Override
	public Criteria addNotEqual(String field, float value) {
		return add(field, new Float(value), NOT_EQUAL);
	}

	@Override
	public Criteria addNotEqual(String field, int value) {
		return add(field, new Integer(value), NOT_EQUAL);
	}

	@Override
	public Criteria addNotEqual(String field, String value) {
		return add(field, new String(value), NOT_EQUAL);
	}

	@Override
	public Criteria addNotEqual(String field, String value, boolean isWrapperComma) {
		if (isWrapperComma) {
			value = "'" + value + "'";
		}
		return add(field, new String(value), NOT_EQUAL);
	}

	@Override
	public Criteria addNotEqual(String field, long value) {
		return add(field, new Long(value), NOT_EQUAL);

	}

	@Override
	public Criteria addGREATERThan(String field, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addGREATERThan(String field, float value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addGREATERThan(String field, int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addGREATERThan(String field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addGREATERThan(String field, Timestamp value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addLESSThan(String field, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addLESSThan(String field, float value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addLESSThan(String field, int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addLESSThan(String field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addLESSThan(String field, Timestamp value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addGREATEREqual(String field, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addGREATEREqual(String field, float value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addGREATEREqual(String field, int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addGREATEREqual(String field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addGREATEREqual(String field, Timestamp value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addLESSEqual(String field, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addLESSEqual(String field, float value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addLESSEqual(String field, int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addLESSEqual(String field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addLESSEqual(String field, Timestamp value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addLIKE(String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addNotLIKE(String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addIn(String field, int[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addIn(String field, long[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addIn(String field, List values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addNotIn(String field, int[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addNotIn(String field, long[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addNotIn(String field, List values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addIsNull(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria addIsNotNull(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria add(Criterion c) {
		Criterion oc = getCriterion(c.getField());

		if (oc == null) {
			add(c);
		} else {
			super.put(c.getField(), c);
		}
		return this;
	}

	@Override
	public Criteria and(Criterion c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria or(Criterion c) {
		// TODO Auto-generated method stub
		return null;
	}

}

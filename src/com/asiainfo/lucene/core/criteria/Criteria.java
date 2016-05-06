package com.asiainfo.lucene.core.criteria;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Criteria extends HashMap {
	private static transient Log log = LogFactory.getLog(Criteria.class);

	public static final SqlEnum EQUAL = SqlEnum.EQUAL;
	public static final SqlEnum NOT_EQUAL = SqlEnum.NOT_EQUAL;
	public static final SqlEnum ALT_NOT_EQUAL = SqlEnum.ALT_NOT_EQUAL;
	public static final SqlEnum GREATER_THAN = SqlEnum.GREATER_THAN;
	public static final SqlEnum LESS_THAN = SqlEnum.LESS_THAN;
	public static final SqlEnum GREATER_EQUAL = SqlEnum.GREATER_EQUAL;
	public static final SqlEnum LESS_EQUAL = SqlEnum.LESS_EQUAL;
	public static final SqlEnum LIKE = SqlEnum.LIKE;
	public static final SqlEnum NOT_LIKE = SqlEnum.NOT_LIKE;
	public static final SqlEnum CUSTOM = SqlEnum.CUSTOM;
	public static final SqlEnum IN = SqlEnum.IN;
	public static final SqlEnum NOT_IN = SqlEnum.NOT_IN;
	public static final SqlEnum ISNULL = SqlEnum.ISNULL;
	public static final SqlEnum ISNotNULL = SqlEnum.ISNOTNULL;
	private static final SqlEnum ASC = SqlEnum.ASC;
	private static final SqlEnum DESC = SqlEnum.DESC;

	private UniqueList selectColumns = new UniqueList();
	private UniqueList orderByColumns = new UniqueList();
	private Hashtable asColumns = new Hashtable(8);

	private HashMap parameters = new HashMap();

	public Criteria() {
	}

	public HashMap getParameters() {
		return this.parameters;
	}

	public String toString() {
		StringBuilder rtn = new StringBuilder();
		UniqueList whereClause = new UniqueList();
		Iterator critKeys = keySet().iterator();
		while (critKeys.hasNext()) {
			String key = (String) critKeys.next();
			Criterion criterion = getCriterion(key);
			whereClause.add(criterion.toString());
		}
		rtn.append(StringUtils.join(whereClause.iterator(), " AND "));

		if (!getOrderByColumns().isEmpty()) {
			Iterator orderKey = getOrderByColumns().iterator();
			rtn.append(" order by ");
			rtn.append(StringUtils.join(orderKey, ","));
		}

		return rtn.toString();
	}

	public void clear() {
		super.clear();
		this.selectColumns.clear();
		this.orderByColumns.clear();
		this.asColumns.clear();
		this.parameters.clear();
	}

	public Criterion getCriterion(String column) {
		return (Criterion) super.get(column);
	}

	public Criterion getNewCriterionForEqual(String column, double value) {
		return new Criterion(column, new Double(value), EQUAL);
	}

	public Criterion getNewCriterionForEqual(String column, float value) {
		return new Criterion(column, new Float(value), EQUAL);
	}

	public Criterion getNewCriterionForEqual(String column, int value) {
		return new Criterion(column, new Integer(value), EQUAL);
	}

	public Criterion getNewCriterionForEqual(String column, long value) {
		return new Criterion(column, new Long(value), EQUAL);
	}

	public Criterion getNewCriterionForNotEqual(String column, double value) {
		return new Criterion(column, new Double(value), NOT_EQUAL);
	}

	public Criterion getNewCriterionForNotEqual(String column, float value) {
		return new Criterion(column, new Float(value), NOT_EQUAL);
	}

	public Criterion getNewCriterionForNotEqual(String column, int value) {
		return new Criterion(column, new Integer(value), NOT_EQUAL);
	}

	public Criterion getNewCriterionForNotEqual(String column, long value) {
		return new Criterion(column, new Long(value), NOT_EQUAL);
	}

	public Criterion getNewCriterionForGREATERThan(String column, double value) {
		return new Criterion(column, new Double(value), GREATER_THAN);
	}

	public Criterion getNewCriterionForGREATERThan(String column, float value) {
		return new Criterion(column, new Float(value), GREATER_THAN);
	}

	public Criterion getNewCriterionForGREATERThan(String column, int value) {
		return new Criterion(column, new Integer(value), GREATER_THAN);
	}

	public Criterion getNewCriterionForGREATERThan(String column, long value) {
		return new Criterion(column, new Long(value), GREATER_THAN);
	}

	public Criterion getNewCriterionForGREATERThan(String column, Timestamp value) {
		return new Criterion(column, value, GREATER_THAN);
	}

	public Criterion getNewCriterionForLESSThan(String column, double value) {
		return new Criterion(column, new Double(value), LESS_THAN);
	}

	public Criterion getNewCriterionForLESSThan(String column, float value) {
		return new Criterion(column, new Float(value), LESS_THAN);
	}

	public Criterion getNewCriterionForLESSThan(String column, int value) {
		return new Criterion(column, new Integer(value), LESS_THAN);
	}

	public Criterion getNewCriterionForLESSThan(String column, long value) {
		return new Criterion(column, new Long(value), LESS_THAN);
	}

	public Criterion getNewCriterionForLESSThan(String column, Timestamp value) {
		return new Criterion(column, value, LESS_THAN);
	}

	public Criterion getNewCriterionForGREATEREqual(String column, double value) {
		return new Criterion(column, new Double(value), GREATER_EQUAL);
	}

	public Criterion getNewCriterionForGREATEREqual(String column, float value) {
		return new Criterion(column, new Float(value), GREATER_EQUAL);
	}

	public Criterion getNewCriterionForGREATEREqual(String column, int value) {
		return new Criterion(column, new Integer(value), GREATER_EQUAL);
	}

	public Criterion getNewCriterionForGREATEREqual(String column, long value) {
		return new Criterion(column, new Long(value), GREATER_EQUAL);
	}

	public Criterion getNewCriterionForGREATEREqual(String column, Timestamp value) {
		return new Criterion(column, value, GREATER_EQUAL);
	}

	public Criterion getNewCriterionForLESSEqual(String column, double value) {
		return new Criterion(column, new Double(value), LESS_EQUAL);
	}

	public Criterion getNewCriterionForLESSEqual(String column, float value) {
		return new Criterion(column, new Float(value), LESS_EQUAL);
	}

	public Criterion getNewCriterionForLESSEqual(String column, int value) {
		return new Criterion(column, new Integer(value), LESS_EQUAL);
	}

	public Criterion getNewCriterionForLESSEqual(String column, long value) {
		return new Criterion(column, new Long(value), LESS_EQUAL);
	}

	public Criterion getNewCriterionForLESSEqual(String column, Timestamp value) {
		return new Criterion(column, value, LESS_EQUAL);
	}

	public Criterion getNewCriterionForLIKE(String column, String value) {
		return new Criterion(column, value, LIKE);
	}

	public Criterion getNewCriterionForNotLIKE(String column, String value) {
		return new Criterion(column, value, NOT_LIKE);
	}

	public Criterion getNewCriterionForIn(String column, int[] values) {
		return new Criterion(column, values, IN);
	}

	public Criterion getNewCriterionForIn(String column, long[] values) {
		return new Criterion(column, values, IN);
	}

	public Criterion getNewCriterionForIn(String column, List values) {
		return new Criterion(column, values, IN);
	}

	public Criterion getNewCriterionForNotIn(String column, int[] values) {
		return new Criterion(column, values, NOT_IN);
	}

	public Criterion getNewCriterionForNotIn(String column, long[] values) {
		return new Criterion(column, values, NOT_IN);
	}

	public Criterion getNewCriterionForNotIn(String column, List values) {
		return new Criterion(column, values, NOT_IN);
	}

	public Criterion getNewCriterionForIsNull(String column) {
		return new Criterion(column, null, ISNULL);
	}

	public Criterion getNewCriterionForIsNotNull(String column) {
		return new Criterion(column, null, ISNotNULL);
	}

	private Criteria add(String column, Object value, SqlEnum comparison) {
		if ((comparison.toString().equals(IN.toString())) || (comparison.toString().equals(NOT_IN.toString())) || (comparison.toString().equals(ISNotNULL.toString()))
				|| (comparison.toString().equals(ISNULL.toString()))) {

			super.put(column, new Criterion(column, value, comparison));
		} else {
			int position = this.parameters.size();
			this.parameters.put(column + "_" + position, value);
			super.put(column + "_" + position, new Criterion(column, ":" + column + "_" + position + " ", comparison));
		}
		return this;
	}

	public Criteria addEqual(String column, double value) {
		add(column, new Double(value), EQUAL);
		return this;
	}

	public Criteria addEqual(String column, float value) {
		add(column, new Float(value), EQUAL);
		return this;
	}

	public Criteria addEqual(String column, int value) {
		add(column, new Integer(value), EQUAL);
		return this;
	}

	public Criteria addEqual(String column, String value) {
		add(column, value, EQUAL);
		return this;
	}

	public Criteria addEqual(String column, String value, boolean isWrapperComma) {
		if (isWrapperComma) {
			value = "'" + value + "'";
		}
		add(column, value, EQUAL);
		return this;
	}

	public Criteria addEqual(String column, long value) {
		add(column, new Long(value), EQUAL);
		return this;
	}

	public Criteria addNotEqual(String column, double value) {
		add(column, new Double(value), NOT_EQUAL);
		return this;
	}

	public Criteria addNotEqual(String column, float value) {
		add(column, new Float(value), NOT_EQUAL);
		return this;
	}

	public Criteria addNotEqual(String column, int value) {
		add(column, new Integer(value), NOT_EQUAL);
		return this;
	}

	public Criteria addNotEqual(String column, String value) {
		add(column, value, NOT_EQUAL);
		return this;
	}

	public Criteria addNotEqual(String column, String value, boolean isWrapperComma) {
		if (isWrapperComma) {
			value = "'" + value + "'";
		}
		add(column, value, NOT_EQUAL);
		return this;
	}

	public Criteria addNotEqual(String column, long value) {
		add(column, new Long(value), NOT_EQUAL);
		return this;
	}

	public Criteria addGREATERThan(String column, double value) {
		add(column, new Double(value), GREATER_THAN);
		return this;
	}

	public Criteria addGREATERThan(String column, float value) {
		add(column, new Float(value), GREATER_THAN);
		return this;
	}

	public Criteria addGREATERThan(String column, int value) {
		add(column, new Integer(value), GREATER_THAN);
		return this;
	}

	public Criteria addGREATERThan(String column, long value) {
		add(column, new Long(value), GREATER_THAN);
		return this;
	}

	public Criteria addGREATERThan(String column, Timestamp value) {
		add(column, value, GREATER_THAN);
		return this;
	}

	public Criteria addLESSThan(String column, double value) {
		add(column, new Double(value), LESS_THAN);
		return this;
	}

	public Criteria addLESSThan(String column, float value) {
		add(column, new Float(value), LESS_THAN);
		return this;
	}

	public Criteria addLESSThan(String column, int value) {
		add(column, new Integer(value), LESS_THAN);
		return this;
	}

	public Criteria addLESSThan(String column, long value) {
		add(column, new Long(value), LESS_THAN);
		return this;
	}

	public Criteria addLESSThan(String column, Timestamp value) {
		add(column, value, LESS_THAN);
		return this;
	}

	public Criteria addGREATEREqual(String column, double value) {
		add(column, new Double(value), GREATER_EQUAL);
		return this;
	}

	public Criteria addGREATEREqual(String column, float value) {
		add(column, new Float(value), GREATER_EQUAL);
		return this;
	}

	public Criteria addGREATEREqual(String column, int value) {
		add(column, new Integer(value), GREATER_EQUAL);
		return this;
	}

	public Criteria addGREATEREqual(String column, long value) {
		add(column, new Long(value), GREATER_EQUAL);
		return this;
	}

	public Criteria addGREATEREqual(String column, Timestamp value) {
		add(column, value, GREATER_EQUAL);
		return this;
	}

	public Criteria addLESSEqual(String column, double value) {
		add(column, new Double(value), LESS_EQUAL);
		return this;
	}

	public Criteria addLESSEqual(String column, float value) {
		add(column, new Float(value), LESS_EQUAL);
		return this;
	}

	public Criteria addLESSEqual(String column, int value) {
		add(column, new Integer(value), LESS_EQUAL);
		return this;
	}

	public Criteria addLESSEqual(String column, long value) {
		add(column, new Long(value), LESS_EQUAL);
		return this;
	}

	public Criteria addLESSEqual(String column, Timestamp value) {
		add(column, value, LESS_EQUAL);
		return this;
	}

	public Criteria addLIKE(String column, String value) {
		add(column, value, LIKE);
		return this;
	}

	public Criteria addNotLIKE(String column, String value) {
		add(column, value, NOT_LIKE);
		return this;
	}

	public Criteria addIn(String column, int[] values) {
		add(column, values, IN);
		return this;
	}

	public Criteria addIn(String column, long[] values) {
		add(column, values, IN);
		return this;
	}

	public Criteria addIn(String column, List values) {
		add(column, values, IN);
		return this;
	}

	public Criteria addNotIn(String column, int[] values) {
		add(column, values, NOT_IN);
		return this;
	}

	public Criteria addNotIn(String column, long[] values) {
		add(column, values, NOT_IN);
		return this;
	}

	public Criteria addNotIn(String column, List values) {
		add(column, values, NOT_IN);
		return this;
	}

	public Criteria addIsNull(String column) {
		add(column, null, ISNULL);
		return this;
	}

	public Criteria addIsNotNull(String column) {
		add(column, null, ISNotNULL);
		return this;
	}

	public Criteria addSelectColumn(String name) {
		this.selectColumns.add(name);
		return this;
	}

	public UniqueList getSelectColumns() {
		return this.selectColumns;
	}

	public Criteria addAscendingOrderByColumn(String name) {
		this.orderByColumns.add(name + ' ' + ASC);
		return this;
	}

	public Criteria addDescendingOrderByColumn(String name) {
		this.orderByColumns.add(name + ' ' + DESC);
		return this;
	}

	public UniqueList getOrderByColumns() {
		return this.orderByColumns;
	}

	public Criteria add(Criterion c) {
		StringBuilder sb = new StringBuilder(c.getTable().length() + c.getColumn().length() + 1);

		sb.append(c.getTable());
		sb.append('.');
		sb.append(c.getColumn());
		super.put(sb.toString(), c);
		return this;
	}

	public Criteria and(Criterion c) {
		Criterion oc = getCriterion(c.getTable() + '.' + c.getColumn());

		if (oc == null) {
			add(c);
		} else {
			oc.and(c);
		}
		return this;
	}

	private Criteria or(Criterion c) {
		Criterion oc = getCriterion(c.getTable() + '.' + c.getColumn());

		if (oc == null) {
			add(c);
		} else {
			oc.or(c);
		}
		return this;
	}

	public final class Criterion implements Serializable {
		public static final String AND = " AND ";

		public static final String OR = " OR ";

		private Object value;

		private SqlEnum comparison;

		private String table;
		private String column;
		private boolean ignoreStringCase = false;

		private List clauses = new ArrayList();
		private List conjunctions = new ArrayList();

		private Criterion(Object val, SqlEnum comp) {
			this.value = val;
			this.comparison = comp;
		}

		Criterion(String table, String column, Object val, SqlEnum comp) {
			this(val, comp);
			this.table = (table == null ? "" : table);
			this.column = (column == null ? "" : column);
		}

		Criterion(String tableColumn, Object val, SqlEnum comp) {
			this(val, comp);
			int dot = tableColumn.indexOf('.');
			if (dot == -1) {
				this.table = "";
				this.column = tableColumn;
			} else {
				this.table = tableColumn.substring(0, dot);
				this.column = tableColumn.substring(dot + 1);
			}
		}

		Criterion(String tableColumn, Object val) {
			this(tableColumn, val, Criteria.EQUAL);
		}

		public String getColumn() {
			return this.column;
		}

		public void setTable(String name) {
			this.table = name;
		}

		public String getTable() {
			return this.table;
		}

		public SqlEnum getComparison() {
			return this.comparison;
		}

		public Object getValue() {
			return this.value;
		}

		public Criterion setIgnoreCase(boolean b) {
			this.ignoreStringCase = b;
			return this;
		}

		public boolean isIgnoreCase() {
			return this.ignoreStringCase;
		}

		private List getClauses() {
			return this.clauses;
		}

		private List getConjunctions() {
			return this.conjunctions;
		}

		public Criterion and(Criterion criterion) {
			Criterion c = new Criterion(Criteria.this, criterion.getColumn(), criterion.getValue(), criterion.getComparison());
			this.clauses.add(c);
			this.conjunctions.add(" AND ");
			return this;
		}

		public Criterion or(Criterion criterion) {
			this.clauses.add(criterion);
			this.conjunctions.add(" OR ");
			return this;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			appendTo(sb);
			return sb.toString();
		}

		public void appendTo(StringBuilder sb) {
			if (this.column == null) {
				return;
			}

			Criterion clause = null;
			for (int j = 0; j < this.clauses.size(); j++) {
				sb.append('(');
			}
			if (Criteria.CUSTOM == this.comparison) {
				if ((this.value != null) && (!"".equals(this.value))) {
					sb.append((String) this.value);
				}
			} else {
				String field = null;
				if (this.table == null) {
					field = this.column;
				} else {
					field = this.table.length() + 1 + this.column.length() + this.table + '.' + this.column;
				}

				SqlExpression.builder(this, sb);
			}

			for (int i = 0; i < this.clauses.size(); i++) {
				sb.append(this.conjunctions.get(i));
				clause = (Criterion) this.clauses.get(i);
				clause.appendTo(sb);
				sb.append(')');
			}
		}

		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if ((obj == null) || (!(obj instanceof Criterion))) {
				return false;
			}

			Criterion crit = (Criterion) obj;

			boolean isEquiv = ((this.table == null) && (crit.getTable() == null))
					|| ((this.table != null) && (this.table.equals(crit.getTable())) && (this.column.equals(crit.getColumn())) && (this.comparison.equals(crit.getComparison())));

			if (isEquiv) {
				Object b = crit.getValue();
				if (((this.value instanceof Object[])) && ((b instanceof Object[]))) {
					isEquiv &= Arrays.equals((Object[]) this.value, (Object[]) b);
				} else if (((this.value instanceof int[])) && ((b instanceof int[]))) {
					isEquiv &= Arrays.equals((int[]) this.value, (int[]) b);
				} else {
					isEquiv &= this.value.equals(b);
				}
			}

			isEquiv &= this.clauses.size() == crit.getClauses().size();
			for (int i = 0; i < this.clauses.size(); i++) {
				isEquiv &= ((String) this.conjunctions.get(i)).equals((String) crit.getConjunctions().get(i));

				isEquiv &= ((Criterion) this.clauses.get(i)).equals((Criterion) crit.getClauses().get(i));
			}

			return isEquiv;
		}

		public int hashCode() {
			int h = this.value.hashCode() ^ this.comparison.hashCode();

			if (this.table != null) {
				h ^= this.table.hashCode();
			}

			if (this.column != null) {
				h ^= this.column.hashCode();
			}

			for (int i = 0; i < this.clauses.size(); i++) {
				h ^= ((Criterion) this.clauses.get(i)).hashCode();
			}

			return h;
		}

		public List getAllTables() {
			UniqueList tables = new UniqueList();
			addCriterionTable(this, tables);
			return tables;
		}

		private void addCriterionTable(Criterion c, UniqueList s) {
			if (c != null) {
				s.add(c.getTable());
				for (int i = 0; i < c.getClauses().size(); i++) {
					addCriterionTable((Criterion) c.getClauses().get(i), s);
				}
			}
		}

		public Criterion[] getAttachedCriterion() {
			ArrayList crits = new ArrayList();
			traverseCriterion(this, crits);
			Criterion[] crita = new Criterion[crits.size()];
			for (int i = 0; i < crits.size(); i++) {
				crita[i] = ((Criterion) crits.get(i));
			}

			return crita;
		}

		private void traverseCriterion(Criterion c, ArrayList a) {
			if (c != null) {
				a.add(c);
				for (int i = 0; i < c.getClauses().size(); i++) {
					traverseCriterion((Criterion) c.getClauses().get(i), a);
				}
			}
		}
	}
}

package com.asiainfo.lucene.core.criteria;

import java.io.Serializable;

class SqlEnum implements Serializable {
	private final String s;

	private SqlEnum(String s) {
		this.s = s;
	}

	public final String toString() {
		return this.s;
	}

	public static final SqlEnum EQUAL = new SqlEnum("=");

	public static final SqlEnum NOT_EQUAL = new SqlEnum("!=");

	public static final SqlEnum ALT_NOT_EQUAL = new SqlEnum("!=");

	public static final SqlEnum GREATER_THAN = new SqlEnum(">");

	public static final SqlEnum LESS_THAN = new SqlEnum("<");

	public static final SqlEnum GREATER_EQUAL = new SqlEnum(">=");

	public static final SqlEnum LESS_EQUAL = new SqlEnum("<=");

	public static final SqlEnum LIKE = new SqlEnum(" LIKE ");

	public static final SqlEnum NOT_LIKE = new SqlEnum(" NOT LIKE ");

	public static final SqlEnum IN = new SqlEnum(" IN ");

	public static final SqlEnum NOT_IN = new SqlEnum(" NOT IN ");

	public static final SqlEnum CUSTOM = new SqlEnum("CUSTOM");

	public static final SqlEnum JOIN = new SqlEnum("JOIN");

	public static final SqlEnum ASC = new SqlEnum("ASC");

	public static final SqlEnum DESC = new SqlEnum("DESC");

	public static final SqlEnum ISNULL = new SqlEnum(" IS NULL ");

	public static final SqlEnum ISNOTNULL = new SqlEnum(" IS NOT NULL ");
}

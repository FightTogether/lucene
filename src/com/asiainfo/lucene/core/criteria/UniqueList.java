package com.asiainfo.lucene.core.criteria;

import java.util.ArrayList;

public class UniqueList<Criteria> extends ArrayList<Criteria> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1814290563346502890L;

	public UniqueList() {
	}

	public boolean add(Criteria o) {
		if ((o != null) && (!contains(o))) {
			return super.add(o);
		}
		return false;
	}
}
package com.asiainfo.lucene.core.criteria;

import java.util.ArrayList;

public class UniqueList extends ArrayList {
	public UniqueList() {
	}

	public boolean add(Object o) {
		if ((o != null) && (!contains(o))) {
			return super.add(o);
		}
		return false;
	}
}
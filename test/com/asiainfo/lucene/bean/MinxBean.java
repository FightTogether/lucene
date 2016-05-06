package com.asiainfo.lucene.bean;

import java.sql.Timestamp;
import java.util.Date;

import com.asiainfo.lucene.core.field.LuceneFieldType;

public class MinxBean {
	@LuceneFieldType(effectived=false)
	private long id;
	@LuceneFieldType(indexed=true)
	private String name;
	@LuceneFieldType(indexed=true,stored=true)
	private Timestamp createTime;
	@LuceneFieldType(indexed=true)
	private Date effectiveDate;
	private String noAllow;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getNoAllow() {
		return noAllow;
	}
	public void setNoAllow(String noAllow) {
		this.noAllow = noAllow;
	}
	
}

package com.asiainfo.lucene.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.asiainfo.lucene.core.field.LuceneFieldType;

public class SearchIndex implements Serializable {

	/** 
	    *  
	    */
	private static final long serialVersionUID = 153648837940506749L;

	// �������
	@LuceneFieldType(stored = true)
	private String id;

	// ��ԴID
	@LuceneFieldType(stored = true)
	private String resourceId;

	// ����
	@LuceneFieldType(stored = true)
	private String title;

	// ��������˵��
	@LuceneFieldType(stored = true)
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}

package com.asiainfo.lucene.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.lucene.core.store.DataStore;

public class ServiceManager {
	private static transient Log log = LogFactory.getLog(ServiceManager.class);
	private static DataStore m_dataStore=null;
	static{
		initial();
	}
	private static void initial() {
		//�Ժ�ĳɿ�����,��ʱ��ôЩ����ȻЧ�ʵ���
		try {
			m_dataStore=(DataStore)Class.forName("com.asiainfo.lucene.core.store.DataStoreImpl").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.error("init DataStore error",e);
		}
	}
	private ServiceManager(){}
	public static DataStore getDataStore(){
		return m_dataStore;
	}
}

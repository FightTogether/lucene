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
		//以后改成可配置,暂时这么些，虽然效率低下
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

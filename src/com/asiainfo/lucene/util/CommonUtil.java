package com.asiainfo.lucene.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonUtil {
	public static String getProertiesValue(String strKey, String strPropertiesName) {
		String strValue = "";
		try {
			InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream(strPropertiesName);
			Properties p = new Properties();
			p.load(in);
			in.close();
			strValue = p.getProperty(strKey);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strValue;
	}
}

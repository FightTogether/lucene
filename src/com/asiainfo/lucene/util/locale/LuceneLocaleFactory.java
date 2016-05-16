package com.asiainfo.lucene.util.locale;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LuceneLocaleFactory {
	private static transient Log log = LogFactory.getLog(LuceneLocaleFactory.class);

	private static final String DEFAULT_RESOURCE_BUNDLE_NAME = "i18n.lucene_resource";
	private static boolean IS_FIXED_LOCALE = true;
	private static Locale DEFAULT_LOCALE = Locale.CHINA;
	private static ICustomLocaleResource CUSTOM_LOCALE_INSTANCE = null;

	private static final HashMap LOCALE_RESOURCE_MAPPING = new HashMap();

	public static String getResource(String key) {
		return getResource(DEFAULT_RESOURCE_BUNDLE_NAME, key);
	}

	public static Locale getCurrentLocale() {
		// 暂时固定为中文
		return Locale.CHINA;
	}

	public static String getResource(String res, String key) {
		Locale locale = getCurrentLocale();

		if (CUSTOM_LOCALE_INSTANCE != null && CUSTOM_LOCALE_INSTANCE.getResourceName().equalsIgnoreCase(res)) {
			return CUSTOM_LOCALE_INSTANCE.getResource(locale, key);
		}

		Long hashCode = new Long(locale.hashCode() * res.hashCode());

		HashMap resource = (HashMap) LOCALE_RESOURCE_MAPPING.get(hashCode);
		if (resource == null) {
			synchronized (LOCALE_RESOURCE_MAPPING) {
				if (!LOCALE_RESOURCE_MAPPING.containsKey(hashCode)) {
					String fileName = StringUtils.replace(res, ".", "/") + "_" + locale.toString() + ".properties";
					InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
					if (is == null) {
						throw new RuntimeException("File " + fileName + " is not exist!!");
					}
					try {
						Properties p = new Properties();
						p.load(is);
						is.close();

						HashMap tmp = new HashMap();
						Set set = p.keySet();
						for (Iterator iter = set.iterator(); iter.hasNext();) {
							String item = (String) iter.next();
							tmp.put(item, p.getProperty(item));
						}

						p.clear();
						p = null;

						LOCALE_RESOURCE_MAPPING.put(hashCode, tmp);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
				resource = (HashMap) LOCALE_RESOURCE_MAPPING.get(hashCode);
			}
		}

		String rtn = (String) resource.get(key);
		if (rtn == null) {
			rtn = key;
			String fileName = DEFAULT_RESOURCE_BUNDLE_NAME+"_" + locale.toString();
			log.error("Not found resource " + key + " from " + fileName);
		}
		return rtn;
	}
	public static void main(String[] args) {
		LuceneLocaleFactory.getResource("a");
	}
}

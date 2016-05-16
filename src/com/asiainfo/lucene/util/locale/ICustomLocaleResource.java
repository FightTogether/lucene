package com.asiainfo.lucene.util.locale;

import java.util.Locale;

public interface ICustomLocaleResource {
	public abstract String getResourceName();

	public abstract String getResource(Locale paramLocale, String paramString, Object[] paramArrayOfObject);

	public abstract String getResource(Locale paramLocale, String paramString);
}

package com.asiainfo.lucene.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



/**
 * Created on Jan 17, 2013
 * <p>Description: [工具类]</p>
*/
public class LuceneCommon
{
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(LuceneCommon.class);

	public static String DATE_FORMAT = "yyyy-MM-dd";

	public static String DATE_SLASH_FORMAT = "yyyy/MM/dd";

	public static String dateToString(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * @author wenghy
	 * <p>Discription:[String转Date]</p>
	 * @param inputDate
	 * @return
	 */
	public static Date stringToDate(String inputDate)
	{
		if (StringUtils.isEmpty(inputDate))
		{
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date date = null;
		try
		{
			date = sdf.parse(inputDate, new ParsePosition(0));
		}
		catch (Exception ex)
		{
			SimpleDateFormat sdfs = new SimpleDateFormat(DATE_SLASH_FORMAT);
			date = sdfs.parse(inputDate, new ParsePosition(0));
			return date;
		}
		return date;
	}

	/**
	 * @author wenghy
	 * <p>Discription:[转换成yyyyMMdd]</p>
	 * @param strData
	 * @return
	 */
	public static String cvsDataFormat(String strData)
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try
		{
			date = sdf1.parse(strData);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return sdf2.format(date);
	}

	public static Date getNowDateShort(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		String dateString = formatter.format(date);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}


	

	/**
	 * <p>Discription:[获取chart参数]</p>
	 * @param strKey
	 * @return
	 */
	public static String getProertiesValue(String strKey,String strPropertiesName)
	{
		String strValue = "";
		try
		{
			InputStream in = LuceneCommon.class.getClassLoader().getResourceAsStream(strPropertiesName);
			Properties p = new Properties();
			p.load(in);
			in.close();
			strValue = p.getProperty(strKey);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return strValue;
	}
	
	public static List<String> getMonthList()
	{
		List<String> listMonth = new ArrayList<String>();
		listMonth.add("1月");
		listMonth.add("2月");
		listMonth.add("3月");
		listMonth.add("4月");
		listMonth.add("5月");
		listMonth.add("6月");
		listMonth.add("7月");
		listMonth.add("8月");
		listMonth.add("9月");
		listMonth.add("10月");
		listMonth.add("11月");
		listMonth.add("12月");
		return listMonth;
	}
	

	
	/**
	 * @author wenghy
	 * <p>Discription:[获取系统当前时间]</p>
	 * @return
	 */
	public static String getCurrentTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strCurrentTime = sdf.format(new java.util.Date());
		return strCurrentTime;
	}
	

}
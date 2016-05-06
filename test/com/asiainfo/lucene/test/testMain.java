package com.asiainfo.lucene.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class testMain {
//	final static String url = "http://localhost:9996/sendCase";
//	final static String url = "http://localhost:9997/sendTask";
	final static String url = "http://localhost:9999/sendDataset?datasetId=";
//	final static String params = "{\"name\":\"baidu.����ճ�\",\"arguments\":{\"database\":\"odbc\"},\"steps\":[\"baidu.�ٶ�һ��.�����������ҳ��\",\"baidu.�ٶȸ�������.�����ճ���ͼ\",\"baidu.�ٶȸ�������.�½��ճ�\"]}";
	final static String params = "{\"testcase\":\"baidu.����ճ�\",\"arguments\":{\"database\":\"odbc\"},\"steps\":[{\"arguments\":{},\"component\":\"baidu.�ٶ�һ��.�����������ҳ��\"},{\"arguments\":{\"account\": {\"value\":\"yynlts1\", \"expect\":\"yynlts1\"},\"password\": {\"value\":\"password1\", \"expect\":\"password1\"}},\"component\":\"baidu.�ٶȸ�������.�����ճ���ͼ\"}]}";
	public static void main(String[] args) {
		// String queryStr="sasqas+";
		// System.out.println(queryStr.lastIndexOf('+', queryStr.length()-1)>1);
		// System.out.println(queryStr.substring(0, queryStr.length()-1));
		post(url, params);
	}

	public static void post(String strURL, String params) {
		try {
			URL url = new URL(strURL+URLEncoder.encode("ART20150121150247952_baidu.����ճ�", "utf-8"));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST");
//			connection.setRequestProperty("taskId", "3");
//			connection.setRequestProperty("testcaseId", "�׿����ϲ�¼ESB�ӿڣ����ͱ��Ĺؼ��ֶ�_����:10176291,�����ļ�·��:��ȷ,�ļ��������棩:��ȷ,�ļ��������棩:��ȷ");
			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(params.getBytes());
			out.flush();
			while (connection.getContentLength() != -1) {
				if (connection.getResponseCode() == 200) {
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					String temp = "";
					while ((temp = reader.readLine()) != null) {
						System.err.println("server response:" + temp);// ��ӡ�յ�����Ϣ
					}
					reader.close();
					in.close();
					connection.disconnect();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

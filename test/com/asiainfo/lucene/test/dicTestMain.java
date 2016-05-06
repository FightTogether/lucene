package com.asiainfo.lucene.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class dicTestMain {
	public static void main(String[] args) throws Exception {
		File f=new File("C:\\Users\\wenghy\\Desktop\\singleDic - 副本.txt");
		InputStream in=new FileInputStream(f);
		Reader reader= new InputStreamReader(in);
		int tempchar;
		 while ((tempchar = reader.read()) != -1) {
             // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
             // 但如果这两个字符分开显示时，会换两次行。
             // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
             if (((char) tempchar) != '\r'&&((char) tempchar) != '\n') {
                 System.out.print((char) tempchar);
                 System.out.println();
             }
         }
         reader.close();
	}
}

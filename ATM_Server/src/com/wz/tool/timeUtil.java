package com.wz.tool;
import java.text.DateFormat;
import java.util.Date;

//�����࣬��Ҫ��ȡ��ʱ���йصĲ���
public class timeUtil {
	public static String getTime()
	{
		Date date=new Date();
		DateFormat format=DateFormat.getDateTimeInstance();
		return format.format(date);
	}

}

package com.soloman.org.cn.utis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dates
{

	/**
	 *  将字符串转为时间戳
	 * @param user_time
	 * @return
	 */

	public static String getTime(String user_time)
	{
		String re_time = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");

		Date d;

		try
		{

			d = sdf.parse(user_time);

			long l = d.getTime();

			String str = String.valueOf(l);

			re_time = str.substring(0, 10);

		} catch (ParseException e)
		{

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return re_time;

	}
}

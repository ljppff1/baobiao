package com.soloman.org.cn.utis;
/**
 * 防止连续点击
 * @author Mac
 *
 */
public class FastClick
{
	
		private static long lastClickTime;

		public synchronized static boolean isFastClick()
		{
			long time = System.currentTimeMillis();
			if (time - lastClickTime < 500)
			{
				return true;
			}
			lastClickTime = time;
			return false;
		}
}

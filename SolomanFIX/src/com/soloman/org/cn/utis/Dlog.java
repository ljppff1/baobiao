package com.soloman.org.cn.utis;


import android.util.Log;

/**
 * 
 * log工具类，调试使用log输出
 * 
 **/
public class Dlog
{

    private static boolean isDebug = true;

    private static String mytag = "huashanglog";

    /** 在发布下，调用这个方法停止打log */
    public static void isDebug(boolean state)
    {
        isDebug = state;
    }

    public static void i(String tag, String msg)
    {
        if (isDebug)
        {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg)
    {
        if (isDebug)
        {
            Log.i(mytag, msg);
        }
    }

}

package com.soloman.org.cn.listener;

import android.support.v4.app.Fragment;




public interface IFragmentSwitcher
{

    public void switchToFrag(Fragment frag);

    /** 切换相机时�?报错，调整方�?*/
    public void swithToFrag(Fragment frag, int type);

    /** 设置fragment的tag方法 **/
    public void swithToFrag(Fragment frag, int type, String tag);

    public static IFragmentSwitcher Null = new IFragmentSwitcher()
    {

        @Override
        public void switchToFrag(Fragment frag)
        {

        }

        @Override
        public void swithToFrag(Fragment frag, int type)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void swithToFrag(Fragment frag, int type, String tag)
        {
            // TODO Auto-generated method stub

        }
    };
}

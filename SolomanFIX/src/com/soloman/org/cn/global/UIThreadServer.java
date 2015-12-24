package com.soloman.org.cn.global;

public interface UIThreadServer
{
    public void post(Runnable runnable);

    public void postDelayed(final Runnable runnable, long timeInMills);

    UIThreadServer NULL = new UIThreadServer()
    {

        @Override
        public void postDelayed(Runnable runnable, long timeInMills)
        {

        }

        @Override
        public void post(Runnable runnable)
        {

        }
    };

}

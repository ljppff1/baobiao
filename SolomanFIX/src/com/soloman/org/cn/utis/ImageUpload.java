package com.soloman.org.cn.utis;

public class ImageUpload
{
	static ImageUpload instance = null;

	public static ImageUpload getInstance()
	{
		if (instance == null)
		{
			instance = new ImageUpload();
		}
		return instance;
	}
}

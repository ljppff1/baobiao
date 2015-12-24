package com.soloman.org.cn.utis;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.duowan.mobile.netroid.DefaultRetryPolicy;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.Network;
import com.duowan.mobile.netroid.RequestQueue;
import com.duowan.mobile.netroid.cache.BitmapImageCache;
import com.duowan.mobile.netroid.cache.DiskCache;
import com.duowan.mobile.netroid.image.NetworkImageView;
import com.duowan.mobile.netroid.request.ImageRequest;
import com.duowan.mobile.netroid.request.JsonObjectRequest;
import com.duowan.mobile.netroid.stack.HurlStack;
import com.duowan.mobile.netroid.toolbox.BasicNetwork;
import com.duowan.mobile.netroid.toolbox.FileDownloader;
import com.duowan.mobile.netroid.toolbox.ImageLoader;
import com.soloman.org.cn.R;
import com.soloman.org.cn.ui.ActStartAnimation;

import android.content.Context;
import android.widget.ImageView;

/**
 * 网络请求/图片加载/缓存设置 管理类
 * 
 * @author DELLLL
 * 
 */

public class Netroid
{
	private static PreferenceUtils preferences;
//	 String url = "http://api.soloman.org.cn/API/v1/";
	String url = "http://dev.soloman.org.cn/API/v1/";
	String urlv2 = "http://dev.soloman.org.cn/API/v2/";
	String urls;
	static Netroid instance = null;
	// Netroid入口，私有该实例，提供方法对外服务。
	private static RequestQueue mRequestQueue;

	// 图片加载管理器，私有该实例，提供方法对外服务。
	private static ImageLoader mImageLoader;

	// 文件下载管理器，私有该实例，提供方法对外服务。
	private static FileDownloader mFileDownloader;

	private void init()
	{
		instance = this;
	}

	public String returnURL(String urls, String vs)
	{
		if (vs.equals("v2"))
		{
			urls = urlv2 + urls;
		} else
		{
			urls = url + urls;
		}
		System.out.println(urls + "      链接");
		return urls;
	}

	public static Netroid getInstance()
	{
		if (instance == null)
		{
			instance = new Netroid();
		}
		return instance;
	}

	public static void init(Context ctx)
	{
		preferences = PreferenceUtils.getInstance(ctx,
				PreferenceConstants.LOGIN_PREF);
		if (mRequestQueue != null)
			throw new IllegalStateException("initialized");

		// 创建Netroid主类，指定硬盘缓存方案
		Network network = new BasicNetwork(
				new HurlStack(Const.USER_AGENT, null), HTTP.UTF_8);
		mRequestQueue = new RequestQueue(network, 4, new DiskCache(new File(
				ctx.getCacheDir(), Const.HTTP_DISK_CACHE_DIR_NAME),
				Const.HTTP_DISK_CACHE_SIZE));
		System.out.println("进入ini初始化1");
		// 创建ImageLoader实例，指定内存缓存方案
		// 注：SelfImageLoader的实现示例请查看图片加载的相关文档
		// 注：ImageLoader及FileDownloader不是必须初始化的组件，如果没有用处，不需要创建实例
		mImageLoader = new SelfImageLoader(mRequestQueue, new BitmapImageCache(
				Const.HTTP_MEMORY_CACHE_SIZE), ctx.getResources(),
				ctx.getAssets())
		{
			@Override
			public void makeRequest(ImageRequest request)
			{
				request.setCacheExpireTime(TimeUnit.DAYS, 30);
			}
		};
		System.out.println("进入ini初始化2");
		mFileDownloader = new FileDownloader(mRequestQueue, 1);
		mRequestQueue.start();
	}

	// 示例做法：执行自定义请求以获得书籍列表
	// public static void getBookList(int pageNo, Listener<List<Book>> listener)
	// {
	// mRequestQueue.add(new BookListRequest("http://server.com/book_list/"
	// + pageNo, listener));
	// }
	// 专门用来解析那段
	/**
	 * 
	 * @param isF
	 *            是否读取缓存 true不读缓存 false读取缓存
	 * @param tag
	 *            缓存name
	 * @param method
	 *            请求方式
	 * @param url
	 *            请求链接
	 * @param jsonRequest
	 * @param listener
	 *            回调
	 */
	public static void getApiUrl(boolean isF, String tag, int method,
			String url, JSONObject jsonRequest, Listener<JSONObject> listener)
	{
		JsonObjectRequest request = new JsonObjectRequest(method, url,
				jsonRequest, listener);
		request.setTag(tag);
		// 头部设置
		if (preferences.getString("access_token", "") != "")
		{
			request.addHeader("X-User-token",
					preferences.getString("access_token", ""));
			System.out.println(preferences.getString("access_token", "")
					+ "             token");

		}
		if (isF == true)
		{
			// true 为不读缓存
			request.setForceUpdate(true);
		} else
		{
			request.setForceUpdate(false);
		}
		// 缓存时间为1小时
		request.setCacheExpireTime(TimeUnit.HOURS, 1);
		// 创建了一个超时时长为5000ms，重试10次的请求
		request.setRetryPolicy(new DefaultRetryPolicy(5000, 10,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		mRequestQueue.add(request);

	}

	// 加载单张图片
	public static void displayImage(String url, ImageView imageView)
	{
		ImageLoader.ImageListener listener = ImageLoader.getImageListener(
				imageView, R.drawable.ic_launcher, R.drawable.ic_launcher);
		mImageLoader.get(url, listener, imageView.getWidth(),
				imageView.getHeight());
	}

	// // 批量加载图片
	public static void displayImage(String url, NetworkImageView imageView)
	{
		// imageView.setDefaultImageResId(R.drawable.werr);
		// imageView.setErrorImageResId(R.drawable.werr);
		imageView.setImageUrl(url, mImageLoader);
	}

	// 执行文件下载请求
	public static FileDownloader.DownloadController addFileDownload(
			String storeFilePath, String url, Listener<Void> listener)
	{
		return mFileDownloader.add(storeFilePath, url, listener);
	}

	public class Const
	{
		// http parameters
		public static final int HTTP_MEMORY_CACHE_SIZE = 2 * 1024 * 1024; // 2MB
		public static final int HTTP_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
		public static final String HTTP_DISK_CACHE_DIR_NAME = "netroid";
		public static final String USER_AGENT = "netroid.cn";
	}

}
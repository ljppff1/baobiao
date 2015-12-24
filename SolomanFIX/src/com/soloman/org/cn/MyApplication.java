package com.soloman.org.cn;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import cn.jpush.android.api.JPushInterface;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.PreferenceUtils;

/**
 * @author zwl 全局配置表
 * 
 */
public class MyApplication extends Application
{
	private static MyApplication instance = null;
	private PreferenceUtils preferences;

	public DisplayImageOptions DEFAULT_IMAGE_OPTIONS;
	public ImageLoaderConfiguration DEFAULT_IMAGE_CONFIGS;
	public File DEFAULT_IMAGE_CACHE_DIR;	
	private static ImageLoader imageLoader = ImageLoader.getInstance();
	/**
	 * 本地安装版本
	 */
	public static int localVersion;
	/**
	 * 安装目录
	 */
	public static String downloadDir = "soloman/";

	private String uname, upwd, uid, pwd;
	private HashMap<String, String> mMap;

	@Override
	public void onCreate()
	{
		super.onCreate();
		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this); // 初始化 JPush
		init();
		Netroid.init(this);
		// 应用更新相关
		try
		{
			PackageInfo packageInfo = getApplicationContext()
					.getPackageManager().getPackageInfo(getPackageName(), 0);
			localVersion = packageInfo.versionCode;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		
		
		DEFAULT_IMAGE_OPTIONS = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisc(true)
		 .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		 .bitmapConfig(Bitmap.Config.RGB_565)
		 // 防止内存溢出的，图片太多就这个。还有其他设置
		 .delayBeforeLoading(1000)
		.showStubImage(R.drawable.werr)
		.showImageForEmptyUri(R.drawable.werr)
		.showImageOnFail(R.drawable.werr)
		 .displayer(new RoundedBitmapDisplayer(5))
		.build();
		DEFAULT_IMAGE_CACHE_DIR = StorageUtils
				.getCacheDirectory(getApplicationContext());
		DEFAULT_IMAGE_CONFIGS = new ImageLoaderConfiguration.Builder(this)
		.memoryCacheExtraOptions(480, 800)
		// 缓存在内存的图片的宽和高度
		.threadPoolSize(3)
		// 线程池内加载的数量
		.memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024))//
		.memoryCacheSize(5 * 1024 * 1024) // 缓存到内存的最大数据
		.discCacheSize(50 * 1024 * 1024) // 缓存到文件的最大数据
		.discCacheFileCount(10000) // 文件数量
		.defaultDisplayImageOptions(DEFAULT_IMAGE_OPTIONS). //
		// 上面的options对象，一些属性配置
		build();
		imageLoader.init(DEFAULT_IMAGE_CONFIGS);
	}

	private void init()
	{
		instance = this;
	}

	public static MyApplication getInstance()
	{
		if (instance == null)
		{
			instance = new MyApplication();
		}
		return instance;
	}


	private List<Activity> activities = new ArrayList<Activity>();

	public void addActivity(Activity activity)
	{
		activities.add(activity);
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();

		for (Activity activity : activities)
		{
			activity.finish();
		}
	}

//	public Map<String, String> getPerferceMap()
//	{
//		preferences = PreferenceUtils.getInstance(getApplicationContext(),
//				PreferenceConstants.LOGIN_PREF);
//		uname = preferences.getString(PreferenceConstants.LOGIN_USERNAME, "");
//		upwd = preferences.getString(PreferenceConstants.LOGIN_USERPWD, "");
//		uid = preferences.getString(PreferenceConstants.LOGIN_UID, "");
//		token = preferences.getString(PreferenceConstants.LOGIN_TOKEN, "");
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("uname", uname);
//		map.put("upwd", upwd);
//		map.put("uid", uid);
//		map.put("token", token);
//		return map;
//	}

	/**
	 * 设置手机网络类型，wifi，cmwap，ctwap，用于联网参数选择
	 * 
	 * @return
	 */
	static String setNetworkType()
	{
		String networkType = "wifi";
		ConnectivityManager manager = (ConnectivityManager) instance
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netWrokInfo = manager.getActiveNetworkInfo();
		if (netWrokInfo == null || !netWrokInfo.isAvailable())
		{
			// 当前网络不可用
			return "";
		}

		String info = netWrokInfo.getExtraInfo();
		if ((info != null)
				&& ((info.trim().toLowerCase().equals("cmwap"))
						|| (info.trim().toLowerCase().equals("uniwap"))
						|| (info.trim().toLowerCase().equals("3gwap")) || (info
						.trim().toLowerCase().equals("ctwap"))))
		{
			// 上网方式为wap
			if (info.trim().toLowerCase().equals("ctwap"))
			{
				// 电信
				networkType = "ctwap";
			} else
			{
				networkType = "cmwap";
			}

		}
		return networkType;
	}

}

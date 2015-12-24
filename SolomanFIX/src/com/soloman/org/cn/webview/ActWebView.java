package com.soloman.org.cn.webview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soloman.org.cn.R;

public class ActWebView extends Activity
{
	private ImageView act_webview_iv;
	private WebView webView1;
	private TextView act_webview_tv;
	private int Mark;
	private String mpath;
	private String URL_PATH;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_web_view);
		setupView();
		init();
		Setting();
	}

	private void setupView()
	{
		Mark = getIntent().getExtras().getInt("Mark");
		URL_PATH = getIntent().getExtras().getString("URL");
		act_webview_iv = (ImageView) findViewById(R.id.act_webview_iv);
		webView1 = (WebView) findViewById(R.id.webView1);
		act_webview_tv = (TextView) findViewById(R.id.act_webview_tv);
		act_webview_iv.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				finish();
			}
		});
	}

	private void init()
	{
		if (Mark == 1)
		{
			act_webview_tv.setText("服务范围");
			// webView1.loadUrl("http://soloman.co/service.html");
		}

		else if (Mark == 2)
		{

			act_webview_tv.setText("常见问题");
			// webView1.loadUrl("http://soloman.co/qa.html");
		} else if (Mark == 3)
		{

			act_webview_tv.setText("关于我们");
			// webView1.loadUrl("http://soloman.co/about.html");
		} else if (Mark == 4)
		{

			act_webview_tv.setText("用户协议");
			// webView1.loadUrl("http://soloman.co/agreement.html");
		}

		// WebSettings settings = webView1.getSettings();
		// // settings.setJavaScriptEnabled(true);
		//
		// settings.setSupportZoom(true);
		//
		// webView1.requestFocus();
		//
		// // WebSettings.LOAD_NO_CACHE不适用缓存 LOAD_CACHE_ELSE_NETWORK使用缓存
		// webView1.getSettings()
		// .setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// webView1.setWebViewClient(new WebViewClient()
		// {
		// @Override
		// public boolean shouldOverrideUrlLoading(WebView view, String url)
		// {
		// // TODO Auto-generated method stub
		// // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
		// view.loadUrl(url);
		// return true;
		// }
		// });
	}

	private void Setting()
	{
		WebSettings webSettings = webView1.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		// 设置禁止访问文件数据
		webSettings.setAllowFileAccess(false);
		// 设置是支持缩放
		webSettings.setBuiltInZoomControls(true);

		MyWebViewClient myWebViewClient = new MyWebViewClient();
		webView1.setWebViewClient(myWebViewClient);
		// 加载自定义web网址

		try
		{
			// 再次判断传递进来的路径是否为空或者包含http
			if (URL_PATH != null && URL_PATH.indexOf("http://") != -1)
			{
				mpath = URL_PATH;
				webView1.loadUrl(mpath);
			} else
			{

				Toast.makeText(this, "链接不存在", 3000).show();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private class MyWebViewClient extends WebViewClient
	{
		// 重写父类方法，让新打开的网页在当前的WebView中显示
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			view.loadUrl(url);
			return true;
		}
	}

}

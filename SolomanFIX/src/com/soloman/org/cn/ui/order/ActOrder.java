package com.soloman.org.cn.ui.order;

/**
 * 订单详情支付页
 */
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.ui.pay.ActPays;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;
import com.soloman.org.cn.utis.wx.Constants;
import com.soloman.org.cn.utis.zfb.PayResult;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class ActOrder extends Activity
{
	RelativeLayout animationHoldingFrame;
	ImageView mapPictures;
	private Animation anim;
	// 商户PID
	public static final String PARTNER = "2088911097138942";
	// 商户收款账号
	public static final String SELLER = "bank@soloman.co";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMamBPMUMFI46nBNQqKhJA/pr5FxdJRsBCDurW0mAwHQTHF7+s0v0uivZ19CEvN/E8DUnBj6qBQQsoPE+Z0UeQgsQEZIE5BHe2s7pwA/dg3HYiUAzpaEP+HR9E7d9iV3g8hj/ZZF3alvRmdyUAFUUhcZuzeEVsncaQfqD/ooWFfNAgMBAAECgYB9giF4b5YELR+6aqYiWIuPXkdmLnfI2uJU6Fg8sRqPR8s0nG+euouYJlK6teWBYRXa7LnfcmdFnjMb3BUV8PxyV0IDL1eqeHdT9ofiXoldBQ7/Ok/IhErNGAe7DP9J7M7XvrYC+jZin8i9GkktQV6Kv6vrximOSAOefeTRxUovoQJBAOtf557S4tHovGDO3W+r2g5eGVZ3gskrTir4nA2+mAGR+ueVXTp9kcSo7v3gWXz2liGhxqAwrLlM9/RwcocqwvkCQQDYDj9hrBgQVdaNwu423Ea80WWCDGyRLFig8zvuYSSXkQrsrVuY4pHOtnFFRJW0WmvOFb9MKvuf0wARM5W8yhx1AkAYTbPN23qeMAjbeiC1Oipu11qIahiwCu+sUjVS2f46E0e0B+Eze+nEQ6lBv5ud4pxH5rtqqPv8uizUKZQ5zrmJAkB5uH0oz8W23kVEFIAUVEFd+zKO8+TuXOeJtb8b64qsAUEAWiJynaK7rLlf5uXYd+CoeEQP619biYpOJpvDgrJJAkEAm0N+kuMJXda/s+d4v1E6tgJZLmG0l7cYy65jc8gBa5WXki9RRTnv8hk5R/zu7mSwd4zJ17MT+0D5lVw/p/2JhA==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	private PopupWindow mPopupWindow;
	// 屏幕的width
	private int mScreenWidth;
	// 屏幕的height
	private int mScreenHeight;
	// PopupWindow的width
	private int mPopupWindowWidth;
	// PopupWindow的height
	private int mPopupWindowHeight;

	PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	TextView show;
	Map<String, String> resultunifiedorder;
	StringBuffer sb;
	private PreferenceUtils preferences;
	private ImageView item_present_indent_details_tv_cancel;
	// 姓名
	private TextView item_present_indent_details_tv_name;
	// 电话
	private TextView item_present_indent_details_tv_phone;
	// 地址
	private TextView item_present_indent_details_tv_addresse;
	// 开始时间
	private TextView item_present_indent_details_tv_start_date;
	// 结束时间
	private TextView item_present_indent_details_tv_end_date;
	// 时长
	private TextView item_present_indent_details_tv_duration;
	// 特卫人数
	private TextView item_present_indent_details_tv_number;
	// 级别
	private TextView item_present_indent_details_tv_level;
	// 捎一句
	private TextView item_present_indent_details_tv_say;
	// 价格
	private TextView item_present_indent_details_tv_price;
	// 按钮
	private TextView item_present_indent_details_tv_money;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_order);
		MyApplication.getInstance().addActivity(this);
		// ActOrder.this.mHandler.sendEmptyMessage(10);
		setupView();
		setupListener();
		// order();
	}

	private void setupView()
	{
		animationHoldingFrame = (RelativeLayout) findViewById(R.id.animationHoldingFrame);
		mapPictures = (ImageView) findViewById(R.id.mapPictures);
		anim = AnimationUtils.loadAnimation(this, R.anim.progressbar);
		bundle = getIntent().getExtras();
		preferences = PreferenceUtils.getInstance(ActOrder.this,
				PreferenceConstants.LOGIN_PREF);
		item_present_indent_details_tv_cancel = (ImageView) findViewById(R.id.item_present_indent_details_tv_cancel);
		// 姓名
		item_present_indent_details_tv_name = (TextView) findViewById(R.id.item_present_indent_details_tv_name);
		item_present_indent_details_tv_name.setText(bundle.getString("name"));
		// 电话
		item_present_indent_details_tv_phone = (TextView) findViewById(R.id.item_present_indent_details_tv_phone);
		item_present_indent_details_tv_phone.setText(bundle
				.getString("phone_num"));

		// 地址
		item_present_indent_details_tv_addresse = (TextView) findViewById(R.id.item_present_indent_details_tv_addresse);
		item_present_indent_details_tv_addresse.setText(bundle
				.getString("city") + "," + bundle.getString("address"));
		// 开始时间
		item_present_indent_details_tv_start_date = (TextView) findViewById(R.id.item_present_indent_details_tv_start_date);
		item_present_indent_details_tv_start_date.setText(bundle.getString(
				"service_at").toString());
		// 结束时间
		item_present_indent_details_tv_end_date = (TextView) findViewById(R.id.item_present_indent_details_tv_end_date);
		item_present_indent_details_tv_end_date.setText(bundle.getString(
				"end_at").toString());
		// 时长
		item_present_indent_details_tv_duration = (TextView) findViewById(R.id.item_present_indent_details_tv_duration);
		item_present_indent_details_tv_duration.setText(bundle.getString(
				"duration").toString());
		// 特卫人数
		item_present_indent_details_tv_number = (TextView) findViewById(R.id.item_present_indent_details_tv_number);
		item_present_indent_details_tv_number.setText(bundle.getString(
				"people_count").toString());
		// 级别
		item_present_indent_details_tv_level = (TextView) findViewById(R.id.item_present_indent_details_tv_level);
		item_present_indent_details_tv_level.setText(bundle.getString("level")
				.toString());
		// 捎一句
		item_present_indent_details_tv_say = (TextView) findViewById(R.id.item_present_indent_details_tv_say);
		item_present_indent_details_tv_say.setText(bundle.getString("words")
				.toString());
		// 价格
		item_present_indent_details_tv_price = (TextView) findViewById(R.id.item_present_indent_details_tv_price);
		item_present_indent_details_tv_price.setText(bundle.getString("price"));
		// 按钮
		item_present_indent_details_tv_money = (TextView) findViewById(R.id.item_present_indent_details_tv_money);
	}

	private void setupListener()
	{
		item_present_indent_details_tv_cancel
				.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						finish();
					}
				});

		item_present_indent_details_tv_money
				.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						Intent intent = new Intent(ActOrder.this, ActPays.class);
						bundle.putString("i", "ActOrder");
						intent.putExtras(bundle);
						startActivityForResult(intent, 10);
					}
				});

	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1)
		{
			switch (requestCode)
			{
			case 10:
				// 获取启动这个activity的intent
				Intent intent = getIntent();
				ActOrder.this.setResult(1, intent);// 跳转回原来的activity
				ActOrder.this.finish();// 结束当前activity
				break;
			default:
				break;
			}
		}
	}

}

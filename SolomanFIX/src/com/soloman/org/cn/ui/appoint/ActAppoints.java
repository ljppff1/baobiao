package com.soloman.org.cn.ui.appoint;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.BobygyardKevel;
import com.soloman.org.cn.bean.CommonAddress;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.address.ActCommonAddress;
import com.soloman.org.cn.ui.indent.FraPresentIndent;
import com.soloman.org.cn.ui.individualData.imagess.AlbumActivity;
import com.soloman.org.cn.ui.individualData.imagess.ImageFile;
import com.soloman.org.cn.ui.map.FraMap;
import com.soloman.org.cn.ui.pay.ActPays;
import com.soloman.org.cn.ui.sliding.ActHosts;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.imagess.Bimp;
import com.soloman.org.cn.utis.wx.Constants;
import com.soloman.org.cn.utis.wx.MD5;
import com.soloman.org.cn.utis.zfb.PayResult;
import com.soloman.org.cn.utis.zfb.SignUtils;
import com.soloman.org.cn.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class ActAppoints extends Activity implements OnClickListener
{

	int id;
	String name;
	String info;
	String complex;
	String phone;

	// 支付
	private String dddid;
	String names;
	String prices;
	private JSONObject obj;
	private JSONObject objj;
	RelativeLayout item_rl_zfb;
	RelativeLayout item_rl_wx;
	RelativeLayout item_rl_close;
	TextView item_price;
	// 显示价格
	String displayPrice;
	// 支付价格
	int payPrice;
	// 显示类型
	String displayYype;
	PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	StringBuffer sb;
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

	//

	private PopupWindow mPopupWindow;
	// 屏幕的width
	private int mScreenWidth;
	// 屏幕的height
	private int mScreenHeight;
	// PopupWindow的width
	private int mPopupWindowWidth;
	// PopupWindow的height
	private int mPopupWindowHeight;
	private ProgressDialog proDialog;
	private ArrayList<BobygyardKevel> lt;
	private CommonAddress cmnAddress;
	private Bundle bb;
	private String price;
	private String current_price;
	private String diff;
	private String type;
	private int typeid;
	private String date;
	private String date2;
	private String number;
	private int number2;
	private int level;
	private ImageView fra_iv_viewss;
	// 地址
	private RelativeLayout act_rl_address;
	private ImageView act_iv;
	private TextView act_tv_name;
	private ImageView act_iv_s;
	private TextView act_tv_phone;
	private TextView act_tv_address;
	// 服务类型
	private LinearLayout act_rl_type;
	private TextView act_tv_type;
	// 服务时长
	private LinearLayout act_rl_date;
	private TextView act_tv_date;
	// 开始时间
	private LinearLayout act_rl_start_date;
	private TextView act_tv_start_date;
	// 特卫等级
	private RelativeLayout act_rl_special_level;
	private View[] view;
	private ImageView act_iv_special_level;
	private ImageView act_iv_special_levels;
	private ImageView act_iv_special_levelss;
	private ImageView act_iv_special_levelsss;
	private ImageView act_iv_special_levelssss;
	// 特卫人数
	private LinearLayout act_rl_special_number;
	private TextView act_tv_special_number;
	// 捎一句
	private LinearLayout act_rl_message;
	private EditText act_tv_message;
	// 订单价格
	private TextView act_tv_money;
	// 优惠后的订单价格
	private TextView act_tv_discount_money;

	private Bundle bundle;
	private TextView fra_tv_;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_appoints);
		sb = new StringBuffer();
		req = new PayReq();
		// app注册到微信
		msgApi.registerApp(Constants.APP_ID);
		setupView();
		setListener();
		calculationPrice();
	}

	/**
	 * 价格计算
	 */
	private void calculationPrice()
	{
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();
		requestParams.put("order_level", level + "");
		requestParams.put("service_at", date2 + ":00");
		requestParams.put("duration", date);
		requestParams.put("level", type);
		requestParams.put("people_count", number2 + "");
		HttpRestClient.postHttpHuaShangha(this, "orders/price", "v2",
				requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						String code;
						try
						{
							code = response.getString("code");

							if (code.equals("200"))
							{
								JSONObject obj = response.getJSONObject("data");
								price = obj.getString("price");
								current_price = obj.getString("current_price");
								diff = obj.getString("diff");
								act_tv_money.setText(current_price);
								act_tv_discount_money.setText("(已优惠￥" + diff
										+ ")");

							} else
							{
								Toast.makeText(ActAppoints.this,
										response.getString("message"),
										Toast.LENGTH_LONG).show();
							}

						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable e, JSONObject errorResponse)
					{
						// TODO Auto-generated method stub
						super.onFailure(e, errorResponse);
						System.out.println("aaa");
					}
				});

	}

	/** 进度条，自己可以定义 **/
	private void LoginDialog()
	{
		proDialog = ProgressDialog.show(ActAppoints.this, "下单中",
				"下单中..请稍后....", true, false);

	}

	private void setupView()
	{
		IntentFilter filter = new IntentFilter("data.broadcast.actions");
		registerReceiver(broadcastReceiver, filter);

		bundle = getIntent().getExtras();
		fra_iv_viewss = (ImageView) findViewById(R.id.fra_iv_viewss);
		fra_tv_ = (TextView) findViewById(R.id.fra_tv_);
		act_rl_address = (RelativeLayout) findViewById(R.id.act_rl_address);
		act_iv = (ImageView) findViewById(R.id.act_iv);
		act_tv_name = (TextView) findViewById(R.id.act_tv_name);
		act_iv_s = (ImageView) findViewById(R.id.act_iv_s);
		act_tv_phone = (TextView) findViewById(R.id.act_tv_phone);
		act_tv_address = (TextView) findViewById(R.id.act_tv_address);
		act_rl_type = (LinearLayout) findViewById(R.id.act_rl_type);
		act_tv_type = (TextView) findViewById(R.id.act_tv_type);
		act_rl_date = (LinearLayout) findViewById(R.id.act_rl_date);
		act_tv_date = (TextView) findViewById(R.id.act_tv_date);
		act_rl_start_date = (LinearLayout) findViewById(R.id.act_rl_start_date);
		act_tv_start_date = (TextView) findViewById(R.id.act_tv_start_date);

		act_rl_special_level = (RelativeLayout) findViewById(R.id.act_rl_special_level);
		act_iv_special_level = (ImageView) findViewById(R.id.act_iv_special_level);
		act_iv_special_levels = (ImageView) findViewById(R.id.act_iv_special_levels);
		act_iv_special_levelss = (ImageView) findViewById(R.id.act_iv_special_levelss);
		act_iv_special_levelsss = (ImageView) findViewById(R.id.act_iv_special_levelsss);
		act_iv_special_levelssss = (ImageView) findViewById(R.id.act_iv_special_levelssss);
		view = new View[]
		{ act_iv_special_level, act_iv_special_levels, act_iv_special_levelss,
				act_iv_special_levelsss, act_iv_special_levelssss };

		act_rl_special_number = (LinearLayout) findViewById(R.id.act_rl_special_number);
		act_tv_special_number = (TextView) findViewById(R.id.act_tv_special_number);
		act_rl_message = (LinearLayout) findViewById(R.id.act_rl_message);
		act_tv_message = (EditText) findViewById(R.id.act_tv_message);
		act_tv_money = (TextView) findViewById(R.id.act_tv_money);
		act_tv_discount_money = (TextView) findViewById(R.id.act_tv_discount_money);
		type = bundle.getString("type");
		typeid = bundle.getInt("typeid");
		date = bundle.getString("date");
		date2 = bundle.getString("date2");
		number = bundle.getString("number");
		number2 = (bundle.getInt("number2") + 1);
		level = bundle.getInt("level");
		act_tv_type.setText(bundle.getString("type"));
		act_tv_date.setText(bundle.getString("date"));
		act_tv_start_date.setText(bundle.getString("date2"));
		act_tv_special_number.setText(bundle.getString("number"));

		for (int i = 0; i < level; i++)
		{
			view[i].setVisibility(View.VISIBLE);
		}
//		act_tv_name.setText("刘德华");
//		act_iv_s.setImageResource(R.drawable.bianji3);
//		act_tv_phone.setVisibility(View.VISIBLE);
//		act_tv_phone.setText("123456789011");
//		act_tv_address.setVisibility(View.VISIBLE);
//		act_tv_address.setText("大冲城市花园");
	}

	private void setListener()
	{
		act_rl_address.setOnClickListener(this);
		act_rl_type.setOnClickListener(this);
		act_rl_date.setOnClickListener(this);
		act_rl_start_date.setOnClickListener(this);
		act_rl_special_level.setOnClickListener(this);
		act_rl_special_number.setOnClickListener(this);
		act_rl_message.setOnClickListener(this);
		fra_tv_.setOnClickListener(this);
		fra_iv_viewss.setOnClickListener(this);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			// 返回
			Intent intentss = new Intent(ActAppoints.this, ActAppoint.class);
			intentss.putExtras(bundle);
			startActivity(intentss);
			unregisterReceiver(broadcastReceiver);
			finish();
		}
		return false;

	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			// mContext.unregisterReceiver(this);
			// TODO Auto-generated method stub
			unregisterReceiver(broadcastReceiver);
			finish();
		}
	};

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.act_rl_address:
			Intent intent = new Intent(ActAppoints.this, ActCommonAddress.class);
			Bundle bundles = new Bundle();
			bundles.putBoolean("is", true);
			bundles.putInt("item",id);
			intent.putExtras(bundles);
			startActivityForResult(intent, 10);

			break;
		case R.id.fra_iv_viewss:
			// 返回
			Intent intentss = new Intent(ActAppoints.this, ActAppoint.class);
			intentss.putExtras(bundle);
			startActivity(intentss);
			unregisterReceiver(broadcastReceiver);
			finish();
			break;
		case R.id.fra_tv_:
//			LoginDialog();
			// 下单
//			determineOrder();
			
			Intent intents = new Intent(ActAppoints.this, ActPays.class);
			bundle.putString("name", name);
			bundle.putString("info",info);
			bundle.putString("complex",complex);
			bundle.putString("phone", phone);
			bundle.putString("act_tv_message", act_tv_message.getText()
					.toString());
			bundle.putString("id",id+"");
			intents.putExtras(bundle);
			startActivity(intents);
			break;
		}
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1)
		{
			bb = data.getExtras();
			switch (requestCode)
			{
			case 10:
				id = bb.getInt("id");
				name = bb.getString("name");
				info = bb.getString("info");
				complex = bb.getString("complex");
				phone = bb.getString("phone");
				
				act_tv_name.setText(name);
				act_iv_s.setImageResource(R.drawable.bianji3);
				act_tv_phone.setVisibility(View.VISIBLE);
				act_tv_phone.setText(phone);
				act_tv_address.setVisibility(View.VISIBLE);
				act_tv_address.setText(complex+info);
				break;
			default:
				break;
			}
		}
	}
}

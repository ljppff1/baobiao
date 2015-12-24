package com.soloman.org.cn.ui.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.appoint.ActAppoints;
import com.soloman.org.cn.ui.indent.FraPresentIndent;
import com.soloman.org.cn.ui.map.FraMap;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;
import com.soloman.org.cn.utis.wx.Constants;
import com.soloman.org.cn.utis.wx.MD5;
import com.soloman.org.cn.utis.zfb.PayResult;
import com.soloman.org.cn.utis.zfb.SignUtils;
import com.soloman.org.cn.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class ActPays extends Activity
{
	private PopupWindow mPopupWindow;
	// 屏幕的width
	private int mScreenWidth;
	// 屏幕的height
	private int mScreenHeight;
	// PopupWindow的width
	private int mPopupWindowWidth;
	// PopupWindow的height
	private int mPopupWindowHeight;
	private String id;
	private String type;
	private int typeid;
	private String date;
	private String date2;
	private String number;
	private int number2;
	private int level;
	private ProgressDialog proDialog;

	private String name;
	private String info;
	private String complex;
	private String phone;
	private String act_tv_message;
	// 显示价格
	String displayPrice;
	// 支付价格
	int payPrice;
	// 显示类型
	String displayYype;

	private String i;
	private Animation anim;
	PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	TextView show;
	Map<String, String> resultunifiedorder;
	StringBuffer sb;
	private JSONObject obj;
	private JSONObject objj;
	private JSONObject objes;
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
	// 微信
	private RelativeLayout item_rl_zfb;
	// 支付宝
	private RelativeLayout item_rl_wx;
	// 取消
	private RelativeLayout item_rl_close;
	private TextView item_price;
	private Bundle bundle;
	// 订单id
	private String dddid;
	// 服务类型 商品名
	private String names;
	// 价格
	private String prices;
	private PreferenceUtils preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_order);
		MyApplication.getInstance().addActivity(this);
		LoginDialog();
		bundle = getIntent().getExtras();
		setupView();
		determineOrder();
	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		
		IntentFilter filter = new IntentFilter("data.broadcast.actions");
		registerReceiver(broadcastReceiver, filter);

		preferences = PreferenceUtils.getInstance(ActPays.this,
				PreferenceConstants.LOGIN_PREF);
		sb = new StringBuffer();
		req = new PayReq();
		// app注册到微信
		msgApi.registerApp(Constants.APP_ID);

		type = bundle.getString("type");
		typeid = bundle.getInt("typeid");
		date = bundle.getString("date");
		date2 = bundle.getString("date2");
		number = bundle.getString("number");
		number2 = (bundle.getInt("number2") + 1);
		level = bundle.getInt("level");
		id = bundle.getString("id");
		name = bundle.getString("name");
		info = bundle.getString("info");
		complex = bundle.getString("complex");
		phone = bundle.getString("phone");
		act_tv_message = bundle.getString("act_tv_message");

	}

	/*
	 * 创建PopupWindow
	 */
	private void initPopuptWindow()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.item_pays, null);
		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height
		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// 需要设置一下此参数，点击外边可消失
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());屏蔽这个之后返回按键才会监听
		// 设置点击窗口外边窗口消失
		mPopupWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		mPopupWindow.setFocusable(true);
		// 获取屏幕和PopupWindow的width和height
		mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
		mScreenWidth = getWindowManager().getDefaultDisplay().getHeight();
		mPopupWindowWidth = mPopupWindow.getWidth();
		mPopupWindowHeight = mPopupWindow.getHeight();
		popupWindow.setFocusable(true);
		popupWindow.setFocusableInTouchMode(true);
		popupWindow.setOnKeyListener(new OnKeyListener()
		{

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2)
			{
				// TODO Auto-generated method stub
				mPopupWindow.dismiss();
				Toast.makeText(ActPays.this, "支付失败,订单已保存", Toast.LENGTH_LONG)
						.show();
				// Intent intents = new Intent(ActAppoints.this,
				// ActHosts.class);
				// intents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// startActivity(intents);
				finish();
				return false;
			}
		});

		item_price = (TextView) popupWindow.findViewById(R.id.item_price);
		item_rl_zfb = (RelativeLayout) popupWindow
				.findViewById(R.id.item_rl_zfb);
		item_rl_zfb.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				// 支付宝支付

				// 支付宝支付
				// 订单
				// String orderInfo = getOrderInfo(names, ".",
				// prices.substring(0, prices.indexOf("元")));

				String orderInfo = getOrderInfo(names, ".", prices);

				// 对订单做RSA 签名
				String sign = sign(orderInfo);
				try
				{
					// 仅需对sign 做URL编码
					sign = URLEncoder.encode(sign, "UTF-8");
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}

				// 完整的符合支付宝参数规范的订单信息
				final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
						+ getSignType();

				Runnable payRunnable = new Runnable()
				{

					@Override
					public void run()
					{
						// 构造PayTask 对象
						PayTask alipay = new PayTask(ActPays.this);
						// 调用支付接口，获取支付结果
						String result = alipay.pay(payInfo);

						Message msg = new Message();
						msg.what = SDK_PAY_FLAG;
						msg.obj = result;
						mHandler.sendMessage(msg);
					}
				};

				// 必须异步调用
				Thread payThread = new Thread(payRunnable);
				payThread.start();
			}
		});
		item_rl_wx = (RelativeLayout) popupWindow.findViewById(R.id.item_rl_wx);
		item_rl_wx.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// 微信支付
				try
				{
					// Intent intents = new Intent(ActAppoints.this,
					// WXPayEntryActivity.class);
					// // intents.putExtras(bundle);
					// startActivityForResult(intents, 10);

					sb.append("prepay_id\n" + objj.get("prepay_id") + "\n\n");
					req.appId = obj.getString("appid");
					req.partnerId = obj.getString("partnerid");
					req.prepayId = obj.getString("prepayid");
					req.packageValue = obj.getString("package");
					req.nonceStr = obj.getString("noncestr");
					req.timeStamp = obj.getString("timestamp");

					List<NameValuePair> signParams = new LinkedList<NameValuePair>();
					signParams.add(new BasicNameValuePair("appid", req.appId));
					signParams.add(new BasicNameValuePair("noncestr",
							req.nonceStr));
					signParams.add(new BasicNameValuePair("package",
							req.packageValue));
					signParams.add(new BasicNameValuePair("partnerid",
							req.partnerId));
					signParams.add(new BasicNameValuePair("prepayid",
							req.prepayId));
					signParams.add(new BasicNameValuePair("timestamp",
							req.timeStamp));

					req.sign = genAppSign(signParams);

					sb.append("sign\n" + req.sign + "\n\n");

					// show.setText(sb.toString());
					// 启动微信支付
					msgApi.registerApp(obj.getString("appid"));
					msgApi.sendReq(req);
					Log.e("orion", signParams.toString());
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		item_rl_close = (RelativeLayout) popupWindow
				.findViewById(R.id.item_rl_close);
		item_rl_close.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// 取消支付
				Toast.makeText(ActPays.this, "支付失败,订单已保存", Toast.LENGTH_LONG)
						.show();
				Intent mIntent = new Intent("data.broadcast.actions");
				// 发送广播
				sendBroadcast(mIntent);
				finish();
			}
		});
		item_price.setText(displayPrice);

	}

	/** 进度条，自己可以定义 **/
	private void LoginDialog()
	{
		proDialog = ProgressDialog.show(ActPays.this, "下单中", "下单中..请稍后....",
				true, false);

	}

	/**
	 * 下订单
	 */
	private void determineOrder()
	{
		// TODO Auto-generated method stub 0男1女

		RequestParams requestParams = new RequestParams();
		requestParams.put("order[order_level]", typeid + "");
		requestParams.put("order[guest][name]", name);
		requestParams.put("order[guest][sex]", 0 + "");
		requestParams.put("order[guest][phone_num]", phone);
		requestParams.put("order[address][id]", id + "");
		requestParams.put("order[address][complex]", complex);
		requestParams.put("order[address][info]", info);
		requestParams.put("order[address][location]", FraMap.Location);
		requestParams.put("order[service_at]", date2 + ":00");
		requestParams.put("order[duration]", date);
		requestParams.put("order[level]", level + "");
		requestParams.put("order[people_count]", number2 + "");
		requestParams.put("order[words]", act_tv_message);
		HttpRestClient.postHttpHuaShangha(this, "orders/create", "v2",
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

								JSONObject jsonobject = obj
										.getJSONObject("order");
								displayPrice = jsonobject
										.getString("current_price");

								payPrice = jsonobject.getInt("price");
								displayYype = jsonobject
										.getString("bodyguard_level");
								names = jsonobject.getString("level");
								prices = jsonobject.getString("price");
								dddid = jsonobject.getString("id");
								tenpay_create(dddid, "192.168.0.22");
							} else
							{
								Toast.makeText(ActPays.this,
										response.getString("message"),
										Toast.LENGTH_LONG).show();
							}

						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

	}

	/**
	 * 微信预支付订单 后台接口
	 * 
	 * @param userid
	 *            用户id 写死 开发环境用168.121.0.11 运营环境用 192.168.0.22
	 * @param id
	 *            订单id
	 */
	private void tenpay_create(String userid, String id)
	{
		RequestParams requestParams = new RequestParams();
		requestParams.put("order_id", userid);
		requestParams.put("spbill_create_ip", id);
		HttpRestClient.postHttpHuaShangha(this, "pays/tenpay_create", "v2",
				requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						try
						{
							objj = response;
							obj = response.getJSONObject("sign_again_params");
							initPopuptWindow();
							View view = new View(ActPays.this);
							mPopupWindow.showAtLocation(view, Gravity.CENTER,
									0, 0);
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(response);

						proDialog.dismiss();
					}
				});

	}

	/**
	 * 
	 * 支付宝返回结果
	 */
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case SDK_PAY_FLAG:
			{
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000"))
				{
					Toast.makeText(ActPays.this, "支付成功", Toast.LENGTH_SHORT)
							.show();
					// unregisterReceiver(broadcastReceiver);
					// finish();
				} else
				{
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000"))
					{
						Toast.makeText(ActPays.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else
					{
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(ActPays.this, "支付失败订单已保存",
								Toast.LENGTH_SHORT).show();
						// unregisterReceiver(broadcastReceiver);
						// finish();
					}
				}
				break;
			}
			case SDK_CHECK_FLAG:
			{
				Toast.makeText(ActPays.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

	/**
	 * 支付宝相关
	 * 
	 * @return
	 */
	public String getSignType()
	{
		return "sign_type=\"RSA\"";
	}

	/**
	 * 支付宝 sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content)
	{
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * 微信支付签名
	 * 
	 * @param params
	 * @return
	 */
	private String genAppSign(List<NameValuePair> params)
	{
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++)
		{
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		// Log.e("orion", appSign);
		return appSign;
	}

	public String getOrderInfo(String subject, String body, String price)
	{
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + dddid + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1)
		{
			Bundle bb = data.getExtras();
			switch (requestCode)
			{
			case 10:
				System.out.println("aaa");
				break;
			default:
				break;
			}
		}
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
}

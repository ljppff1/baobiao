package com.soloman.org.cn.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.BobygyardKevel;
import com.soloman.org.cn.bean.Customer;
import com.soloman.org.cn.bean.Update;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.appoint.ActAppoints;
import com.soloman.org.cn.ui.sliding.ActHosts;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.NetworkJudge;
import com.soloman.org.cn.utis.PhoneRoMailVerify;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;

/**
 * 登录
 * 
 * @author Mac
 * 
 */
public class ActLogin extends Activity implements OnClickListener
{
	private ProgressDialog mPrgsDialog;
	private String[] data;
	private int i = 60;
	private PreferenceUtils preferences;
	private ImageView login_click1;
	private ImageView login_click2;
	private TextView login_tv_get;
	private TextView login_tv_gets;
	/**
	 * 验证码
	 */
	private EditText login_et_verification;
	/**
	 * 电话
	 */
	private EditText login_et_phone;
	/**
	 * 获取验证码
	 */
	private RelativeLayout login_rl_;
	/**
	 * 确定登陆
	 */
	private RelativeLayout logon_rl_click;
	/**
	 * 用于关闭软键盘
	 */
	private RelativeLayout login_rl_main;
	private ProgressDialog proDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		MyApplication.getInstance().addActivity(this);

		setUpView();
		setUpListener();
	}

	private void setUpListener()
	{
		login_et_phone.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				if (arg0.length() > 0)
				{
					login_click1.setVisibility(View.VISIBLE);
				} else
				{
					login_click1.setVisibility(View.GONE);
				}
			}
		});
		login_et_verification.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				if (arg0.length() > 0)
				{
					login_click2.setVisibility(View.VISIBLE);
				} else
				{
					login_click2.setVisibility(View.GONE);
				}
			}
		});

		login_click1.setOnClickListener(this);
		login_click2.setOnClickListener(this);
		login_rl_.setOnClickListener(this);
		logon_rl_click.setOnClickListener(this);
		login_rl_main.setOnClickListener(this);
	}

	/** 进度条，自己可以定义 **/
	private void LoginDialog()
	{
		proDialog = ProgressDialog.show(ActLogin.this, "正在登录", "登录中..请稍后....",
				true, false);

	}

	private void setUpView()
	{
		preferences = PreferenceUtils.getInstance(ActLogin.this,
				PreferenceConstants.LOGIN_PREF);
		login_click1 = (ImageView) findViewById(R.id.login_click1);
		login_click2 = (ImageView) findViewById(R.id.login_click2);
		login_tv_get = (TextView) findViewById(R.id.login_tv_get);
		login_tv_gets = (TextView) findViewById(R.id.login_tv_gets);
		login_et_verification = (EditText) findViewById(R.id.login_et_verification);
		login_et_phone = (EditText) findViewById(R.id.login_et_phone);
		login_rl_ = (RelativeLayout) findViewById(R.id.login_rl_);
		logon_rl_click = (RelativeLayout) findViewById(R.id.logon_rl_click);
		login_rl_main = (RelativeLayout) findViewById(R.id.login_rl_main);
	}

	/**
	 * 获取验证码
	 */
	private void getVerification()
	{
		RequestParams requestParams = new RequestParams();
		requestParams.put("login", login_et_phone.getText().toString());
		HttpRestClient.postHttpHuaShangha(this, "users/verification_code_sms",
				"v1", requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
								System.out.println("成功");
					}
					@Override
					public void onFailure(Throwable e, JSONObject errorResponse)
					{
						// TODO Auto-generated method stub
						super.onFailure(e, errorResponse);
						System.out.println("失败");
					}
				});
	}

	/**
	 * 登录
	 */
	private void register()
	{
		LoginDialog();
		RequestParams requestParams = new RequestParams();
		requestParams.put("login", login_et_phone.getText().toString());
		requestParams.put("code", login_et_verification.getText().toString());
		HttpRestClient.postHttpHuaShangha(this, "users/login", "v1",
				requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						JSONObject obj = response;
						JSONObject objs;
						JSONArray array;
						try
						{
							if (!response.has("user"))
							{

								Toast.makeText(ActLogin.this, "验证码错误！",
										Toast.LENGTH_LONG).show();
								return;
							}
							objs = response.getJSONObject("user");
							array = objs.getJSONArray("guests");
							JSONObject objt = array.getJSONObject(0);
							preferences.put("user_id",
									objt.getString("user_id"));

							preferences.put("access_token",
									objs.getString("access_token"));
							preferences.put("userName",
									objs.getString("username").toString());
							preferences.put("phone_number",
									objs.getString("phone_number"));

								Intent intent = new Intent(ActLogin.this,
										ActHosts.class);
								intent.putExtras(getIntent().getExtras());
								startActivity(intent);

						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						proDialog.dismiss();
						// registration();
						finish();
					}

					@Override
					public void onFailure(Throwable e, JSONObject errorResponse)
					{
						// TODO Auto-generated method stub
						super.onFailure(e, errorResponse);
						System.out.println("aa");
					}
				});

	}

	// private void registration()
	// {
	// Netroid.getApiUrl(
	// true,
	// "City",
	// Method.POST,
	// Netroid.getInstance().returnURL(
	// "jpush_configs/reference_registration_id_to_user?registration_id="
	// + preferences.getString("regId", "")
	// + "&phone_number="
	// + login_et_phone.getText().toString(),"v1"), null,
	// new Listener<JSONObject>()
	// {
	// public void onSuccess(JSONObject response)
	// {
	// System.out.println(response);
	// }
	//
	// @Override
	// public void onError(NetroidError error)
	// {
	// System.out.println(error);
	// }
	//
	// @Override
	// public void onCancel()
	// {
	// super.onCancel();
	// }
	// });
	//
	// }

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.login_click1:
			// 清空手机号码
			login_et_phone.setText("");
			break;
		case R.id.login_click2:
			// 清空验证码
			login_et_verification.setText("");
			break;
		case R.id.login_rl_:
			// 获取验证码
			if (i == 60
					&& PhoneRoMailVerify.isMobileNum(login_et_phone.getText()
							.toString()))
			{
				// 开启线程实现倒计时
				new Thread(new ThreadShow()).start();
				getVerification();
			} else if (!PhoneRoMailVerify.isMobileNum(login_et_phone.getText()
					.toString()))
			{
				Toast.makeText(ActLogin.this, "请正确输入手机号", Toast.LENGTH_LONG)
						.show();
				return;
			}

			break;
		case R.id.logon_rl_click:
			// 登陆
			if (login_et_verification.length() <= 0)
			{
				Toast.makeText(ActLogin.this, "请输入验证码", Toast.LENGTH_LONG)
						.show();
				return;
			}
			if (login_et_phone.length() <= 0)
			{
				Toast.makeText(ActLogin.this, "请输入手机号码", Toast.LENGTH_LONG)
						.show();
				return;
			}
			if (login_et_phone.length() > 0
					&& login_et_verification.length() > 0)
			{
				register();
			}
			break;
		case R.id.login_rl_main:
			// 关闭软键盘
			InputMethodManager imm = (InputMethodManager) v.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
			break;
		default:
			break;
		}
	}

	// handler类接收数据
	Handler handler = new Handler()
	{

		public void handleMessage(Message msg)
		{
			if (msg.what == 1)
			{
				Bundle b = msg.getData();
				int id = b.getInt("ID");
				login_tv_get.setText(Integer.toString(id));
				login_tv_gets.setVisibility(View.GONE);
			} else if (msg.what == 2)
			{
				login_tv_get.setText("重新");
				login_tv_gets.setVisibility(View.VISIBLE);
				login_tv_gets.setText("获取");
			}
		};
	};

	class ThreadShow implements Runnable
	{

		@Override
		public void run()
		{
			while (i > 1)
			{
				try
				{
					Message msg = new Message();
					msg.what = 1;
					Bundle b = new Bundle();
					b.putInt("ID", --i);
					msg.setData(b);
					handler.sendMessage(msg);
					Thread.sleep(1000);

				} catch (Exception e)
				{
					e.printStackTrace();
					System.out.println("thread error...");
				}
			}
			if (i == 1)
			{
				i = 60;
				ActLogin.this.handler.sendEmptyMessage(2);
			}
		}
	}

}

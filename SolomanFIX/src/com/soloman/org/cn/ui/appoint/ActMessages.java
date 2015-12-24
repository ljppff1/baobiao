package com.soloman.org.cn.ui.appoint;

import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.ui.ActCity;
import com.soloman.org.cn.ui.address.ActAppointmentsAddress;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 捎一句
 * 
 * @author Mac
 * 
 */
public class ActMessages extends Activity
{
	private PreferenceUtils preferences;
	/**
	 * 返回
	 */
	ImageView act_tv_common_address_return;
	/**
	 * 拨打客服电话
	 */
	ImageView act_tv_common_address_phone;
	private TextView tv;

	private PopupWindow mPopupWindow;
	// 屏幕的width
	private int mScreenWidth;
	// 屏幕的height
	private int mScreenHeight;
	// PopupWindow的width
	private int mPopupWindowWidth;
	// PopupWindow的height
	private int mPopupWindowHeight;

	private EditText act_et_mess;
	private ImageView act_tv_mess;
	private RelativeLayout act_rl_appoint;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_messages);
		MyApplication.getInstance().addActivity(this);
		preferences = PreferenceUtils.getInstance(ActMessages.this,
				PreferenceConstants.LOGIN_PREF);
		setupView();
		setupListener();
	}

	private void setupListener()
	{
		act_tv_mess.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				act_et_mess.setText("");
			}
		});
		act_tv_common_address_return.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				finish();
			}
		});

		act_tv_common_address_phone.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getPopupWindowInstance();
				mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			}
		});
		// 隐藏软件盘
		findViewById(R.id.keyboard).setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{

				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				act_et_mess.requestFocus(); // 请求获取焦点
				act_et_mess.clearFocus(); // 清除焦点
				return imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
			}
		});
		act_et_mess.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{

				if (hasFocus)
				{// 获得焦点
					act_tv_mess.setVisibility(View.VISIBLE);
				} else
				{// 失去焦点
					act_tv_mess.setVisibility(View.GONE);
				}
			}
		});
		act_rl_appoint.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				if (act_et_mess.getText().length() > 150)
				{
					Toast.makeText(ActMessages.this, "输入的字符请在150以内",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (act_et_mess.getText().length() != 0)
				{
					// 获取启动这个activity的intent
					Intent intent = getIntent();
					Bundle b = new Bundle();
					b.putString("mess", act_et_mess.getText().toString());// 把数据塞入intent里面
					intent.putExtras(b);
					ActMessages.this.setResult(1, intent);// 跳转回原来的activity
					preferences.put("yiju", act_et_mess.getText().toString());
				}
				ActMessages.this.finish();// 结束当前activity
			}
		});
	}

	private void setupView()
	{
		act_tv_common_address_return = (ImageView) findViewById(R.id.act_tv_common_address_return);
		act_tv_common_address_phone = (ImageView) findViewById(R.id.act_tv_common_address_phone);
		act_et_mess = (EditText) findViewById(R.id.act_et_mess);
		act_tv_mess = (ImageView) findViewById(R.id.act_tv_mess);
		act_rl_appoint = (RelativeLayout) findViewById(R.id.act_rl_appoint);
		if (!preferences.getString("yiju", "").toString().equals("捎一句"))
		{
			act_et_mess.setText(preferences.getString("yiju", "").toString());
		}
	}

	/*
	 * 获取PopupWindow实例
	 */
	private void getPopupWindowInstance()
	{
		if (null != mPopupWindow)
		{
			mPopupWindow.dismiss();
			return;
		} else
		{
			initPopuptWindow();
		}
	}

	/*
	 * 创建PopupWindow
	 */
	private void initPopuptWindow()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.act_phone, null);
		popupWindow.findViewById(R.id.phone_ll).setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						mPopupWindow.dismiss();
					}
				});
		popupWindow.findViewById(R.id.phone_ll_cancel).setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						mPopupWindow.dismiss();
					}
				});

		tv = (TextView) popupWindow.findViewById(R.id.city_tv__);
		tv.setText(preferences.getString("customer", ""));
		tv.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

			}
		});
		popupWindow.findViewById(R.id.phone_ll_).setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						try
						{
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + tv.getText().toString()));
							startActivity(intent);
						} catch (Exception e)
						{
							// TODO: handle exception
							Toast.makeText(ActMessages.this, "无有效手机卡",
									Toast.LENGTH_LONG).show();
						}

					}
				});
		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height
		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		// 需要设置一下此参数，点击外边可消失
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		mPopupWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		mPopupWindow.setFocusable(true);
		// 获取屏幕和PopupWindow的width和height
		mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
		mScreenWidth = getWindowManager().getDefaultDisplay().getHeight();
		mPopupWindowWidth = mPopupWindow.getWidth();
		mPopupWindowHeight = mPopupWindow.getHeight();
	}
}

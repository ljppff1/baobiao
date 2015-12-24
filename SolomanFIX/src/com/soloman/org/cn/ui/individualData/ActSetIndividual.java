package com.soloman.org.cn.ui.individualData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.ui.ActLogin;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;
import com.soloman.org.cn.utis.SwipeBackActivity;

/**
 * Soloman个人资料
 * 
 * @author Mac
 * 
 */
public class ActSetIndividual extends SwipeBackActivity implements
		OnClickListener
{
	/**
	 * 后退
	 */
	private ImageView act_iv_viewss;
	/**
	 * 证件号码
	 */
	private TextView act_tv_set_Certificates_number;
	/**
	 * 证件类型
	 */
	private TextView act_tv_set_certificates_type;
	/**
	 * 银行卡名称
	 */
	private TextView act_tv_set_name;
	/**
	 * 网点名称
	 */
	private TextView act_tv_set_names;
	/**
	 * 名称
	 */
	private TextView act_tv_set_namess;
	/**
	 * 手机号码
	 */
	private TextView act_tv_set_phone;
	/**
	 * 是否认证
	 */
	private TextView set_tv_;
	/**
	 * 图标
	 */
	private ImageView set_iv__;
	private RelativeLayout act_rl_s, act_rl_ss, act_rl_sss, act_rl_ssss;
	private PreferenceUtils preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_set_individual);
		MyApplication.getInstance().addActivity(this);
		preferences = PreferenceUtils.getInstance(ActSetIndividual.this,
				PreferenceConstants.LOGIN_PREF);
		setupView();
		setupListener();
	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		act_iv_viewss = (ImageView) findViewById(R.id.act_iv_viewss);
		act_tv_set_Certificates_number = (TextView) findViewById(R.id.act_tv_set_Certificates_number);
		act_tv_set_certificates_type = (TextView) findViewById(R.id.act_tv_set_certificates_type);
		act_tv_set_name = (TextView) findViewById(R.id.act_tv_set_name);
		act_tv_set_names = (TextView) findViewById(R.id.act_tv_set_names);
		act_tv_set_namess = (TextView) findViewById(R.id.act_tv_set_namess);
		act_tv_set_phone = (TextView) findViewById(R.id.act_tv_set_phone);
		set_tv_ = (TextView) findViewById(R.id.set_tv_);
		set_iv__ = (ImageView) findViewById(R.id.set_iv__);
		act_rl_s = (RelativeLayout) findViewById(R.id.act_rl_s);
		act_rl_ss = (RelativeLayout) findViewById(R.id.act_rl_ss);
		act_rl_sss = (RelativeLayout) findViewById(R.id.act_rl_sss);
		act_rl_ssss = (RelativeLayout) findViewById(R.id.act_rl_ssss);
		act_tv_set_namess.setText(preferences.getString("userName", ""));
		act_tv_set_phone.setText(preferences.getString("phone_number", ""));
	}

	private void setupListener()
	{
		// TODO Auto-generated method stub
		act_rl_s.setOnClickListener(this);
		act_rl_ss.setOnClickListener(this);
		act_rl_sss.setOnClickListener(this);
		act_rl_ssss.setOnClickListener(this);
		act_iv_viewss.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.act_iv_viewss:
			finish();
			break;
		case R.id.act_rl_s:
			/**
			 * 证件
			 */
//			Intent intent = new Intent(this, ActCertificates.class);
//			startActivity(intent);
			break;
		case R.id.act_rl_ss:
			/**
			 * 银行
			 */
			Intent intent = new Intent(this, ActMyBankCard.class);
			startActivity(intent);
			break;
		case R.id.act_rl_sss:
			/**
			 * 联系人
			 */
			break;
		case R.id.act_rl_ssss:
			/**
			 * 特为认证
			 */
			break;
		default:
			break;
		}
	}
}

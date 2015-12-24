package com.soloman.org.cn.ui.mail_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.ui.ActCity;

/**
 * 通讯录信息展示
 * 
 * @author Mac
 * 
 */
public class ActMailText extends Activity implements OnClickListener
{
	private Bundle bundle;
	private TextView act_tv_mail_text_name;
	private TextView act_tv_mail_text_phone;
	private ImageView act_tv_common_address_return;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_mail_text);
		MyApplication.getInstance().addActivity(this);
		setupView();
	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		bundle = getIntent().getExtras();
		act_tv_common_address_return = (ImageView) findViewById(R.id.act_tv_common_address_return);
		act_tv_mail_text_name = (TextView) findViewById(R.id.act_tv_mail_text_name);
		act_tv_mail_text_phone = (TextView) findViewById(R.id.act_tv_mail_text_phone);
		act_tv_mail_text_name.setText(bundle.getString("name").toString());
		act_tv_mail_text_phone.setText(bundle.getString("number").toString());
		act_tv_mail_text_phone.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.act_tv_common_address_return:
			finish();
			break;
		case R.id.act_tv_mail_text_phone:
			// 获取启动这个activity的intent
			Intent intent = getIntent();
			Bundle b = new Bundle();
			b.putString("name", act_tv_mail_text_name.getText().toString());// 把数据塞入intent里面
			b.putString("number", act_tv_mail_text_phone.getText().toString());// 把数据塞入intent里面
			intent.putExtras(b);
			ActMailText.this.setResult(1, intent);// 跳转回原来的activity
			ActMailText.this.finish();// 结束当前activity
			break;
		default:
			break;
		}
	}
}

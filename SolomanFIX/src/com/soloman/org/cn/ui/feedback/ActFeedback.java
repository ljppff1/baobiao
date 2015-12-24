package com.soloman.org.cn.ui.feedback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.ActLogin;
import com.soloman.org.cn.utis.Netroid;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 意见反馈
 * 
 * @author Mac
 * 
 */
public class ActFeedback extends Activity
{
	private ImageView act_tv_common_address_return;
	private EditText editText1;
	private RelativeLayout act_rl_appoint;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_feedback);
		MyApplication.getInstance().addActivity(this);
		setupView();
		setupListener();
	}

	private void setupListener()
	{
		// TODO Auto-generated method stub
		act_tv_common_address_return.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
		act_rl_appoint.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Toast.makeText(ActFeedback.this, "提交成功", Toast.LENGTH_LONG)
						.show();
				finish();
				feedback();
			}
		});
	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		act_tv_common_address_return = (ImageView) findViewById(R.id.act_tv_common_address_return);
		editText1 = (EditText) findViewById(R.id.editText1);
		act_rl_appoint = (RelativeLayout) findViewById(R.id.act_rl_appoint);
	}

	private void feedback()
	{

		RequestParams requestParams = new RequestParams();
		requestParams.put("desc", editText1.getText().toString());
		HttpRestClient.postHttpHuaShangha(this, "feedbacks/create", "v2",
				requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
					}
				});

	}
}

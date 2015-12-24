package com.soloman.org.cn.ui.message;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.Message;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.utis.ExampleUtil;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.NetworkJudge;
import com.soloman.org.cn.utis.SwipeBackActivity;

/**
 * 通知详情
 * 
 * @author Mac
 * 
 */
public class ActMessageListDetails extends SwipeBackActivity
{
	private TextView act_message_title;
	private TextView act_message_created_at;
	private TextView act_message_content;
	private Message message;
	private ImageView act_tv_common_address_return;
	private Bundle bundle;
	private String content;
	private ArrayList<Message> lt;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_messages_list);
		MyApplication.getInstance().addActivity(this);
		setupView();
		// messageList();

	}

	/**
	 * 推送消息解析
	 */
	private void jpushJSONAnalytical()
	{
		// TODO Auto-generated method stub
		// String title = bundle
		// .getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
		// 推送过来的消息
		content = bundle.getString("type");
		JSONObject objj;
		if (!ExampleUtil.isEmpty(content))
		{
			try
			{
				objj = new JSONObject(content);
				if (null != objj && objj.length() > 0)
				{
					// Message me = new Message();
					if (objj.has("content"))
					{
						message.setContent(objj.getString("content"));
					} else
					{
						message.setContent(" ");
					}

					if (objj.has("created_at"))
					{
						String created = objj.getString("created_at");

						message.setCreated_at(created.substring(0,
								created.indexOf("+")));
					} else
					{
						message.setCreated_at(" ");
					}

					if (objj.has("has_read"))
					{

						message.setHas_read(objj.getString("has_read"));
					} else
					{
						message.setHas_read(" ");
					}

					if (objj.has("id"))
					{

						message.setId(objj.getInt("id"));
					} else
					{
						message.setId(0);
					}

					if (objj.has("title"))
					{

						message.setTitle(objj.getString("title"));
					} else
					{
						message.setTitle(" ");
					}

					if (objj.has("updated_at"))
					{

						message.setUpdated_at(objj.getString("updated_at"));
					} else
					{
						message.setUpdated_at(" ");
					}
					lt.add(message);
				}
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void setupView()
	{
		lt = new ArrayList<Message>();
		bundle = getIntent().getExtras();
		message = (Message) bundle.getSerializable("yi");

		if (message == null)
		{
			message = new Message();
			jpushJSONAnalytical();
		}
		act_message_title = (TextView) findViewById(R.id.act_message_title);
		act_message_created_at = (TextView) findViewById(R.id.act_message_created_at);
		act_message_content = (TextView) findViewById(R.id.act_message_content);
		act_tv_common_address_return = (ImageView) findViewById(R.id.act_tv_common_address_return);
		act_message_title.setText(message.getTitle());
		act_message_content.setText(message.getContent());
		act_message_created_at.setText(message.getCreated_at());
		act_tv_common_address_return.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/**
	 * 已读
	 */
	private void messageList()
	{
		// boolean is = false;
		// if (NetworkJudge.getNetWorkType(this) !=
		// NetworkJudge.NETWORKTYPE_INVALID)
		// {
		// is = true;
		// }
		RequestParams requestParams = new RequestParams();
		requestParams.put("message_id", message.getId() + "");
		HttpRestClient.postHttpHuaShangha(this, "messages/read", "v2",
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

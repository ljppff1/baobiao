package com.soloman.org.cn.ui.message;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.MessageListAdapter;
import com.soloman.org.cn.bean.Message;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.NetworkJudge;
import com.soloman.org.cn.utis.SwipeBackActivity;

/**
 * 消息列表
 * 
 * @author Mac
 * 
 */
public class ActMessageList extends SwipeBackActivity
{
	private TextView act_iv_indext;
	private ListView pop_listview_left;
	private ArrayList<Message> lt;
	private ImageView act_tv_common_address_return;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_message_list);
		MyApplication.getInstance().addActivity(this);
		setupView();
		messageList();
	}

	private void setupView()
	{
		act_iv_indext = (TextView) findViewById(R.id.act_iv_indext);
		act_tv_common_address_return = (ImageView) findViewById(R.id.act_tv_common_address_return);
		act_tv_common_address_return.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
		pop_listview_left = (ListView) findViewById(R.id.pop_listview_left);
		pop_listview_left.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				Intent intent = new Intent(ActMessageList.this,
						ActMessageListDetails.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("yi", lt.get(arg2));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private void messageList()
	{
//		boolean is = false;
//		if (NetworkJudge.getNetWorkType(this) != NetworkJudge.NETWORKTYPE_INVALID)
//		{
//			is = true;
//		}
		
		HttpRestClient.postHttpHuaShangha(this,
				"messages/index", "v2", null,
				new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						lt = new ArrayList<Message>();

						try
						{
							if (response.getJSONArray("message").toString()
									.equals("[]"))
							{
								// 可见
								act_iv_indext.setVisibility(View.VISIBLE);
							}
							JSONArray array = response.getJSONArray("message");
							JSONObject obj;
							for (int i = 0; i < array.length(); i++)
							{
								obj = array.getJSONObject(i);
								Message me = new Message();
								if (obj.has("content"))
								{
									me.setContent(obj.getString("content"));
								} else
								{
									me.setContent(" ");
								}

								if (obj.has("created_at"))
								{

									me.setCreated_at(obj.getString("created_at"));
								} else
								{
									me.setCreated_at(" ");
								}

								if (obj.has("has_read"))
								{

									me.setHas_read(obj.getString("has_read"));
								} else
								{
									me.setHas_read(" ");
								}

								if (obj.has("id"))
								{

									me.setId(obj.getInt("id"));
								} else
								{
									me.setCreated_at(" ");
								}

								if (obj.has("title"))
								{

									me.setTitle(obj.getString("title"));
								} else
								{
									me.setTitle(" ");
								}

								if (obj.has("updated_at"))
								{

									me.setUpdated_at(obj.getString("updated_at"));
								} else
								{
									me.setUpdated_at(" ");
								}
								lt.add(me);
							}
							MessageListAdapter mla = new MessageListAdapter(lt,
									ActMessageList.this);
							pop_listview_left.setAdapter(mla);
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
}

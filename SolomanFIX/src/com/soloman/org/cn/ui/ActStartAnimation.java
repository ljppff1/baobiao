package com.soloman.org.cn.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import cn.jpush.android.api.InstrumentedActivity;

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
import com.soloman.org.cn.ui.sliding.ActHosts;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.NetworkJudge;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;

/**
 * 启动动画
 * 
 * @author Mac
 * 
 */
public class ActStartAnimation extends InstrumentedActivity
{
	private PreferenceUtils preferences;
	private ArrayList<BobygyardKevel> lt;
	String[] dates;
	private Customer cr;
	private Update update;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_start_animation);
		MyApplication.getInstance().addActivity(this);
		preferences = PreferenceUtils.getInstance(ActStartAnimation.this,
				PreferenceConstants.LOGIN_PREF);
		// setStyleCustom();
		initBodyguardRequest();
	}

	/**
	 * 请求保镖类型
	 * 
	 * 由于主页使用的时间控件为第三方如果数据加载慢会出现异常 所以在此主页直接拿缓存
	 */
	private void initBodyguardRequest()
	{

		HttpRestClient.getHttpHuaShangha(this, "levels/index", "v2", null,
				new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						lt = new ArrayList<BobygyardKevel>();
						JSONObject obj;
						JSONArray array;
						try
						{
							obj = response.getJSONObject("data");
							array = obj.getJSONArray("levels");
							dates = new String[array.length()];
							for (int i = 0; i < array.length(); i++)
							{
								BobygyardKevel bk = new BobygyardKevel();
								JSONObject aFriend = array.getJSONObject(i);
								bk.setCreated_at(aFriend
										.getString("created_at"));
								bk.setEnable(aFriend.getBoolean("enable"));
								bk.setFlag(aFriend.getString("flag"));
								bk.setId(aFriend.getInt("id"));
								bk.setIndex(aFriend.getInt("index"));
								bk.setName(aFriend.getString("name"));
								dates[i] = aFriend.getString("name");
								bk.setPrice_day(aFriend.getInt("price_day"));
								bk.setPrice_hour(aFriend.getInt("price_hour"));
								bk.setPrice_month(aFriend.getInt("price_month"));
								bk.setUpdate_manager_id(aFriend
										.getString("update_manager_id"));
								bk.setUpdated_at(aFriend
										.getString("updated_at"));
								lt.add(bk);
							}
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						initCustomerRequest();
					}

					@Override
					public void onFailure(Throwable e, JSONArray errorResponse)
					{
						// TODO Auto-generated method stub
						super.onFailure(e, errorResponse);
						System.out.println("aaa");
					}
				});

	}

	/**
	 * 客户电话
	 */
	private void initCustomerRequest()
	{
		HttpRestClient.getHttpHuaShangha(this, "customer/get", "v1", null,
				new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						JSONObject obj = (JSONObject) response;
						JSONObject objs;
						JSONArray array;
						try
						{

							cr = new Customer();
							update = new Update();
							cr.setCustomer(obj.getString("customer"));
							array = obj.getJSONArray("cities");
							for (int i = 0; i < array.length(); i++)
							{
								cr.getcities().add(array.getString(i));
							}
							objs = obj.getJSONObject("version");
							update.setCurrent_version(objs
									.getDouble("current_version"));
							update.setDesc(objs.getString("desc"));
							update.setForce_upgrade(objs
									.getBoolean("force_upgrade"));
							update.setMin_version(objs.getString("min_version"));
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Start();
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

	public void Start()
	{
		new Thread()
		{
			public void run()
			{
				// try
				// {
				// Thread.sleep(1000);
				// } catch (InterruptedException e)
				// {
				// e.printStackTrace();
				// }
				if (preferences.getString("access_token", "") != "")
				{
					Intent intent = new Intent();
					Bundle bun = new Bundle();
					bun.putStringArray("dates", dates);
					bun.putSerializable("lt", lt);
					bun.putSerializable("cr", cr);
					bun.putSerializable("update", update);
					intent.setClass(ActStartAnimation.this, ActHosts.class);
					intent.putExtras(bun);
					startActivity(intent);
				} else
				{
					Intent intent = new Intent();
					Bundle bun = new Bundle();
					bun.putStringArray("dates", dates);
					bun.putSerializable("lt", lt);
					bun.putSerializable("cr", cr);
					bun.putSerializable("update", update);
					intent.setClass(ActStartAnimation.this, ActLogin.class);
					intent.putExtras(bun);
					startActivity(intent);
				}
				finish();
			}
		}.start();
	}

}

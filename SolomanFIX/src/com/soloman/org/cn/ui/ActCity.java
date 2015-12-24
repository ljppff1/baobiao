package com.soloman.org.cn.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.CityAdapter;
import com.soloman.org.cn.bean.Customer;

public class ActCity extends Activity
{
	private ListView act_lv_city;
	private ArrayList<String> lt;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_city);
		MyApplication.getInstance().addActivity(this);
		lt = (ArrayList<String>) getIntent().getExtras().getStringArrayList(
				"cr");
		setUpView();
		setUpListener();

	}

	private void setUpListener()
	{
		act_lv_city.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				if (arg2 < lt.size())
				{
					// 获取启动这个activity的intent
					Intent intent = getIntent();
					Bundle b = new Bundle();
					b.putString("City", lt.get(arg2).toString());// 把数据塞入intent里面
					intent.putExtras(b);
					ActCity.this.setResult(1, intent);// 跳转回原来的activity
					ActCity.this.finish();// 结束当前activity
				}
			}
		});
	}

	private void setUpView()
	{
		act_lv_city = (ListView) findViewById(R.id.act_lv_city);
		act_lv_city.setFooterDividersEnabled(false);
		act_lv_city.setHeaderDividersEnabled(false);
		act_lv_city.addFooterView(LayoutInflater.from(this).inflate(
				R.layout.item_city2, null));
		act_lv_city.setAdapter(new CityAdapter(lt, ActCity.this));

	}
}

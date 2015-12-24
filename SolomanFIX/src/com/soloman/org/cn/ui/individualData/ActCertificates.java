package com.soloman.org.cn.ui.individualData;

import android.os.Bundle;

import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.utis.SwipeBackActivity;
/**
 * 证件编辑
 * @author Mac
 *
 */
public class ActCertificates extends SwipeBackActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_certificates);
		MyApplication.getInstance().addActivity(this);
		
	}
}

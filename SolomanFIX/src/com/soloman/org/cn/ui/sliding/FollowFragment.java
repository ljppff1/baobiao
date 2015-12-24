package com.soloman.org.cn.ui.sliding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soloman.org.cn.R;

public class FollowFragment extends Fragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.follow_people, container, false);
		setupView(view);
		return view;
	}

	private void setupView(View v)
	{
		// TODO Auto-generated method stub
	}
}

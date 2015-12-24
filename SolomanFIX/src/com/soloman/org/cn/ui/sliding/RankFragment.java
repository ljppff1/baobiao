package com.soloman.org.cn.ui.sliding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soloman.org.cn.R;

public class RankFragment extends Fragment
{


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.rank_layout, container, false);
		setupView(view);
		return view;
	}

	private void setupView(View v)
	{
		// TODO Auto-generated method stub
	}
}

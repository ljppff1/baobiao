package com.soloman.org.cn.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;
import com.soloman.org.cn.R;
import com.soloman.org.cn.ui.indent.FraFormerIndent;
import com.soloman.org.cn.ui.indent.FraPresentIndent;

/**
 * 当前订单Activity
 * 
 * @author Mac
 * 
 */
public class FraCurrentlyOrder extends Fragment implements
		OnCheckedChangeListener
{

	private RadioButton rb_gchat, rb_discuss;

	private RadioGroup rg_toptablehost;

	ArrayList<Fragment> list = new ArrayList<Fragment>();

	private RadioButton currenRadio;// 当前的控件

	private JazzyViewPager jviewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fra_currently_order, container,
				false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setUpView(view);
	}

	private void setUpView(View v)
	{
		list.add(new FraPresentIndent());
		list.add(new FraFormerIndent());
		rb_gchat = (RadioButton) v.findViewById(R.id.rb_gchat);
		rb_discuss = (RadioButton) v.findViewById(R.id.rb_discuss);
		jviewPager = (JazzyViewPager) v.findViewById(R.id.download_jazzyvp);
		rg_toptablehost = (RadioGroup) v.findViewById(R.id.rg_toptablehost);
		rg_toptablehost.setOnCheckedChangeListener(this);
		currenRadio = rb_gchat;
		TransitionEffect effect = TransitionEffect.Standard;
		jviewPager.setTransitionEffect(effect);
		jviewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
		jviewPager.setPageMargin(0);
		jviewPager.setCurrentItem(0);
		jviewPager.setOnPageChangeListener(new MyOnPageChangeListener());

	}

	@Override
	public void onCheckedChanged(RadioGroup rootView, int checkedId)
	{
		// TODO Auto-generated method stub
		switch (checkedId)
		{
		case R.id.rb_gchat:
			if (currenRadio != rb_gchat)
			{
				jviewPager.setCurrentItem(0);
				currenRadio = rb_gchat;

			}

			break;
		case R.id.rb_discuss:
			if (currenRadio != rb_discuss)
			{
				jviewPager.setCurrentItem(1);
				currenRadio = rb_discuss;
			}
			break;
		default:
			break;
		}
	}

	public class MyAdapter extends FragmentStatePagerAdapter
	{
		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
		}

		public MyAdapter(FragmentManager fragmentManager)
		{
			super(fragmentManager);
		}

		@Override
		public int getCount()
		{
			return list.size();
		}

		@Override
		public Fragment getItem(int position)
		{
			return list.get(position);
		}
	}

	/**
	 * ViewPager滑动事件
	 * 
	 * @author Mac
	 * 
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener
	{

		public void onPageScrollStateChanged(int arg0)
		{

		}

		public void onPageScrolled(int arg0, float arg1, int arg2)
		{

		}

		public void onPageSelected(int position)
		{
			switch (position)
			{
			case 0:
				rb_gchat.setChecked(true);
				break;
			case 1:
				rb_discuss.setChecked(true);
				break;
			default:
				break;
			}
		}
	}
}

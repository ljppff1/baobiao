package com.soloman.org.cn.ui.task;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;
import com.soloman.org.cn.R;
import com.soloman.org.cn.ui.indent.FraFormerIndent;
import com.soloman.org.cn.ui.indent.FraPresentIndent;
import com.soloman.org.cn.ui.sliding.ActHosts;
import com.soloman.org.cn.ui.task.accept_task.FraTaskComplete;
import com.soloman.org.cn.ui.task.accept_task.FraTaskNoComplete;

/**
 * 已接任务
 * 
 * @author Mac
 * 
 */
@SuppressLint("ResourceAsColor")
public class FraAcceptTask extends Fragment implements OnClickListener
{
	private ArrayList<Fragment> list = new ArrayList<Fragment>();

	private JazzyViewPager jviewPager;
	private ActHosts host;
	/**
	 * 未完成
	 */
	private RelativeLayout fra_rl_task_s;
	/**
	 * 已完成
	 */
	private RelativeLayout fra_rl_task_ss;
	private ImageView fra_iv_viewss;
	/**
	 * 账户
	 */
	private TextView fra_tv_;
	private TextView fra_tv_task_s, fra_tv_task_sz, fra_tv_task_ss,
			fra_tv_task_ssz;
	private View fra_v_task_s, fra_v_task_ss;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.fra_accept_task, container, false);
		host = (ActHosts) getActivity();
		setupView(view);
		setListener();
		return view;
	}

	private void setupView(View v)
	{
		// TODO Auto-generated method stub
		list.add(new FraTaskNoComplete());
		list.add(new FraTaskComplete());
		fra_tv_task_s = (TextView) v.findViewById(R.id.fra_tv_task_s);
		fra_tv_task_ss = (TextView) v.findViewById(R.id.fra_tv_task_ss);
		fra_tv_task_sz = (TextView) v.findViewById(R.id.fra_tv_task_sz);
		fra_tv_task_ssz = (TextView) v.findViewById(R.id.fra_tv_task_ssz);
		fra_v_task_s = v.findViewById(R.id.fra_v_task_s);
		fra_v_task_ss = v.findViewById(R.id.fra_v_task_ss);
		jviewPager = (JazzyViewPager) v.findViewById(R.id.download_jazzyvp);
		fra_rl_task_s = (RelativeLayout) v.findViewById(R.id.fra_rl_task_s);
		fra_rl_task_ss = (RelativeLayout) v.findViewById(R.id.fra_rl_task_ss);
		fra_iv_viewss = (ImageView) v.findViewById(R.id.fra_iv_viewss);
		fra_tv_ = (TextView) v.findViewById(R.id.fra_tv_);

		TransitionEffect effect = TransitionEffect.Standard;
		jviewPager.setTransitionEffect(effect);
		jviewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
		jviewPager.setPageMargin(0);
		jviewPager.setOffscreenPageLimit(2); // 预加载 默认预加载数量为1
		jviewPager.setCurrentItem(0);
	}

	private void setListener()
	{
		// TODO Auto-generated method stub
		fra_rl_task_s.setOnClickListener(this);
		fra_rl_task_ss.setOnClickListener(this);
		fra_iv_viewss.setOnClickListener(this);
		fra_tv_.setOnClickListener(this);
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

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.fra_rl_task_s:
			// 未完成
			jviewPager.setCurrentItem(0);
			fra_tv_task_s.setVisibility(View.VISIBLE);
			fra_tv_task_sz.setVisibility(View.GONE);
			fra_v_task_s.setVisibility(View.VISIBLE);

			// 已完成
			fra_tv_task_ss.setVisibility(View.VISIBLE);
			fra_tv_task_ssz.setVisibility(View.GONE);
			fra_v_task_ss.setVisibility(View.GONE);
			break;
		case R.id.fra_rl_task_ss:
			// 已完成
			jviewPager.setCurrentItem(1);
			fra_tv_task_ss.setVisibility(View.GONE);
			fra_tv_task_ssz.setVisibility(View.VISIBLE);
			fra_v_task_ss.setVisibility(View.VISIBLE);

			// 未完成
			fra_tv_task_s.setVisibility(View.GONE);
			fra_tv_task_sz.setVisibility(View.VISIBLE);
			fra_v_task_s.setVisibility(View.GONE);
			break;
		case R.id.fra_iv_viewss:
			host.sm.toggle();
			break;
		case R.id.fra_tv_:
			// 账户
			break;

		default:
			break;
		}
	}

}

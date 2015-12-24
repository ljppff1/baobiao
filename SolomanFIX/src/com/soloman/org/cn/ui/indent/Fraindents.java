package com.soloman.org.cn.ui.indent;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;
import com.soloman.org.cn.R;
import com.soloman.org.cn.ui.indent.accept_indent.FraAllIndent;
import com.soloman.org.cn.ui.indent.accept_indent.FraAlreadyCancel;
import com.soloman.org.cn.ui.indent.accept_indent.FraAlreadyExecutive;
import com.soloman.org.cn.ui.indent.accept_indent.FraReadyComplete;
import com.soloman.org.cn.ui.indent.accept_indent.FraReadyExecutive;
import com.soloman.org.cn.ui.indent.accept_indent.FraReadyPay;

/**
 * 2.0订单
 * 
 * @author Mac
 * 
 */
public class Fraindents extends Fragment implements OnClickListener
{
	private ArrayList<Fragment> list = new ArrayList<Fragment>();

	private JazzyViewPager jviewPager;
	/**
	 * 全部
	 */
	private RelativeLayout fra_rl_task_s;
	private TextView fra_tv_task_s;
	private View fra_v_task_s;
	/**
	 * 待付款
	 */
	private RelativeLayout fra_rl_task_ss;
	private TextView fra_tv_task_ss;
	private View fra_v_task_ss;

	/**
	 * 待履行
	 */
	private RelativeLayout fra_rl_task_sss;
	private TextView fra_tv_task_sss;
	private View fra_v_task_sss;

	/**
	 * 履行中
	 */
	private RelativeLayout fra_rl_task_ssss;
	private TextView fra_tv_task_ssss;
	private View fra_v_task_ssss;
	/**
	 * 已完成
	 */
	private RelativeLayout fra_rl_task_sssss;
	private TextView fra_tv_task_sssss;
	private View fra_v_task_sssss;
	/**
	 * 已取消
	 */
	private RelativeLayout fra_rl_task_ssssss;
	private TextView fra_tv_task_ssssss;
	private View fra_v_task_ssssss;

	private RelativeLayout s;
	private TextView ss;
	private View sss;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fra_indents, container, false);
		setupView(view);
		setupListener();
		return view;
	}

	private void setupView(View v)
	{
		list.add(new FraAllIndent());
		list.add(new FraReadyPay());
		list.add(new FraReadyExecutive());
		list.add(new FraAlreadyExecutive());
		list.add(new FraReadyComplete());
		list.add(new FraAlreadyCancel());
		// TODO Auto-generated method stu
		fra_rl_task_s = (RelativeLayout) v.findViewById(R.id.fra_rl_task_s);
		fra_tv_task_s = (TextView) v.findViewById(R.id.fra_tv_task_s);
		fra_v_task_s = v.findViewById(R.id.fra_v_task_s);

		fra_rl_task_ss = (RelativeLayout) v.findViewById(R.id.fra_rl_task_ss);
		fra_tv_task_ss = (TextView) v.findViewById(R.id.fra_tv_task_ss);
		fra_v_task_ss = v.findViewById(R.id.fra_v_task_ss);

		fra_rl_task_sss = (RelativeLayout) v.findViewById(R.id.fra_rl_task_sss);
		fra_tv_task_sss = (TextView) v.findViewById(R.id.fra_tv_task_sss);
		fra_v_task_sss = v.findViewById(R.id.fra_v_task_sss);

		fra_rl_task_ssss = (RelativeLayout) v
				.findViewById(R.id.fra_rl_task_ssss);
		fra_tv_task_ssss = (TextView) v.findViewById(R.id.fra_tv_task_ssss);
		fra_v_task_ssss = v.findViewById(R.id.fra_v_task_ssss);

		fra_rl_task_sssss = (RelativeLayout) v
				.findViewById(R.id.fra_rl_task_sssss);
		fra_tv_task_sssss = (TextView) v.findViewById(R.id.fra_tv_task_sssss);
		fra_v_task_sssss = v.findViewById(R.id.fra_v_task_sssss);
		fra_rl_task_ssssss = (RelativeLayout) v
				.findViewById(R.id.fra_rl_task_ssssss);
		fra_tv_task_ssssss = (TextView) v.findViewById(R.id.fra_tv_task_ssssss);
		fra_v_task_ssssss = v.findViewById(R.id.fra_v_task_ssssss);
		s = fra_rl_task_s;
		ss = fra_tv_task_s;
		sss = fra_v_task_s;

		jviewPager = (JazzyViewPager) v.findViewById(R.id.download_jazzyvp);
		TransitionEffect effect = TransitionEffect.Standard;
		jviewPager.setTransitionEffect(effect);
		jviewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
		jviewPager.setPageMargin(0);
		jviewPager.setOffscreenPageLimit(5); // 预加载 默认预加载数量为1
		jviewPager.setCurrentItem(0);
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

	private void setupListener()
	{
		// TODO Auto-generated method stub
		fra_rl_task_s.setOnClickListener(this);
		fra_rl_task_ss.setOnClickListener(this);
		fra_rl_task_sss.setOnClickListener(this);
		fra_rl_task_ssss.setOnClickListener(this);
		fra_rl_task_sssss.setOnClickListener(this);
		fra_rl_task_ssssss.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.fra_rl_task_s:
			if (s != fra_rl_task_s)
			{

				ss.setTextColor(getActivity().getResources().getColor(
						R.color.black));
				sss.setVisibility(View.GONE);

				fra_tv_task_s.setTextColor(getActivity().getResources()
						.getColor(R.color.hong));
				fra_v_task_s.setVisibility(View.VISIBLE);

				s = fra_rl_task_s;
				ss = fra_tv_task_s;
				sss = fra_v_task_s;
				jviewPager.setCurrentItem(0);
			}
			break;
		case R.id.fra_rl_task_ss:

			if (s != fra_rl_task_ss)
			{
				ss.setTextColor(getActivity().getResources().getColor(
						R.color.black));
				sss.setVisibility(View.GONE);

				fra_tv_task_ss.setTextColor(getActivity().getResources()
						.getColor(R.color.hong));
				fra_v_task_ss.setVisibility(View.VISIBLE);

				s = fra_rl_task_ss;
				ss = fra_tv_task_ss;
				sss = fra_v_task_ss;
				jviewPager.setCurrentItem(1);
			}
			break;
		case R.id.fra_rl_task_sss:
			if (s != fra_rl_task_sss)
			{
				ss.setTextColor(getActivity().getResources().getColor(
						R.color.black));
				sss.setVisibility(View.GONE);

				fra_tv_task_sss.setTextColor(getActivity().getResources()
						.getColor(R.color.hong));
				fra_v_task_sss.setVisibility(View.VISIBLE);

				s = fra_rl_task_sss;
				ss = fra_tv_task_sss;
				sss = fra_v_task_sss;
				jviewPager.setCurrentItem(2);
			}
			break;
		case R.id.fra_rl_task_ssss:
			if (s != fra_rl_task_ssss)
			{
				ss.setTextColor(getActivity().getResources().getColor(
						R.color.black));
				sss.setVisibility(View.GONE);

				fra_tv_task_ssss.setTextColor(getActivity().getResources()
						.getColor(R.color.hong));
				fra_v_task_ssss.setVisibility(View.VISIBLE);

				s = fra_rl_task_ssss;
				ss = fra_tv_task_ssss;
				sss = fra_v_task_ssss;
				jviewPager.setCurrentItem(3);
			}
			break;
		case R.id.fra_rl_task_sssss:
			if (s != fra_rl_task_sssss)
			{
				ss.setTextColor(getActivity().getResources().getColor(
						R.color.black));
				sss.setVisibility(View.GONE);

				fra_tv_task_sssss.setTextColor(getActivity().getResources()
						.getColor(R.color.hong));
				fra_v_task_sssss.setVisibility(View.VISIBLE);

				s = fra_rl_task_sssss;
				ss = fra_tv_task_sssss;
				sss = fra_v_task_sssss;
				jviewPager.setCurrentItem(4);
			}
			break;
		case R.id.fra_rl_task_ssssss:
			if (s != fra_rl_task_ssssss)
			{
				ss.setTextColor(getActivity().getResources().getColor(
						R.color.black));
				sss.setVisibility(View.GONE);

				fra_tv_task_ssssss.setTextColor(getActivity().getResources()
						.getColor(R.color.hong));
				fra_v_task_ssssss.setVisibility(View.VISIBLE);

				s = fra_rl_task_ssssss;
				ss = fra_tv_task_ssssss;
				sss = fra_v_task_ssssss;
				jviewPager.setCurrentItem(5);
			}
			break;
		default:
			break;
		}
	}
}

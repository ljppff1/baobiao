package com.soloman.org.cn.ui.sliding;

import java.util.Arrays;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.soloman.org.cn.R;
import com.soloman.org.cn.ui.FraCurrentlyOrder;
import com.soloman.org.cn.ui.FraIndex;
import com.soloman.org.cn.ui.FraIndividualDate;
import com.soloman.org.cn.ui.discount.ActDiscount;
import com.soloman.org.cn.ui.indent.Fraindents;
import com.soloman.org.cn.ui.individualData.ActindividualData;
import com.soloman.org.cn.ui.map.FraMap;
import com.soloman.org.cn.ui.message.ActMessageList;
import com.soloman.org.cn.ui.more.FraMore;
import com.soloman.org.cn.ui.task.FraAcceptTask;
import com.soloman.org.cn.ui.task.FraTask;

public class LeftFragment extends Fragment
{
	static boolean is = false;
	private final static int MENU_NORMAL_ICONS[] =
	{ R.drawable.sliding_livehall_icon_normal,
			R.drawable.sliding_follow_icon_normal,
			R.drawable.sliding_rank_icon_normal };

	private final static int MENU_CHECKED_ICONS[] =
	{ R.drawable.sliding_livehall_icon_checked,
			R.drawable.sliding_follow_icon_checked,
			R.drawable.sliding_rank_icon_checked };

	public final static Class[] FRAGMENTS_CLASSES =
	{ MainHallFragment.class, FraTask.class, FraAcceptTask.class,
			Fraindents.class, RankFragment.class, RankFragment.class,
			RankFragment.class, FraMore.class, ActindividualData.class };

	private View[] mMenuLayouts;
	private ImageView[] mMenuIcons;
	private TextView[] mMenuTexts;

	private Bitmap mLoadingBitmap;
	private int mCurrentIndex = -1;
	int i = 0;
	ActHosts host;
	private RelativeLayout act_rl_individual;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.sliding_left, container, false);
		setupView(view);
		changeMenuByClass(FRAGMENTS_CLASSES[0]);
		return view;
	}

	private void setupView(View view)
	{
		// TODO Auto-generated method stub
		host = (ActHosts) getActivity();
		mMenuLayouts = new View[]
		{ view.findViewById(R.id.menu_livehall_layout),
				view.findViewById(R.id.menu_follow_layout),
				view.findViewById(R.id.menu_rank_layout),
				view.findViewById(R.id.menu_rank_layout_order),
				view.findViewById(R.id.menu_rank_layout_news),
				view.findViewById(R.id.menu_rank_layout_youhui),
				view.findViewById(R.id.menu_rank_layout_fenxiang),
				view.findViewById(R.id.menu_rank_layout_gengduo),
				view.findViewById(R.id.act_rl_individual) };
		mMenuLayouts[0].setBackgroundResource(R.drawable.all_bj);
		mMenuIcons = new ImageView[]
		{ (ImageView) view.findViewById(R.id.menu_livehall_icon),
				(ImageView) view.findViewById(R.id.menu_follow_icon),
				(ImageView) view.findViewById(R.id.menu_rank_icon) };
		mMenuTexts = new TextView[]
		{ (TextView) view.findViewById(R.id.menu_livehall_text),
				(TextView) view.findViewById(R.id.menu_follow_text),
				(TextView) view.findViewById(R.id.menu_rank_text), };
		// = (RelativeLayout) view
		// .findViewById(R.id.act_rl_individual);
		// act_rl_individual.setOnClickListener(new OnClickListener()
		// {
		//
		// @Override
		// public void onClick(View arg0)
		// {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent(getActivity(),
		// ActindividualData.class);
		// intent.putExtras(getArguments());
		// startActivity(intent);
		//
		// }
		// });
		for (int i = 0; i < mMenuLayouts.length; i++)
		{
			mMenuLayouts[i].setTag(i);
			mMenuLayouts[i].setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					int index = (Integer) v.getTag();
					changeMenuByIndex(index);
				}
			});
		}
	}

	/**
	 * 通过索引改变Menu
	 * 
	 * @param index
	 */
	@SuppressWarnings("unchecked")
	private void changeMenuByIndex(int index)
	{
		if (index == 4)
		{
			is = false;
			Intent intent = new Intent(getActivity(), ActMessageList.class);
			startActivity(intent);
		} else if (index == 5)
		{
			is = false;
			Intent intent = new Intent(getActivity(), ActDiscount.class);
			startActivity(intent);
		} else
		{
			is = true;
			if (index == 0 && MainHallFragment.state == 0)
			{
				host.sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			} else
			{

				host.sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			}

			Class<? extends Fragment> clazz = null;

			clazz = FRAGMENTS_CLASSES[index];
			host.switchCenterFragment(clazz);

		}
		if (mCurrentIndex != index && index != 8)
		{
			clearMenu();
			setMenuChecked(index);
		}
		mCurrentIndex = index;

	}

	/**
	 * 通过Fragment类改变menu
	 * 
	 * @param clazz
	 */
	public void changeMenuByClass(Class<? extends Fragment> clazz)
	{
		int index = Arrays.asList(FRAGMENTS_CLASSES).indexOf(clazz);
		if (index != -1)
		{
			changeMenuByIndex(index);

		}
	}

	private void clearMenu()
	{
		for (int i = 1; i <= mMenuLayouts.length; i++)
		{
			mMenuLayouts[i - 1].setBackgroundDrawable(null);
		}
	}

	private void setMenuChecked(int index)
	{
		mMenuLayouts[index].setBackgroundResource(R.drawable.all_bj);
	}

	@Override
	public void onDestroy()
	{
		if (mLoadingBitmap != null && !mLoadingBitmap.isRecycled())
		{
			mLoadingBitmap.recycle();
			mLoadingBitmap = null;
		}

		super.onDestroy();
	}
}

package com.soloman.org.cn.ui.sliding;

import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;

public class ActHosts extends SlidingFragmentActivity
{
	private Fragment mContent;
	Fragment mCurFragment;
	public SlidingMenu sm;
	private long mkeyTime;
	LeftFragment Left;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.responsive_content_frame);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		MyApplication.getInstance().addActivity(this);
		if (findViewById(R.id.menu_frame) == null)
		{
			setBehindContentView(R.layout.menu_frame);
			// getSlidingMenu().setSlidingEnabled(true);

		} else
		{
			View v = new View(this);
			setBehindContentView(v);
			// getSlidingMenu().setSlidingEnabled(false);
		}
		// replace是先清空堆栈中的Fragment在添加Fragment
		// getSupportFragmentManager().beginTransaction()
		// .replace(R.id.menu_frame, new LeftFragment()).commit();
		// add是吧Fragment添加到堆栈中
		Left = new LeftFragment();
		Left.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction()
				.add(R.id.menu_frame, Left).commit();
		sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.DIMEN_120PX);
		sm.setShadowWidthRes(R.dimen.DIMEN_30PX);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);
		if (savedInstanceState != null)
		{
			mCurFragment = getSupportFragmentManager().getFragment(
					savedInstanceState, "mCurContent");
		}
	}
/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			toggle();
		}
		return super.onOptionsItemSelected(item);
	}

*/	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent",
				mCurFragment);
	}

	/**
	 * slidingMenu中的内容Fragment切换(左侧菜单触发)
	 * 
	 * @param clazz
	 */
	public void switchCenterFragment(Class<? extends Fragment> clazz)
	{
		try
		{

			boolean isInit = false;
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			Fragment userFragment = fm.findFragmentByTag(clazz.getName());
			if (userFragment == null)
			{
				isInit = true;
				try
				{
					userFragment = clazz.newInstance();
					userFragment.setArguments(getIntent().getExtras());
				} catch (InstantiationException e)
				{
					e.printStackTrace();
				} catch (IllegalAccessException e)
				{
					e.printStackTrace();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			if (mCurFragment != null && mCurFragment != userFragment)
			{
				// 隐藏Fragment
				ft.hide(mCurFragment);
			}

			if (!userFragment.isAdded() && isInit)
			{
				// 添加Fragment
				ft.add(R.id.content_frame, userFragment, clazz.getName());
			} else
			{
				// 显示Fragment
				ft.show(userFragment);
			}

			ft.commitAllowingStateLoss();

			getSlidingMenu().showContent();
			mCurFragment = userFragment;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 清除FragmentManager中所有Fragment
	 */
	public void removeAllFragments()
	{
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		for (int i = 0; i < LeftFragment.FRAGMENTS_CLASSES.length; i++)
		{
			Fragment fragment = getSupportFragmentManager().findFragmentByTag(
					LeftFragment.FRAGMENTS_CLASSES[i].getName());
			if (fragment != null)
			{
				// if (fragment instanceof MainHallFragment)
				// {
				// return;
				// }
				ft.remove(fragment);
			}
		}
		ft.commitAllowingStateLoss();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if ((System.currentTimeMillis() - mkeyTime) > 2000)
			{
				mkeyTime = System.currentTimeMillis();
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
			} else
			{
				// 完全退出杀死进程
				// System.exit(0);
				// 关闭所有Activity
				MyApplication.getInstance().onTerminate();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}

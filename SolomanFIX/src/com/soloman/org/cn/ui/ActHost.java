package com.soloman.org.cn.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.BobygyardKevel;

/**
 * 碎片主页
 * 
 * @author Mac
 * 
 */
public class ActHost extends FragmentActivity implements
		OnCheckedChangeListener, OnClickListener
{
	static int i = 1;
	private ArrayList<BobygyardKevel> lt;
	private String[] data;
	private Fragment currenFrag;// 当前位置
	String[] dates;
	private RadioButton currenRadio;// 当前的控件

	private int currIndex = 0;//

	private int offset = 0;//
	//
	private int bmpW;//
	/**
	 * 主页
	 */
	private FraIndex fraIndex;
	/**
	 * 当前订单
	 */
	private FraCurrentlyOrder fraCurrentlyOrder;
	/**
	 * 个人资料
	 */
	private FraIndividualDate FraIndividualDate;

	private long mkeyTime;

	private String uname, upwd, uid, token, pwd;

	// 定义FragmentTabHost对象
	public FragmentTabHost mTabHost;

	// 定义一个布局
	private LayoutInflater layoutInflater;
	private FragmentManager manager;
	private boolean islogin;
	// 定义数组来存放Fragment界面
	public Fragment fragmentArray[] =
	{ new FraIndex(), new FraCurrentlyOrder(), new FraIndividualDate() };

	// 定义数组来存放按钮图片
	private int mImageViewArray[] =
	{ R.drawable.tab_home_btn, R.drawable.tab_friends_btn,
			R.drawable.tab_message_btn };

	// Tab选项卡的文字
	public String mTextviewArray[] =
	{ "首页", "消息", "联系人", "我的" };

	private RadioButton rb_gchat, rb_discuss, rb_discusss;
	private RelativeLayout rl_gchat, rl_discuss, rl_discusss;
	private RadioGroup rg_toptablehost;

	ArrayList<Fragment> list = new ArrayList<Fragment>();

	private JazzyViewPager jviewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_mainhost);
		MyApplication.getInstance().addActivity(this);
		setupView();
	}

	private void setupView()
	{
		list.add(fraIndex = new FraIndex());
		list.add(fraCurrentlyOrder = new FraCurrentlyOrder());
		list.add(FraIndividualDate = new FraIndividualDate());
		rb_gchat = (RadioButton) findViewById(R.id.rb_gchat);
		rb_gchat.setOnClickListener(this);
		rb_discuss = (RadioButton) findViewById(R.id.rb_discuss);
		rb_discuss.setOnClickListener(this);
		rb_discusss = (RadioButton) findViewById(R.id.rb_discusss);
		rb_discusss.setOnClickListener(this);

		rl_gchat = (RelativeLayout) findViewById(R.id.rl_gchat);
		rl_gchat.setOnClickListener(this);
		rl_discuss = (RelativeLayout) findViewById(R.id.rl_discuss);
		rl_discuss.setOnClickListener(this);
		rl_discusss = (RelativeLayout) findViewById(R.id.rl_discusss);
		rl_discusss.setOnClickListener(this);

		jviewPager = (JazzyViewPager) findViewById(R.id.download_jazzyvp);
		rg_toptablehost = (RadioGroup) findViewById(R.id.rg_toptablehost);
		rg_toptablehost.setOnCheckedChangeListener(this);
		currenRadio = rb_gchat;
		TransitionEffect effect = TransitionEffect.Standard;
		jviewPager.setTransitionEffect(effect);
		jviewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

		jviewPager.setPageMargin(0);
		jviewPager.setCurrentItem(0);
		jviewPager.setOffscreenPageLimit(2); // 预加载 默认预加载数量为1
		jviewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		fraIndex.setArguments(getIntent().getExtras());
		FraIndividualDate.setArguments(getIntent().getExtras());
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
		case R.id.rb_discusss:
			if (currenRadio != rb_discusss)
			{
				jviewPager.setCurrentItem(2);
				currenRadio = rb_discusss;
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
				rb_discuss.setChecked(false);
				rb_discusss.setChecked(false);
				break;
			case 1:
				rb_discuss.setChecked(true);
				rb_gchat.setChecked(false);
				rb_discusss.setChecked(false);
				break;
			case 2:
				rb_discusss.setChecked(true);
				rb_discuss.setChecked(false);
				rb_gchat.setChecked(false);
				break;
			default:
				break;
			}
		}
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
				//完全退出杀死进程
//				System.exit(0);
				//关闭所有Activity 
				MyApplication.getInstance().onTerminate();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View arg0)
	{
		switch (arg0.getId())
		{
		case R.id.rb_gchat:
			if (currenRadio != rb_gchat)
			{
				jviewPager.setCurrentItem(0);
				// currenRadio.setBackgroundResource(R.drawable.icon_home_h);
				currenRadio = rb_gchat;
				// currenRadio.setBackgroundResource(R.drawable.icon_home);
			}

			break;
		case R.id.rb_discuss:
			if (currenRadio != rb_discuss)
			{
				jviewPager.setCurrentItem(1);
				currenRadio = rb_discuss;
			}
			break;
		case R.id.rb_discusss:
			if (currenRadio != rb_discusss)
			{
				jviewPager.setCurrentItem(2);
				currenRadio = rb_discusss;
			}
			break;
		case R.id.rl_gchat:
			if (currenRadio != rb_gchat)
			{
				jviewPager.setCurrentItem(0);
				// currenRadio.setBackgroundResource(R.drawable.icon_home_h);
				currenRadio = rb_gchat;
				// currenRadio.setBackgroundResource(R.drawable.icon_home);
			}
			break;
		case R.id.rl_discuss:
			if (currenRadio != rb_discuss)
			{
				jviewPager.setCurrentItem(1);
				currenRadio = rb_discuss;
			}
			break;
		case R.id.rl_discusss:
			if (currenRadio != rb_discusss)
			{
				jviewPager.setCurrentItem(2);
				currenRadio = rb_discusss;
			}
			break;
		default:
			break;
		}
	}

}

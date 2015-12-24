package com.soloman.org.cn.ui.sliding;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.BobygyardKevel;
import com.soloman.org.cn.bean.Customer;
import com.soloman.org.cn.bean.Update;
import com.soloman.org.cn.ui.ActCity;
import com.soloman.org.cn.ui.appoint.ActAppoint;
import com.soloman.org.cn.ui.map.FraMap;
import com.soloman.org.cn.ui.map.FraMapList;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;

/**
 * 首页
 * 
 * @author Mac
 * 
 */
public class MainHallFragment extends Fragment implements OnClickListener
{
	private PreferenceUtils preferences;
	private ScrollView scrollView1;
	private LinearLayout index_ll_s;
	static int i = 1;
	public static ArrayList<BobygyardKevel> lt = null;
	private String[] data;
	private Fragment currenFrag;// 当前位置
	public static String[] dates;
	private RadioButton currenRadio;// 当前的控件

	private int currIndex = 0;//

	private int offset = 0;//
	//
	private int bmpW;//
	/**
	 * 地图
	 */
	private FraMap fraMap;
	/**
	 * 列表
	 */
	private FraMapList fraMapList;

	private long mkeyTime;

	private String uname, upwd, uid, token, pwd;

	// 定义FragmentTabHost对象
	public FragmentTabHost mTabHost;

	// 定义一个布局
	private LayoutInflater layoutInflater;
	private FragmentManager manager;
	private boolean islogin;
	/**
	 * 发现
	 */
	private LinearLayout fra_ll_index_found;

	/**
	 * 预约
	 */
	private RelativeLayout fra_individual_date_rlvs_;

	/**
	 * 地图
	 */
	private RelativeLayout fra_ll_index_map;
	private TextView fra_tv_index_map;
	private ImageView fra_iv_index_map;
	private ImageView fra_iv_index_maps;
	/**
	 * 预约城市
	 */
	private RelativeLayout act_rl_address;

	private TextView act_tv_address;
	private ImageView act_iv_viewss;

	private JazzyViewPager jviewPager;
	ArrayList<Fragment> list = new ArrayList<Fragment>();

	private static Customer cr;
	private Bundle bb;
	private AMap mMap;
	private MapView mapView;
	static int state = 0;
	private ActHosts host;
	private static Update update;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fra_indexs, container, false);

		// View view = inflater.inflate(R.layout.pager_viewpager, container,
		// false);
		setUpView(view, savedInstanceState);
		preferences = PreferenceUtils.getInstance(getActivity(),
				PreferenceConstants.LOGIN_PREF);
		try
		{
			dates = getArguments().getStringArray("dates");
			lt = (ArrayList<BobygyardKevel>) getArguments().getSerializable(
					"lt");
			cr = (Customer) getArguments().getSerializable("cr");
			update = (Update) getArguments().getSerializable("update");
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		// dates = getArguments().getStringArray("dates");
		// lt = (ArrayList<BobygyardKevel>)
		// getArguments().getSerializable("lt");
		// cr = (Customer) getArguments().getSerializable("cr");
		// update = (Update) getArguments().getSerializable("update");
		preferences.put("customer", cr.getCustomer());
		// 是否有新版本 trur为有
		preferences.put("force_upgrade", update.getForce_upgrade());
		// 版本号
		preferences.put("current_version", update.getCurrent_version());
		preferences.put("userName1", preferences.getString("userName", ""));
		preferences.put("phone_number1",
				preferences.getString("phone_number", ""));
		return view;
	}

	private void setUpView(View view, Bundle savedInstanceState)
	{
		scrollView1 = (ScrollView) view.findViewById(R.id.scrollView1);
		index_ll_s = (LinearLayout) view.findViewById(R.id.index_ll_s);
		host = (ActHosts) getActivity();
		View viewItem02 = LayoutInflater.from(getActivity()).inflate(
				R.layout.pager_item02, null);
		mapView = (MapView) viewItem02.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);

		// list.add(First=new FirstFragment());
		list.add(fraMap = new FraMap());
		list.add(fraMapList = new FraMapList());
		fra_ll_index_found = (LinearLayout) view
				.findViewById(R.id.fra_ll_index_found);
		fra_ll_index_found.setOnClickListener(this);
		fra_individual_date_rlvs_ = (RelativeLayout) view
				.findViewById(R.id.fra_individual_date_rlvs_);
		fra_individual_date_rlvs_.setOnClickListener(this);
		fra_ll_index_map = (RelativeLayout) view
				.findViewById(R.id.fra_ll_index_map);
		fra_ll_index_map.setOnClickListener(this);
		act_rl_address = (RelativeLayout) view
				.findViewById(R.id.act_rl_address);
		act_rl_address.setOnClickListener(this);
		fra_tv_index_map = (TextView) view.findViewById(R.id.fra_tv_index_map);
		fra_iv_index_map = (ImageView) view.findViewById(R.id.fra_iv_index_map);
		fra_iv_index_maps = (ImageView) view
				.findViewById(R.id.fra_iv_index_maps);
		act_iv_viewss = (ImageView) view.findViewById(R.id.act_iv_viewss);
		act_iv_viewss.setOnClickListener(this);
		act_tv_address = (TextView) view.findViewById(R.id.act_tv_address);
		jviewPager = (JazzyViewPager) view.findViewById(R.id.download_jazzyvp);
		TransitionEffect effect = TransitionEffect.Standard;
		jviewPager.setTransitionEffect(effect);
		jviewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

		jviewPager.setPageMargin(0);
		jviewPager.setCurrentItem(0);
		jviewPager.setOffscreenPageLimit(5); // 预加载 默认预加载数量为1
		jviewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		fraMapList.setArguments(getArguments());
		// fraMap.setArguments(getArguments());
		initMap();

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
			// jviewPager.getChildAt(position); 获得当前显示的实例
			state = position;
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

	@Override
	public void onClick(View v)
	{

		switch (v.getId())
		{
		case R.id.fra_ll_index_found:
			// currenRadio.setBackgroundResource(R.drawable.icon_home_h);
			break;
		case R.id.fra_individual_date_rlvs_:
			// 预约
			Intent intents = new Intent(getActivity(), ActAppoint.class);
			Bundle bundlea = new Bundle();
			bundlea.putStringArray("datas", dates);
			bundlea.putSerializable("lt", lt);
			intents.putExtras(bundlea);
			startActivity(intents);

			break;
		case R.id.fra_ll_index_map:
			if (fra_tv_index_map.getText().equals("列表"))
			{
				jviewPager.setCurrentItem(1);
				fra_tv_index_map.setText("地图");
				fra_iv_index_map.setVisibility(View.VISIBLE);
				fra_iv_index_maps.setVisibility(View.INVISIBLE);
				host.sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			} else
			{
				jviewPager.setCurrentItem(0);
				fra_tv_index_map.setText("列表");
				fra_iv_index_map.setVisibility(View.INVISIBLE);
				fra_iv_index_maps.setVisibility(View.VISIBLE);

				host.sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			}

			break;
		case R.id.act_iv_viewss:
			// MainActivity.mSlidingMenu.showMenu();
			host.sm.toggle();
			// host.removeAllFragments();

			break;
		case R.id.act_rl_address:
			// 预约城市
			Intent intent = new Intent(getActivity(), ActCity.class);
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("cr", cr.getcities());
			intent.putExtras(bundle);
			startActivityForResult(intent, 10);
			break;

		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1)
		{
			bb = data.getExtras();
			switch (requestCode)
			{
			case 10:
				act_tv_address.setText(bb.getString("City").toString());
				break;
			default:
				break;
			}
		}
	}

	public void postScrollTop()
	{

	}

	private void initMap()
	{
		if (mMap == null)
		{
			mMap = mapView.getMap();
		}

	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		new Thread(new ThreadShow()).start();
		mapView.onResume();
	}

	// 用来控制标题中间字体滑动
	class ThreadShow implements Runnable
	{
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			while (true)
			{
				try
				{
					// 判断高度,ScrollView的最大高度，就是屏幕的高度
					int off = index_ll_s.getMeasuredHeight()
							- scrollView1.getHeight();
					if (off > 0)
					{
						if (i == 0)
						{
							Thread.sleep(17);
							scrollView1.scrollBy(0, 2);
							// 当前位子 scrollView1.getScrollY()
							if (scrollView1.getScrollY() == off)
							{
								// Thread.currentThread().interrupt();
								Thread.sleep(5000);
								i = 1;
							}
						} else if (i == 1)
						{
							Thread.sleep(17);
							scrollView1.scrollBy(0, -2);
							if (scrollView1.getScrollY() == 0)
							{
								// Thread.currentThread().interrupt();
								Thread.sleep(5000);
								i = 0;
							}
						}
						// System.out.println("receive....");
					}
					// System.out.println("send...");
				} catch (Exception e)
				{
					e.printStackTrace();
					System.out.println("thread error...");
				}
			}
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onPause()
	{
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mapView.onDestroy();
	}
}

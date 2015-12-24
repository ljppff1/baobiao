package com.soloman.org.cn.ui;

import java.util.ArrayList;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.BobygyardKevel;
import com.soloman.org.cn.bean.Customer;
import com.soloman.org.cn.bean.Update;
import com.soloman.org.cn.ui.appoint.ActAppointHome;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;

/**
 * 主页
 * 
 * @author Mac
 * 
 */
public class FraIndex extends Fragment implements OnWheelChangedListener,
		OnClickListener
{
	private String people;
	private String type;
	private String day;
	private PreferenceUtils preferences;
	Bundle bb;
	private View rootView;// 缓存Fragment view
	private PopupWindow mPopupWindow;
	// 屏幕的width
	private int mScreenWidth;
	// 屏幕的height
	private int mScreenHeight;
	// PopupWindow的width
	private int mPopupWindowWidth;
	// PopupWindow的height
	private int mPopupWindowHeight;
	private int i = 0;
	private ArrayList<BobygyardKevel> lt;
	private Customer cr;
	private Update update;
	private WheelView id_province;
	private WheelView id_provinces;
	private WheelView id_provincess;
	private View index_vw_about;
	private ScrollView scrollView1;
	private LinearLayout index_ll_s;
	private ImageView act_Iv_views;
	private TextView act_tv_address;
	private TextView tv;
	private String[] dates;
	private String[] date = new String[]
	{ "1小时", "2小时", "3小时", "4小时", "5小时", "6小时", "7小时", "8小时", "1天",
			"2天", "3天", "4天", "5天", "6天", "7天", "8天", "9天", "10天", "11天",
			"12天", "13天", "14天", "15天", "16天", "17天", "18天", "19天", "20天",
			"21天", "22天", "23天", "24天", "25天", "26天", "27天", "28天", "29天",
			"30天", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月",
			"11月", "12月", "1年" };
	String[] data = new String[]
	{ "1特卫", "2特卫", "3特卫", "4特卫", "5特卫", "6特卫", "7特卫", "8特卫", "9特卫", "10特卫",
			"11特卫", "12特卫", "13特卫", "14特卫", "15特卫", "16特卫", "17特卫", "18特卫",
			"19特卫", "20特卫", "21特卫", "22特卫", "23特卫", "24特卫", "25特卫", "26特卫",
			"27特卫", "28特卫", "29特卫", "30特卫", "31特卫", "32特卫", "33特卫", "34特卫",
			"35特卫", "36特卫", "37特卫", "38特卫", "39特卫", "40特卫", "41特卫", "42特卫",
			"43特卫", "44特卫", "45特卫", "46特卫", "47特卫", "48特卫", "49特卫", "50特卫",
			"51特卫", "52特卫", "53特卫", "54特卫", "55特卫", "56特卫", "57特卫", "58特卫",
			"59特卫", "60特卫", "61特卫", "62特卫", "63特卫", "64特卫", "65特卫", "66特卫",
			"67特卫", "68特卫", "69特卫", "70特卫", "71特卫", "72特卫", "73特卫", "74特卫",
			"75特卫", "76特卫", "77特卫", "78特卫", "79特卫", "80特卫", "81特卫", "82特卫",
			"83特卫", "84特卫", "85特卫", "86特卫", "87特卫", "88特卫", "89特卫", "90特卫",
			"91特卫", "92特卫", "93特卫", "94特卫", "95特卫", "96特卫", "97特卫", "98特卫",
			"99特卫", };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		if (rootView == null)
		{
			rootView = inflater.inflate(R.layout.activity_maina, null);
		}
		// 缓存的rootView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null)
		{
			parent.removeView(rootView);
		}
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setupViews(view);
		preferences = PreferenceUtils.getInstance(getActivity(),
				PreferenceConstants.LOGIN_PREF);
		dates = getArguments().getStringArray("dates");
		cr = (Customer) getArguments().getSerializable("cr");
		update = (Update) getArguments().getSerializable("update");
		preferences.put("customer", cr.getCustomer());
		// 是否有新版本 trur为有
		preferences.put("force_upgrade", update.getForce_upgrade());
		// 版本号
		preferences.put("current_version", update.getCurrent_version());
		preferences.put("userName1",
				preferences.getString("userName", ""));
		preferences.put("phone_number1",
				preferences.getString("phone_number", ""));
		setUpListener();
		setUpData();
		// initBodyguardRequest();
		// initCustomerRequest();
	}

	private void setupViews(View v)
	{
		scrollView1 = (ScrollView) v.findViewById(R.id.scrollView1);
		index_ll_s = (LinearLayout) v.findViewById(R.id.index_ll_s);
		id_province = (WheelView) v.findViewById(R.id.id_province);
		id_provinces = (WheelView) v.findViewById(R.id.id_provinces);
		id_provincess = (WheelView) v.findViewById(R.id.id_provincess);
		index_vw_about = (View) v.findViewById(R.id.index_vw_about);
		act_Iv_views = (ImageView) v.findViewById(R.id.act_Iv_views);
		act_tv_address = (TextView) v.findViewById(R.id.act_tv_address);
		id_province.setVisibleItems(7);
		id_provinces.setVisibleItems(7);
		id_provincess.setVisibleItems(7);
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		new Thread(new ThreadShow()).start();
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

	private void setUpListener()
	{
		// 添加change事件
		id_province.addChangingListener(this);
		// 添加change事件
		id_provinces.addChangingListener(this);
		// 添加change事件
		id_provincess.addChangingListener(this);
		act_Iv_views.setOnClickListener(this);
		index_vw_about.setOnClickListener(this);
		act_tv_address.setOnClickListener(this);
	}

	private void setUpData()
	{
		id_province.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(),
				date));
		id_provinces.setViewAdapter(new ArrayWheelAdapter<String>(
				getActivity(), dates));
		id_provincess.setViewAdapter(new ArrayWheelAdapter<String>(
				getActivity(), data));
		// // 设置可见条目数量
		// // updateCities();
		// // updateAreas();
	}

	/*
	 * 获取PopupWindow实例
	 */
	private void getPopupWindowInstance()
	{
		if (null != mPopupWindow)
		{
			mPopupWindow.dismiss();
			return;
		} else
		{
			initPopuptWindow();
		}
	}

	/*
	 * 创建PopupWindow
	 */
	private void initPopuptWindow()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View popupWindow = layoutInflater.inflate(R.layout.act_phone, null);
		popupWindow.findViewById(R.id.phone_ll).setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						mPopupWindow.dismiss();
					}
				});
		popupWindow.findViewById(R.id.phone_ll_cancel).setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						mPopupWindow.dismiss();
					}
				});

		tv = (TextView) popupWindow.findViewById(R.id.city_tv__);
		tv.setText(cr.getCustomer().toString());
		tv.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

			}
		});
		popupWindow.findViewById(R.id.phone_ll_).setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						try
						{
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + tv.getText().toString()));
							startActivity(intent);
						} catch (Exception e)
						{
							// TODO: handle exception
							Toast.makeText(getActivity(),"无有效手机卡", Toast.LENGTH_LONG).show();
						}
					}
				});
		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height
		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		// 需要设置一下此参数，点击外边可消失
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		mPopupWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		mPopupWindow.setFocusable(true);
		// 获取屏幕和PopupWindow的width和height
		mScreenWidth = getActivity().getWindowManager().getDefaultDisplay()
				.getWidth();
		mScreenWidth = getActivity().getWindowManager().getDefaultDisplay()
				.getHeight();
		mPopupWindowWidth = mPopupWindow.getWidth();
		mPopupWindowHeight = mPopupWindow.getHeight();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue)
	{
		if (wheel == id_province)
		{
			// 天
			int pCurrent = id_province.getCurrentItem();
			people = date[pCurrent];
			System.out.println("aaaaaa " + people + "      "
					+ id_province.getCurrentItem());
		} else if (wheel == id_provinces)
		{
			// 类型
			int pCurrent = id_provinces.getCurrentItem();
			type = dates[pCurrent];
			System.out.println("aaaaaa " + type);
		} else if (wheel == id_provincess)
		{
			// 人数
			int pCurrent = id_provincess.getCurrentItem();
			day = data[pCurrent];
			System.out.println("aaaaaa " + day);
		}

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.index_vw_about:
			// 天
			int pCurrent = id_province.getCurrentItem();
			// 类型
			int pCurrents = id_provinces.getCurrentItem();
			// 人数
			int pCurrentss = id_provincess.getCurrentItem();
			// 预约
			Intent intents = new Intent(getActivity(), ActAppointHome.class);
			Bundle bundles = new Bundle();
			bundles.putString("pCurrent", date[pCurrent]);
			bundles.putString("pCurrents", dates[pCurrents]);
			bundles.putString("pCurrentss", data[pCurrentss]);
			bundles.putSerializable("cr", cr);
			intents.putExtras(bundles);
			startActivity(intents);
			break;
		case R.id.act_Iv_views:
			getPopupWindowInstance();
			mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			break;
		case R.id.act_tv_address:
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
}

package com.soloman.org.cn.ui.appoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.BobygyardKevel;
import com.soloman.org.cn.view.percent.PercentRelativeLayout;

public class ActAppoint extends Activity implements OnClickListener
{
	Bundle bundlez;
	int typeid;
	private ArrayList<BobygyardKevel> lt;
	int number;
	String date2;
	int level = -1;
	String name = null;
	private String day;
	int aa = 100;
	private int days;
	long daysa;
	long hour;
	long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
	long nh = 1000 * 60 * 60;// 一小时的毫秒数
	long nm = 1000 * 60;// 一分钟的毫秒数
	long ns = 1000;// 一秒钟的毫秒数
	int YEAR;
	int MONTH;
	int DAY_OF_MONTH;
	int mHours;
	int mMinutes;
	private boolean isssa = true;
	private String[] mDateDisplayValues = new String[7];
	private int mHour, mMinute, mDays;
	private TextView item_tv_;
	private TextView tv;
	private NumberPicker mDateSpinner;

	private NumberPicker mHourSpinner;

	private NumberPicker mMinuteSpinner;
	private Calendar mDate;
	private TextView item_tv_determine;
	private TextView item_tv_cancel;
	private TextView tvss;
	private PopupWindow mPopupWindow;
	// 屏幕的width
	private int mScreenWidth;
	// 屏幕的height
	private int mScreenHeight;
	// PopupWindow的width
	private int mPopupWindowWidth;
	// PopupWindow的height
	private int mPopupWindowHeight;
	private String[] datess;
	private String[] date = new String[]
	{ "1小时", "2小时", "3小时", "4小时", "5小时", "6小时", "7小时", "8小时", "1天", "2天", "3天",
			"4天", "5天", "6天", "7天", "8天", "9天", "10天", "11天", "12天", "13天",
			"14天", "15天", "16天", "17天", "18天", "19天", "20天", "21天", "22天",
			"23天", "24天", "25天", "26天", "27天", "28天", "29天", "30天", "1月", "2月",
			"3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月", "1年" };
	String[] data = new String[]
	{ "1个特卫", "2个特卫", "3个特卫", "4个特卫", "5个特卫", "6个特卫", "7个特卫", "8个特卫", "9个特卫",
			"10特卫", "11个特卫", "12个特卫", "13个特卫", "14个特卫", "15个特卫", "16个特卫",
			"17个特卫", "18个特卫", "19个特卫", "20个特卫", "21个特卫", "22个特卫", "23个特卫",
			"24个特卫", "25个特卫", "26个特卫", "27个特卫", "28个特卫", "29个特卫", "30个特卫",
			"31个特卫", "32个特卫", "33个特卫", "34个特卫", "35个特卫", "36个特卫", "37个特卫",
			"38个特卫", "39个特卫", "40个特卫", "41个特卫", "42个特卫", "43个特卫", "44个特卫",
			"45个特卫", "46个特卫", "47个特卫", "48个特卫", "49个特卫", "50个特卫", "51个特卫",
			"52个特卫", "53个特卫", "54个特卫", "55个特卫", "56个特卫", "57个特卫", "58个特卫",
			"59个特卫", "60个特卫", "61个特卫", "62个特卫", "63个特卫", "64个特卫", "65个特卫",
			"66个特卫", "67个特卫", "68个特卫", "69个特卫", "70个特卫", "71个特卫", "72个特卫",
			"73个特卫", "74个特卫", "75个特卫", "76个特卫", "77个特卫", "78个特卫", "79个特卫",
			"80个特卫", "81个特卫", "82个特卫", "83个特卫", "84个特卫", "85个特卫", "86个特卫",
			"87个特卫", "88个特卫", "89个特卫", "90个特卫", "91个特卫", "92个特卫", "93个特卫",
			"94个特卫", "95个特卫", "96个特卫", "97个特卫", "98个特卫", "99个特卫", };
	String[] special = new String[]
	{ "一级勋章", "二级勋章", "三级勋章", "四级勋章", "五级勋章" };
	// 服务类型
	private LinearLayout act_ll_appoint_type;
	private TextView act_tv_appoint_type;
	// 服务时长
	private LinearLayout act_ll_appoint_date;
	private TextView act_tv_appoint_date;
	// 开始时间
	private LinearLayout act_ll_appoint_date2;
	private TextView act_tv_appoint_date2;
	// 特卫人数
	private PercentRelativeLayout act_rl_appoint_number;
	private TextView act_tv_appoint_number;
	// 特卫级别
	private PercentRelativeLayout act_rl_appoint_level;
	private TextView act_tv_appoint_level;
	private TextView fra_tv_;
	private ImageView fra_iv_viewss;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_appoint);
		setupView();
		setupListener();
		try
		{
			bundlez = getIntent().getExtras();
			lt = (ArrayList<BobygyardKevel>) bundlez.getSerializable("lt");
			datess = bundlez.getStringArray("datas");
			// bundle.putString("type",
			// act_tv_appoint_type.getText().toString());
			// bundle.putInt("typeid", typeid);
			// bundle.putString("date",
			// act_tv_appoint_date.getText().toString());
			// bundle.putString("date2", date2);
			// bundle.putString("number", act_tv_appoint_number.getText()
			// .toString());
			// bundle.putInt("number2", number);
			// bundle.putString("levelname", act_tv_appoint_level.getText()
			// .toString());
			// bundle.putInt("level", level + 1);
			if (bundlez.getString("type") != null
					&& bundlez.getString("date") != null
					&& bundlez.getString("date22") != null
					&& bundlez.getString("number") != null
					&& bundlez.getString("levelname") != null)
			{
				act_tv_appoint_type.setText(bundlez.getString("type"));
				act_tv_appoint_date.setText(bundlez.getString("date"));
				act_tv_appoint_date2.setText(bundlez.getString("date22"));
				act_tv_appoint_number.setText(bundlez.getString("number"));
				act_tv_appoint_level.setText(bundlez.getString("levelname"));

			}

		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		fra_iv_viewss = (ImageView) findViewById(R.id.fra_iv_viewss);
		fra_tv_ = (TextView) findViewById(R.id.fra_tv_);
		act_ll_appoint_type = (LinearLayout) findViewById(R.id.act_ll_appoint_type);
		act_tv_appoint_type = (TextView) findViewById(R.id.act_tv_appoint_type);

		act_ll_appoint_date = (LinearLayout) findViewById(R.id.act_ll_appoint_date);
		act_tv_appoint_date = (TextView) findViewById(R.id.act_tv_appoint_date);

		act_ll_appoint_date2 = (LinearLayout) findViewById(R.id.act_ll_appoint_date2);
		act_tv_appoint_date2 = (TextView) findViewById(R.id.act_tv_appoint_date2);

		act_rl_appoint_number = (PercentRelativeLayout) findViewById(R.id.act_rl_appoint_number);
		act_tv_appoint_number = (TextView) findViewById(R.id.act_tv_appoint_number);

		act_rl_appoint_level = (PercentRelativeLayout) findViewById(R.id.act_rl_appoint_level);
		act_tv_appoint_level = (TextView) findViewById(R.id.act_tv_appoint_level);
	}

	private void setupListener()
	{
		// TODO Auto-generated method stub
		act_ll_appoint_type.setOnClickListener(this);
		act_ll_appoint_date.setOnClickListener(this);
		act_ll_appoint_date2.setOnClickListener(this);
		act_rl_appoint_number.setOnClickListener(this);
		act_rl_appoint_level.setOnClickListener(this);
		fra_iv_viewss.setOnClickListener(this);
		fra_tv_.setOnClickListener(this);
	}

	int i = 0;

	/*
	 * 创建PopupWindow
	 */
	private void initPopuptWindow(int i)
	{
		name = null;
		this.i = -1;
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.item_rl_date, null);
		mDateSpinner = (NumberPicker) popupWindow.findViewById(R.id.np_date);
		mHourSpinner = (NumberPicker) popupWindow.findViewById(R.id.np_hour);
		tvss = (TextView) popupWindow.findViewById(R.id.tvss);
		mMinuteSpinner = (NumberPicker) popupWindow
				.findViewById(R.id.np_minute);
		item_tv_determine = (TextView) popupWindow
				.findViewById(R.id.item_tv_determine);
		item_tv_cancel = (TextView) popupWindow
				.findViewById(R.id.item_tv_cancel);
		if (i == 1)
		{
			mHourSpinner.setVisibility(View.GONE);
			tvss.setVisibility(View.GONE);
			mMinuteSpinner.setVisibility(View.GONE);
			mDateSpinner.setDisplayedValues(datess);
			mDateSpinner.setMinValue(0);
			mDateSpinner.setMaxValue(datess.length - 1);
			item_tv_determine.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					name = datess[mDateSpinner.getValue()];
					act_tv_appoint_type.setText(name);
					typeid = lt.get(mDateSpinner.getValue()).getId();
					mPopupWindow.dismiss();
				}
			});
			// mDateSpinner.setOnValueChangedListener(mOnDateChangedListener);

		} else if (i == 2)
		{
			mHourSpinner.setVisibility(View.GONE);
			tvss.setVisibility(View.GONE);
			mMinuteSpinner.setVisibility(View.GONE);
			mDateSpinner.setDisplayedValues(date);
			mDateSpinner.setMinValue(0);
			mDateSpinner.setMaxValue(date.length - 1);
			item_tv_determine.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					name = date[mDateSpinner.getValue()];
					act_tv_appoint_date.setText(name);
					mPopupWindow.dismiss();
				}
			});
			// mDateSpinner.setOnValueChangedListener(mOnDateChangedListener);
		} else if (i == 3)
		{
			mHourSpinner.setVisibility(View.VISIBLE);
			tvss.setVisibility(View.VISIBLE);
			mMinuteSpinner.setVisibility(View.VISIBLE);

			item_tv_determine.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Calendar cals = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
					cals.setTimeInMillis(System.currentTimeMillis());
					// System.out.println(cals.get(Calendar.YEAR) + " 年");
					// System.out.println(cals.get(Calendar.MONTH) + 1 + " 月");
					// System.out.println(cals.get(Calendar.DAY_OF_MONTH) +
					// " 日");
					// System.out.println(cals.get(Calendar.HOUR_OF_DAY) +
					// " 时");
					// System.out.println(cals.get(Calendar.MINUTE) + " 分");

					YEAR = mDate.get(mDate.YEAR);
					MONTH = (mDate.get(Calendar.MONTH) + 1);
					DAY_OF_MONTH = mDate.get(mDate.DAY_OF_MONTH);
					mHours = mHour;
					mMinutes = mMinute;
					// 当前时间
					String dates = cals.get(Calendar.YEAR) + "-"
							+ (cals.get(Calendar.MONTH) + 1) + "-"
							+ cals.get(Calendar.DAY_OF_MONTH) + " "
							+ cals.get(Calendar.HOUR_OF_DAY) + ":"
							+ cals.get(Calendar.MINUTE);
					// 开始时间
					String datess = YEAR + "-" + MONTH + "-" + DAY_OF_MONTH
							+ " " + mHours + ":" + mMinutes;

					// String s1="2008-01-25 09:12:09";
					// String s2="2008-01-29 09:12:11";
					// java.text.DateFormat df=new
					// java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.text.DateFormat df = new java.text.SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					java.util.Calendar c1 = java.util.Calendar.getInstance();
					java.util.Calendar c2 = java.util.Calendar.getInstance();
					try
					{
						// ···························计算时间差....................
						// 当前时间
						Date d1 = df.parse(dates);
						// 开始时间
						Date d2 = df.parse(datess);
						long diff = d2.getTime() - d1.getTime();
						hour = diff % nd / nh;// 计算时间差多少小时
						daysa = diff / nd;// 计算差多少天

						// ···························计算时间差....................
						c1.setTime(df.parse(dates));
						c2.setTime(df.parse(datess));
					} catch (java.text.ParseException e)
					{
						System.err.println("格式不正确");
					}

					if (hour >= 2 || daysa > 0)
					{
						// int result = c1.compareTo(c2);
						// if (result == 0)
						// {
						// System.out.println("c1相等c2");
						// } else if (result < 0)
						// {
						// System.out.println("c1小于c2");
						// } else
						// {
						// Toast.makeText(ActAppointHome.this, "开始时间不得小于当前时间",
						// Toast.LENGTH_LONG).show();
						// System.out.println("c1大于c2");
						// return;
						// }

						days = mDate.get(Calendar.DAY_OF_WEEK);
						String todays = null;

						if (days == 2)
						{
							todays = "周一";
						} else if (days == 3)
						{
							todays = "周二";
						} else if (days == 4)
						{
							todays = "周三";
						} else if (days == 5)
						{
							todays = "周四";
						} else if (days == 6)
						{
							todays = "周五";
						} else if (days == 7)
						{
							todays = "周六";
						} else if (days == 1)
						{
							todays = "周日";
						}
						// 开始时间
						act_tv_appoint_date2.setText("开始："
								+ mDate.get(mDate.YEAR) + "-"
								+ (mDate.get(Calendar.MONTH) + 1) + "-"
								+ mDate.get(mDate.DAY_OF_MONTH) + " "
								+ todays.toString() + " " + mHour + ":"
								+ mMinute);
						date2 = mDate.get(mDate.YEAR) + "-"
								+ (mDate.get(Calendar.MONTH) + 1) + "-"
								+ mDate.get(mDate.DAY_OF_MONTH) + " " + mHour
								+ ":" + mMinute;
						System.out.println(mDate.get(mDate.DAY_OF_MONTH)
								+ "mimimi");
						istrue();
						if (aa == 1)
							mDays = mDate.get(mDate.DAY_OF_MONTH) + aa;
						else
							mDays = mDate.get(mDate.DAY_OF_MONTH);
						System.out.println(mDate.get(mDate.DAY_OF_MONTH)
								+ "mimimi");
						// 结束时间
						// act_tv_appoint_homesws.setText("结束："
						// + mDate.get(mDate.YEAR)
						// + "-"
						// + (mDate.get(Calendar.MONTH) + 1)
						// + "-"
						// + mDays
						// + " "
						// + getWeek(mDate.get(mDate.YEAR) + "-"
						// + (mDate.get(Calendar.MONTH) + 1) + "-"
						// + mDate.get(mDate.DAY_OF_MONTH)) + " "
						// + mHour + ":" + mMinute);
						// act_tv_appoint_homesw.setVisibility(View.INVISIBLE);
						// act_tv_appoint_homeswss.setVisibility(View.VISIBLE);
						// act_tv_appoint_homesws.setVisibility(View.VISIBLE);
						mPopupWindow.dismiss();
						aa = 0;
					} else
					{

						Toast.makeText(ActAppoint.this, "开始时间请在2小时后",
								Toast.LENGTH_LONG).show();
					}

				}

			});
			item_tv_ = (TextView) popupWindow.findViewById(R.id.item_tv_);
			mDate = Calendar.getInstance();
			mDate.setTimeInMillis(System.currentTimeMillis());
			mHour = mDate.get(Calendar.HOUR_OF_DAY);
			mMinute = mDate.get(Calendar.MINUTE);
			mDays = mDate.get(Calendar.DAY_OF_MONTH);
			System.out.println(mDays);
			mDateSpinner = (NumberPicker) popupWindow
					.findViewById(R.id.np_date);
			mDateSpinner.setMaxValue(6);
			mDateSpinner.setMinValue(0);
			updateDateControl();
			mDateSpinner.setOnValueChangedListener(mOnDateChangedListener);

			mHourSpinner = (NumberPicker) popupWindow
					.findViewById(R.id.np_hour);
			mHourSpinner.setMaxValue(23);
			mHourSpinner.setMinValue(0);
			mHourSpinner.setValue(mHour);
			mHourSpinner.setOnValueChangedListener(mOnHourChangedListener);

			mMinuteSpinner = (NumberPicker) popupWindow
					.findViewById(R.id.np_minute);
			mMinuteSpinner.setMaxValue(59);
			mMinuteSpinner.setMinValue(0);
			mMinuteSpinner.setValue(mMinute);
			mMinuteSpinner.setOnValueChangedListener(mOnMinuteChangedListener);
		} else if (i == 4)
		{
			mHourSpinner.setVisibility(View.GONE);
			tvss.setVisibility(View.GONE);
			mMinuteSpinner.setVisibility(View.GONE);
			mDateSpinner.setDisplayedValues(data);
			mDateSpinner.setMinValue(0);
			mDateSpinner.setMaxValue(data.length - 1);
			item_tv_determine.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub

					name = data[mDateSpinner.getValue()];
					number = mDateSpinner.getValue();
					act_tv_appoint_number.setText(name);
					mPopupWindow.dismiss();
				}
			});
			// mDateSpinner.setOnValueChangedListener(mOnDateChangedListener);
		} else if (i == 5)
		{
			mHourSpinner.setVisibility(View.GONE);
			tvss.setVisibility(View.GONE);
			mMinuteSpinner.setVisibility(View.GONE);
			mDateSpinner.setDisplayedValues(special);
			mDateSpinner.setMinValue(0);
			mDateSpinner.setMaxValue(special.length - 1);
			item_tv_determine.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					name = special[mDateSpinner.getValue()];
					level = mDateSpinner.getValue();
					act_tv_appoint_level.setText(name);
					mPopupWindow.dismiss();
				}
			});
			// mDateSpinner.setOnValueChangedListener(mOnDateChangedListener);
		}
		item_tv_cancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				mPopupWindow.dismiss();
			}
		});
		// 设置不可编辑
		mMinuteSpinner
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		mHourSpinner
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		mDateSpinner
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height
		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// 需要设置一下此参数，点击外边可消失
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		mPopupWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		mPopupWindow.setFocusable(true);
		// 获取屏幕和PopupWindow的width和height
		mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
		mScreenWidth = getWindowManager().getDefaultDisplay().getHeight();
		mPopupWindowWidth = mPopupWindow.getWidth();
		mPopupWindowHeight = mPopupWindow.getHeight();
	}

	private void updateDateControl()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(mDate.getTimeInMillis());
		cal.add(Calendar.DAY_OF_YEAR, -7 / 2 - 1);
		mDateSpinner.setDisplayedValues(null);
		for (int i = 0; i < 7; ++i)
		{
			cal.add(Calendar.DAY_OF_YEAR, 1);
			mDateDisplayValues[i] = (String) DateFormat.format("MM.dd EEEE",
					cal);
		}
		mDateSpinner.setDisplayedValues(mDateDisplayValues);
		mDateSpinner.setValue(7 / 2);
		mDateSpinner.invalidate();
	}

	private NumberPicker.OnValueChangeListener mOnDateChangedListener = new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{

			mDate.add(Calendar.DAY_OF_MONTH, newVal - oldVal);
			updateDateControl();
			onDateTimeChanged();
		}
	};
	private NumberPicker.OnValueChangeListener mOnHourChangedListener = new OnValueChangeListener()
	{
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
			mHour = mHourSpinner.getValue();
			onDateTimeChanged();
		}
	};

	private NumberPicker.OnValueChangeListener mOnMinuteChangedListener = new OnValueChangeListener()
	{
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
			mMinute = mMinuteSpinner.getValue();
			onDateTimeChanged();
		}
	};

	private void onDateTimeChanged()
	{
		handler.sendEmptyMessage(1);
	}

	Handler handler = new Handler()
	{
		int i = 0;

		public void handleMessage(Message msg)
		{
			if (msg.what == 1)
			{
				item_tv_.setText(mDate.get(Calendar.YEAR) + "年"
						+ (mDate.get(Calendar.MONTH) + 1) + "月"
						+ mDate.get(Calendar.DAY_OF_MONTH) + "日" + mHour + "时"
						+ mMinute + "分");
			}
		};
	};

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		name = null;
		switch (v.getId())
		{
		case R.id.act_ll_appoint_type:
			// 服务类型
			initPopuptWindow(1);
			mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			break;
		case R.id.act_ll_appoint_date:
			// 服务时长
			initPopuptWindow(2);
			mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			break;
		case R.id.act_ll_appoint_date2:
			// 开始时间
			initPopuptWindow(3);
			mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			break;
		case R.id.act_rl_appoint_number:
			// 特卫人数
			initPopuptWindow(4);
			mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			break;
		case R.id.act_rl_appoint_level:
			// 特卫级别
			initPopuptWindow(5);
			mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			break;
		case R.id.fra_iv_viewss:
			finish();
			break;
		case R.id.fra_tv_:
			if (act_tv_appoint_type.getText().toString().equals("点击选择服务类型"))
			{
				Toast.makeText(this, "请选择服务类型", Toast.LENGTH_LONG).show();
				return;
			}
			if (act_tv_appoint_date.getText().toString().equals("点击选择服务时长"))
			{
				Toast.makeText(this, "请选择服务时长", Toast.LENGTH_LONG).show();
				return;
			}
			if (act_tv_appoint_date2.getText().toString().equals("点击选择开始时间"))
			{
				Toast.makeText(this, "请选择开始时间", Toast.LENGTH_LONG).show();
				return;
			}
			Intent intent = new Intent(ActAppoint.this, ActAppoints.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("lt", lt);
			bundle.putSerializable("datas", datess);
			bundle.putString("type", act_tv_appoint_type.getText().toString());
			if (typeid == 0)
			{
				bundle.putInt("typeid", bundlez.getInt("typeid"));
			} else
			{

				bundle.putInt("typeid", typeid);
			}
			bundle.putString("date", act_tv_appoint_date.getText().toString());
			if (date2 == null)
			{
				bundle.putString("date2", bundlez.getString("date2"));
			} else
			{

				bundle.putString("date2", date2);
			}
			bundle.putString("date22", act_tv_appoint_date2.getText()
					.toString());
			bundle.putString("number", act_tv_appoint_number.getText()
					.toString());
			bundle.putInt("number2", number);
			bundle.putString("levelname", act_tv_appoint_level.getText()
					.toString());
			//没有选择勋章进入
			if (level == -1)
			{

				if (bundlez.getInt("level") == 0)
				{
					//第一次进入不选择默认为一个特卫
					level=0;
					bundle.putInt("level", level + 1);
				} else
				{//上级页面跳转进来吧上级页面传递过来的值返回回去
					bundle.putInt("level", bundlez.getInt("level"));
				}
			} else
			{
				System.out.println("aaaaa");
				bundle.putInt("level", level + 1);
			}
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	// 计算时间
	private void istrue()
	{
		try
		{
			if (day.substring(day.indexOf("小"), day.length()).equals("小时"))
			{
				int is = mHour
						+ Integer.parseInt(day.substring(0, day.indexOf("小")));
				if (is == 24)
				{
					aa = 1;
					mHour = 0;
					return;
				}
				if (is > 24)
				{
					int a = is - 24;

					aa = 1;
					mHour = a;

				}
				if (is < 24)
				{
					mHour = is;
				}

				// mDate.add(Calendar.HOUR_OF_DAY,
				// Integer.parseInt(day.substring(0, day.indexOf("小"))));
				return;
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		try
		{
			if (day.substring(day.indexOf("天"), day.length()).equals("天"))
			{

				mDate.add(Calendar.DAY_OF_MONTH,
						Integer.parseInt(day.substring(0, day.indexOf("天"))));
				return;
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		try
		{
			if (day.substring(day.indexOf("月"), day.length()).equals("月"))
			{
				mDate.add(Calendar.MONTH,
						Integer.parseInt(day.substring(0, day.indexOf("月"))));
				return;
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		try
		{
			if (day.substring(day.indexOf("年"), day.length()).equals("年"))
			{
				mDate.add(Calendar.YEAR,
						Integer.parseInt(day.substring(0, day.indexOf("年"))));
				return;
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}

	}

	// 根据日期获得星期
	String getWeek(String pTime)
	{

		String Week = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try
		{

			c.setTime(format.parse(pTime));

		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 1)
		{
			Week += "周日";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2)
		{
			Week += "周一";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3)
		{
			Week += "周二";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4)
		{
			Week += "周三";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5)
		{
			Week += "周四";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6)
		{
			Week += "周五";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7)
		{
			Week += "周六";
		}

		return Week;
	}
}

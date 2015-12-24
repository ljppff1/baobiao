package com.soloman.org.cn.ui.appoint;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.Customer;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.address.ActAppointmentsAddress;
import com.soloman.org.cn.ui.mail_list.ActMailList;
import com.soloman.org.cn.ui.order.ActOrder;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.NetworkJudge;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;
import com.soloman.org.cn.utis.dates;

/**
 * 预约首页
 * 
 * @author Mac
 * 
 */
public class ActAppointHome extends Activity implements OnClickListener
{
	long daysa;
	long hour;
	// 时间相差
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
	int aa = 100;
	SimpleDateFormat format;
	Calendar c;
	private int days;
	/**
	 * 返回
	 */
	ImageView act_tv_common_address_return;
	/**
	 * 拨打客服电话
	 */
	ImageView act_tv_common_address_phone;
	private TextView tv;
	private NumberPicker mDateSpinner;

	private NumberPicker mHourSpinner;

	private NumberPicker mMinuteSpinner;

	private Calendar mDate;

	private int mHour, mMinute, mDays;

	private String[] mDateDisplayValues = new String[7];
	// 取消
	private TextView item_tv_cancel;
	// 确定
	private TextView item_tv_determine;
	private TextView item_tv_;
	private PopupWindow mPopupWindow;
	// 屏幕的width
	private int mScreenWidth;
	// 屏幕的height
	private int mScreenHeight;
	// PopupWindow的width
	private int mPopupWindowWidth;
	// PopupWindow的height
	private int mPopupWindowHeight;

	private Customer cr;
	private String s;
	private Bundle bb;
	private PreferenceUtils preferences;
	private String people;
	private String type;
	private String day;
	Bundle bundle;
	private int price;
	private RelativeLayout act_rl__;
	// 账号
	private TextView act_tv_appoint_home;
	// 电话
	private TextView act_tv_appoint_home1;
	// 地址显示
	private TextView act_tv_appoint_homes;
	// 地址事件
	private RelativeLayout act_rl_appoint_homes;
	private TextView act_tv_appoint_homesw;
	// 选择开始时间
	private TextView act_tv_appoint_homeswss;
	// 结束时间
	private TextView act_tv_appoint_homesws;
	// 选择开始时间事件
	private RelativeLayout act_rl_date;
	// 捎句话
	private TextView act_tv_appoint_homesss;
	// 捎一句点击事件
	private RelativeLayout act_rl_appoint_;
	// 订单价格
	private TextView act_tv_appoint_htme_money;
	// 原价
	private TextView act_tv_appoint_htme_moneyss;
	// 抵扣卷
	private TextView act_tv_appoint_htme_moneyzz;
	// 确定
	private RelativeLayout act_rl_appoint;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_appoint_home);
		MyApplication.getInstance().addActivity(this);
		bundle = getIntent().getExtras();
		day = bundle.getString("pCurrent");
		type = bundle.getString("pCurrents");
		people = bundle.getString("pCurrentss");
		cr = (Customer) bundle.getSerializable("cr");
		setupView();
		setupListener();
		calculationPrice();
	}

	private void setupView()
	{
		preferences = PreferenceUtils.getInstance(ActAppointHome.this,
				PreferenceConstants.LOGIN_PREF);
		act_rl__ = (RelativeLayout) findViewById(R.id.act_rl__);
		act_tv_common_address_return = (ImageView) findViewById(R.id.act_tv_common_address_return);
		act_tv_common_address_phone = (ImageView) findViewById(R.id.act_tv_common_address_phone);
		act_tv_appoint_home = (TextView) findViewById(R.id.act_tv_appoint_home);
		act_tv_appoint_home1 = (TextView) findViewById(R.id.act_tv_appoint_home1);
		act_tv_appoint_homes = (TextView) findViewById(R.id.act_tv_appoint_homes);
		act_rl_appoint_homes = (RelativeLayout) findViewById(R.id.act_rl_appoint_homes);
		act_tv_appoint_homesw = (TextView) findViewById(R.id.act_tv_appoint_homesw);
		act_tv_appoint_homesws = (TextView) findViewById(R.id.act_tv_appoint_homesws);
		act_tv_appoint_homeswss = (TextView) findViewById(R.id.act_tv_appoint_homeswss);
		act_rl_date = (RelativeLayout) findViewById(R.id.act_rl_date);
		act_tv_appoint_homesss = (TextView) findViewById(R.id.act_tv_appoint_homesss);
		act_rl_appoint_ = (RelativeLayout) findViewById(R.id.act_rl_appoint_);
		act_tv_appoint_htme_money = (TextView) findViewById(R.id.act_tv_appoint_htme_money);
		act_tv_appoint_htme_moneyss = (TextView) findViewById(R.id.act_tv_appoint_htme_moneyss);
		act_tv_appoint_htme_moneyzz = (TextView) findViewById(R.id.act_tv_appoint_htme_moneyzz);
		act_rl_appoint = (RelativeLayout) findViewById(R.id.act_rl_appoint);
		act_tv_appoint_home.setText(preferences.getString("userName1", "")
				.toString());
		act_tv_appoint_home1.setText(preferences.getString("phone_number1", "")
				.toString());
		act_tv_appoint_homesss.setText(preferences.getString("yiju", "捎一句")
				.toString());
		if (!preferences.getString("addresssa", "").equals(""))
		{
			s = preferences.getString("addresssa", "");
			act_tv_appoint_homes.setText(s.substring(0, s.indexOf(","))
					+ s.substring(s.indexOf(","), s.length()));
		}
	}

	private void setupListener()
	{
		act_rl_appoint.setOnClickListener(this);
		act_rl_date.setOnClickListener(this);
		act_rl_appoint_.setOnClickListener(this);
		act_rl_appoint_homes.setOnClickListener(this);
		act_tv_common_address_return.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				finish();
			}
		});

		act_tv_common_address_phone.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				initPopuptWindow(2);
				mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			}
		});

		act_rl__.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				Intent intents = new Intent(ActAppointHome.this,
						ActMailList.class);
				startActivityForResult(intents, 40);

			}
		});
	}

	/**
	 * 价格计算
	 */
	private void calculationPrice()
	{

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);

		// boolean is = false;
		// if (NetworkJudge.getNetWorkType(ActAppointHome.this) !=
		// NetworkJudge.NETWORKTYPE_INVALID)
		// {
		// is = true;
		// }

		RequestParams requestParams = new RequestParams();
		requestParams.put("service_at",str);
		requestParams.put("duration", day);
		requestParams.put("level",type);
		requestParams.put("people_count",people);
		HttpRestClient.postHttpHuaShangha(this, "orders/price", "v2",
				requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						System.out.println("aaaaaaa        " + response);
						JSONObject obj = (JSONObject) response;
						JSONArray array;
						try
						{
							act_tv_appoint_htme_money.setText(obj
									.getString("current_price"));
							act_tv_appoint_htme_moneyss.setText(obj
									.getString("full_price"));
							act_tv_appoint_htme_moneyzz.setText(obj
									.getString("diff"));
							price = obj.getInt("price");
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.act_rl_appoint:
			String str = act_tv_appoint_homes.getText().toString();
			// String newStr = str.subString(str.indexOf("\\"), str.length());
			// 确定
			if (aa == 100)
			{
				Toast.makeText(ActAppointHome.this, "请选择开始时间",
						Toast.LENGTH_LONG).show();
				return;
			} else if (act_tv_appoint_homes.getText().equals("请输入地址"))
			{
				Toast.makeText(ActAppointHome.this, "请输入地址", Toast.LENGTH_LONG)
						.show();
				return;
			}

			Intent intentz = new Intent(ActAppointHome.this, ActOrder.class);
			Bundle bundlez = new Bundle();
			bundlez.putString("name", preferences.getString("userName", "")
					.toString());
			bundlez.putString("phone_num",
					preferences.getString("phone_number", "").toString());
			bundlez.putString("duration", day);
			bundlez.putString("level", type);
			bundlez.putString("people_count", people);
			bundlez.putString("address",
					str.substring(str.indexOf(",") + 1, str.length()));
			bundlez.putString("service_at", act_tv_appoint_homeswss.getText()
					+ ":00");
			bundlez.putString("end_at", act_tv_appoint_homesws.getText()
					+ ":00");
			bundlez.putString("words", act_tv_appoint_homesss.getText()
					.toString());
			bundlez.putString("city", str.substring(0, str.indexOf(",")));
			bundlez.putString("price", act_tv_appoint_htme_money.getText()
					.toString());
			bundlez.putString("prices", String.valueOf(price));

			intentz.putExtras(bundlez);
			startActivityForResult(intentz, 30);
			break;
		case R.id.act_rl_appoint_homes:
			// 地址跳转
			Intent intent = new Intent(ActAppointHome.this,
					ActAppointmentsAddress.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("cr", cr);
			intent.putExtras(bundle);
			startActivityForResult(intent, 10);
			break;
		case R.id.act_rl_date:
			// 开始时间
			initPopuptWindow(1);
			mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
			break;
		case R.id.act_rl_appoint_:
			// 捎一句
			Intent intents = new Intent(ActAppointHome.this, ActMessages.class);
			startActivityForResult(intents, 20);
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
				act_tv_appoint_homes.setText(preferences.getString("addresssa",
						""));
				break;
			case 20:
				act_tv_appoint_homesss.setText(bb.getString("mess"));
				break;
			case 30:
				finish();
				break;
			case 40:
				act_tv_appoint_home.setText(preferences.getString("userName1",
						""));
				preferences.getString("aa", "");
				act_tv_appoint_home1.setText(preferences.getString(
						"phone_number1", ""));
				break;
			default:
				break;
			}
		}
	}

	// /*
	// * 获取PopupWindow实例
	// */
	// private void getPopupWindowInstance()
	// {
	// if (null != mPopupWindow)
	// {
	// mPopupWindow.dismiss();
	// return;
	// } else
	// {
	// initPopuptWindow(1);
	// }
	// }

	/*
	 * 创建PopupWindow
	 */
	private void initPopuptWindow(int i)
	{
		// 时间
		if (i == 1)
		{
			LayoutInflater layoutInflater = LayoutInflater.from(this);
			View popupWindow = layoutInflater.inflate(R.layout.item_rl_date,
					null);
			item_tv_cancel = (TextView) popupWindow
					.findViewById(R.id.item_tv_cancel);
			item_tv_determine = (TextView) popupWindow
					.findViewById(R.id.item_tv_determine);

			item_tv_cancel.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					mPopupWindow.dismiss();
				}
			});
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
						act_tv_appoint_homeswss.setText("开始："
								+ mDate.get(mDate.YEAR) + "-"
								+ (mDate.get(Calendar.MONTH) + 1) + "-"
								+ mDate.get(mDate.DAY_OF_MONTH) + " "
								+ todays.toString() + " " + mHour + ":"
								+ mMinute);
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
						act_tv_appoint_homesws.setText("结束："
								+ mDate.get(mDate.YEAR)
								+ "-"
								+ (mDate.get(Calendar.MONTH) + 1)
								+ "-"
								+ mDays
								+ " "
								+ getWeek(mDate.get(mDate.YEAR) + "-"
										+ (mDate.get(Calendar.MONTH) + 1) + "-"
										+ mDate.get(mDate.DAY_OF_MONTH)) + " "
								+ mHour + ":" + mMinute);
						act_tv_appoint_homesw.setVisibility(View.INVISIBLE);
						act_tv_appoint_homeswss.setVisibility(View.VISIBLE);
						act_tv_appoint_homesws.setVisibility(View.VISIBLE);
						mPopupWindow.dismiss();
						aa = 0;
					} else
					{

						Toast.makeText(ActAppointHome.this, "开始时间请在2小时后",
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
			mPopupWindow = new PopupWindow(popupWindow,
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		} else
		{

			LayoutInflater layoutInflater = LayoutInflater.from(this);
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
			tv.setText(preferences.getString("customer", ""));
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
								Intent intent = new Intent(Intent.ACTION_CALL,
										Uri.parse("tel:"
												+ tv.getText().toString()));
								startActivity(intent);
							} catch (Exception e)
							{
								// TODO: handle exception
								Toast.makeText(ActAppointHome.this, "无有效手机卡",
										Toast.LENGTH_LONG).show();
							}

						}
					});

			// 创建一个PopupWindow
			// 参数1：contentView 指定PopupWindow的内容
			// 参数2：width 指定PopupWindow的width
			// 参数3：height 指定PopupWindow的height
			mPopupWindow = new PopupWindow(popupWindow,
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		}

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

	private NumberPicker.OnValueChangeListener mOnDateChangedListener = new OnValueChangeListener()
	{
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

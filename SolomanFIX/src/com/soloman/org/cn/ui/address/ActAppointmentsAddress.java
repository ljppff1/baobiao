package com.soloman.org.cn.ui.address;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.CommonAddress;
import com.soloman.org.cn.bean.Customer;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.NetworkJudge;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;

/**
 * 预约地址
 * 
 * @author Mac
 * 
 */
@SuppressLint("ResourceAsColor")
public class ActAppointmentsAddress extends Activity
{
	/**
	 * 返回
	 */
	ImageView act_tv_common_address_return;
	/**
	 * 拨打客服电话
	 */
	ImageView act_tv_common_address_phone;
	private TextView tv;
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
	private CommonAddress cmnAddress;

	private Bundle bb;
	private String s;
	private PreferenceUtils preferences;
	int i;
	private int clickPosition = -1;
	List<CommonAddress> lt;
	// 常用地址
	private RelativeLayout act_rl_;
	private AppointmentsAddressAdapters aAddressAdapter;
	// 城市点击事件
	private RelativeLayout item_rl_common_address;
	// 城市显示
	private TextView item_tv_common_address;
	// 详细地址
	private EditText item_tv_common_address_text;
	private ListView act_lv_appintments;
	// 确定
	private RelativeLayout act_rl_appoint;
	private ImageView act_Iv_appintments_address;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_appointments_address);
		MyApplication.getInstance().addActivity(this);
		preferences = PreferenceUtils.getInstance(ActAppointmentsAddress.this,
				PreferenceConstants.LOGIN_PREF);
		setupView();
		setupListener();
		HistoryRecord();
	}

	@SuppressLint("ClickableViewAccessibility")
	private void setupListener()
	{
		// 隐藏软件盘
		findViewById(R.id.keyboard).setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{

				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				item_tv_common_address_text.requestFocus(); // 请求获取焦点
				item_tv_common_address_text.clearFocus(); // 清除焦点
				return imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
			}
		});
		act_lv_appintments.setOnItemClickListener(new MyItemClickListener());
		item_tv_common_address_text
				.setOnFocusChangeListener(new OnFocusChangeListener()
				{
					@Override
					public void onFocusChange(View v, boolean hasFocus)
					{

						if (hasFocus)
						{// 获得焦点
							act_Iv_appintments_address
									.setVisibility(View.VISIBLE);
						} else
						{// 失去焦点
							act_Iv_appintments_address.setVisibility(View.GONE);
						}
					}

				});
		act_Iv_appintments_address.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				item_tv_common_address_text.setText("");
			}
		});
		act_rl_appoint.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (item_tv_common_address_text.getText().length() > 0)
				{
					preferences.put("addresssa", item_tv_common_address
							.getText().toString()
							+ ","
							+ item_tv_common_address_text.getText().toString());
					// 获取启动这个activity的intent
					Intent intent = getIntent();
					ActAppointmentsAddress.this.setResult(1, intent);// 跳转回原来的activity
					ActAppointmentsAddress.this.finish();// 结束当前activity
				}
			}
		});
		act_rl_.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				// 常用地址获取
				Intent intent = new Intent(ActAppointmentsAddress.this,
						ActCommonAddress.class);
				Bundle bundle = new Bundle();
				bundle.putInt("is", 1);
				bundle.putSerializable("cr", getIntent().getExtras()
						.getSerializable("cr"));
				intent.putExtras(bundle);
				startActivityForResult(intent, 10);
			}
		});
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
				getPopupWindowInstance();
				mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			}
		});
	}

	class MyItemClickListener implements OnItemClickListener
	{

		@SuppressLint("ResourceAsColor")
		@Override
		public void onItemClick(AdapterView<?> parent, View arg1,
				int checkedId, long arg3)
		{
			checkedId = checkedId - 1;
			System.out.println(checkedId + "`````````" + arg3);
			CommonAddress ca = lt.get(checkedId);
			item_tv_common_address.setText(ca.getCity());
			item_tv_common_address_text.setText(ca.getInfo());
			if (checkedId != clickPosition)
			{
				clickPosition = checkedId;
			} else
			{
				// clickPosition = -1;
			}
			aAddressAdapter.notifyDataSetChanged();
		}

	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		lt = new ArrayList<CommonAddress>();
		act_tv_common_address_return = (ImageView) findViewById(R.id.act_tv_common_address_return);
		act_tv_common_address_phone = (ImageView) findViewById(R.id.act_tv_common_address_phone);
		act_Iv_appintments_address = (ImageView) findViewById(R.id.act_iv_appintments_address);
		act_rl_ = (RelativeLayout) findViewById(R.id.act_rl_);
		item_rl_common_address = (RelativeLayout) findViewById(R.id.item_rl_common_address);
		item_tv_common_address = (TextView) findViewById(R.id.item_tv_common_address);
		item_tv_common_address_text = (EditText) findViewById(R.id.item_tv_common_address_text);
		act_lv_appintments = (ListView) findViewById(R.id.act_lv_appintments);
		act_lv_appintments.addFooterView(LayoutInflater.from(this).inflate(
				R.layout.item_act_appointments_addressaa, null));
		act_lv_appintments.addHeaderView(LayoutInflater.from(this).inflate(
				R.layout.item_act_appointments_addressaa, null));
		act_rl_appoint = (RelativeLayout) findViewById(R.id.act_rl_appoint);
		aAddressAdapter = new AppointmentsAddressAdapters(lt, this);
		act_lv_appintments.setAdapter(aAddressAdapter);
		if (!preferences.getString("addresssa", "").equals(""))
		{
			s = preferences.getString("addresssa", "");
			item_tv_common_address.setText(s.substring(0, s.indexOf(",")));
			item_tv_common_address_text.setText(s.substring(s.indexOf(",") + 1,
					s.length()));
		}
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
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + tv.getText().toString()));
							startActivity(intent);
						} catch (Exception e)
						{
							// TODO: handle exception
							Toast.makeText(ActAppointmentsAddress.this,
									"无有效手机卡", Toast.LENGTH_LONG).show();
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
		mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
		mScreenWidth = getWindowManager().getDefaultDisplay().getHeight();
		mPopupWindowWidth = mPopupWindow.getWidth();
		mPopupWindowHeight = mPopupWindow.getHeight();
	}

	/**
	 * 历史记录
	 */
	private void HistoryRecord()
	{

		// boolean is = false;
		// if (NetworkJudge.getNetWorkType(ActAppointmentsAddress.this) !=
		// NetworkJudge.NETWORKTYPE_INVALID)
		// {
		// is = true;
		// }
		HttpRestClient.getHttpHuaShangha(this, "addresses/history", "v2", null,
				new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);

						System.out.println("aaaaaaa        " + response);
						JSONObject obj;
						try
						{
							JSONArray array = response
									.getJSONArray("addresses");
							for (int i = 0; i < array.length(); i++)
							{
								CommonAddress cAddress = new CommonAddress();
								obj = array.getJSONObject(i);
								cAddress.setCity(obj.getString("city"));
								cAddress.setCreated_at(obj
										.getString("created_at"));
								cAddress.setId(obj.getInt("id"));
								cAddress.setInfo(obj.getString("info"));
								cAddress.setIs_default(obj
										.getBoolean("is_default"));
								cAddress.setUpdated_at(obj
										.getString("updated_at"));
								cAddress.setUser_id(obj.getInt("user_id"));
								lt.add(cAddress);
							}
							aAddressAdapter.notifyDataSetChanged();
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

	}

	class AppointmentsAddressAdapters extends BaseAdapter
	{
		private List<CommonAddress> city;

		private Context context;

		public AppointmentsAddressAdapters(List<CommonAddress> lt,
				Context context)
		{
			super();
			this.city = lt;
			this.context = context;
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return city.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return city.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2)
		{
			// TODO Auto-generated method stub
			final ViewHolder holder;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_city3, null);
				holder.item_city_tv = (TextView) convertView
						.findViewById(R.id.item_city_tv);
				holder.item_city_rl = (RelativeLayout) convertView
						.findViewById(R.id.item_city_rl);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			CommonAddress cAddress = city.get(position);
			holder.item_city_tv.setText(cAddress.getCity() + ","
					+ cAddress.getInfo());
			holder.item_city_rl.setBackgroundDrawable(context.getResources()
					.getDrawable(R.color.color_a));
			if (position == clickPosition)
			{
				holder.item_city_rl.setBackgroundDrawable(context
						.getResources().getDrawable(R.color.cc));
			}

			return convertView;
		}

		class ViewHolder
		{
			private TextView item_city_tv;
			private RelativeLayout item_city_rl;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1)
		{
			bb = data.getExtras();
			cmnAddress = (CommonAddress) bb.getSerializable("address");
			switch (requestCode)
			{
			case 10:
				item_tv_common_address_text.setText(cmnAddress.getInfo()
						.toString());
				item_tv_common_address.setText(cmnAddress.getCity().toString());
				break;
			default:
				break;
			}
		}
	}
}

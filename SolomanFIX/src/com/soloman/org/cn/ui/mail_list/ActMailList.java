package com.soloman.org.cn.ui.mail_list;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.MessageListAdapter;
import com.soloman.org.cn.bean.CommonAddress;
import com.soloman.org.cn.bean.User;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.ActCity;
import com.soloman.org.cn.ui.address.ActAppointmentsAddress;
import com.soloman.org.cn.ui.appoint.ActAppointHome;
import com.soloman.org.cn.ui.message.ActMessageList;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 通讯录main
 * 
 * @author Mac
 * 
 */
public class ActMailList extends Activity
{
	private int is;
	private Bundle bb;
	private ImageView login_click1;
	private ImageView login_click3;
	private int clickPosition = -1;
	private MailListAdapter mla;
	private ArrayList<User> lt;
	private PreferenceUtils preferences;
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

	/**
	 * 姓名
	 */
	private EditText item_tv_common_address_text;
	/**
	 * 手机号码
	 */
	private EditText item_tv_common_address_text1;
	private RelativeLayout act_rl_;
	private ListView act_lv_appintments;
	private RelativeLayout act_rl_appoint;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_mail_list);
		MyApplication.getInstance().addActivity(this);
		preferences = PreferenceUtils.getInstance(ActMailList.this,
				PreferenceConstants.LOGIN_PREF);
		setupView();
		setupListener();
		guests();
	}

	private void setupListener()
	{
		// TODO Auto-generated method stub
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

	private void setupView()
	{
		// TODO Auto-generated method stub
		act_tv_common_address_return = (ImageView) findViewById(R.id.act_tv_common_address_return);
		act_tv_common_address_phone = (ImageView) findViewById(R.id.act_tv_common_address_phone);
		item_tv_common_address_text = (EditText) findViewById(R.id.item_tv_common_address_text);
		item_tv_common_address_text1 = (EditText) findViewById(R.id.item_tv_common_address_text1);
		act_rl_ = (RelativeLayout) findViewById(R.id.act_rl_);
		act_lv_appintments = (ListView) findViewById(R.id.act_lv_appintments);
		act_lv_appintments.setOnItemClickListener(new MyItemClickListener());
		act_rl_appoint = (RelativeLayout) findViewById(R.id.act_rl_appoint);
		lt = new ArrayList<User>();
		mla = new MailListAdapter(lt, ActMailList.this);
		act_lv_appintments.setAdapter(mla);
		login_click1 = (ImageView) findViewById(R.id.login_click1);
		login_click3 = (ImageView) findViewById(R.id.login_click3);

		item_tv_common_address_text.setText(preferences.getString("userName1",
				"").toString());
		item_tv_common_address_text1.setText(preferences.getString(
				"phone_number1", "").toString());
		act_rl_.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intents = new Intent(ActMailList.this, ActMail.class);
				startActivityForResult(intents, 10);
			}
		});
		act_rl_appoint.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				preferences.put("userName1",
						item_tv_common_address_text.getText().toString());
				preferences.put("phone_number1",
						item_tv_common_address_text1.getText().toString());

				// 获取启动这个activity的intent
				Intent intent = getIntent();
				ActMailList.this.setResult(1, intent);// 跳转回原来的activity
				ActMailList.this.finish();// 结束当前activity
			}
		});
		login_click1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				item_tv_common_address_text.setText("");
			}
		});

		login_click3.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				item_tv_common_address_text1.setText("");
			}
		});
		item_tv_common_address_text.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				// TODO Auto-generated method stub
				if(is==1){
					return;
				}
				if (!item_tv_common_address_text.getText().toString()
						.equals(""))
				{
					login_click1.setVisibility(View.VISIBLE);
				} else
				{
					login_click1.setVisibility(View.GONE);

				}
			}
		});

		item_tv_common_address_text
				.setOnFocusChangeListener(new OnFocusChangeListener()
				{
					@Override
					public void onFocusChange(View v, boolean hasFocus)
					{

						if (hasFocus)
						{// 获得焦点
							if (!item_tv_common_address_text.getText()
									.toString().equals(""))
							{
								login_click1.setVisibility(View.VISIBLE);
							}

						} else
						{// 失去焦点
							login_click1.setVisibility(View.GONE);
						}
					}

				});

		item_tv_common_address_text1.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				// TODO Auto-generated method stub
				if(is==1){
					is=0;
					return;
				}
				if (!item_tv_common_address_text.getText().toString()
						.equals(""))
				{
					login_click3.setVisibility(View.VISIBLE);
				} else
				{
					login_click3.setVisibility(View.GONE);

				}
			}
		});

		item_tv_common_address_text1
				.setOnFocusChangeListener(new OnFocusChangeListener()
				{
					@Override
					public void onFocusChange(View v, boolean hasFocus)
					{

						if (hasFocus)
						{// 获得焦点
							if (!item_tv_common_address_text1.getText()
									.toString().equals(""))
							{
								login_click3.setVisibility(View.VISIBLE);
							}

						} else
						{// 失去焦点
							login_click3.setVisibility(View.GONE);
						}
					}

				});
		findViewById(R.id.keyboard).setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{

				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				item_tv_common_address_text.requestFocus(); // 请求获取焦点
				item_tv_common_address_text.clearFocus(); // 清除焦点
				item_tv_common_address_text1.requestFocus();
				item_tv_common_address_text1.clearFocus();
				return imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
			}
		});
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
							Toast.makeText(ActMailList.this,"无有效手机卡", Toast.LENGTH_LONG).show();
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
	 * 被服务人的列表
	 */
	private void guests()
	{
		HttpRestClient.getHttpHuaShangha(this,
				"guests/index", "v2", null,
				new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						JSONObject obj;
						try
						{
							JSONArray array = response.getJSONArray("guests");

							for (int i = 0; i < array.length(); i++)
							{
								User user = new User();
								obj = array.getJSONObject(i);
								user.setCreated_at(obj.getString("created_at"));

								user.setEnable(obj.getString("enable"));

								user.setId(obj.getInt("id"));

								user.setName(obj.getString("name"));
								user.setPhone_num(obj.getString("phone_num"));
								user.setUpdated_at(obj.getString("updated_at"));
								user.setUsed_at(obj.getString("used_at"));
								user.setUser_id(obj.getInt("user_id"));
								lt.add(user);
							}
							mla.notifyDataSetChanged();
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
			 is=1;
			// checkedId = checkedId - 1;
			checkedId = checkedId;
			System.out.println(checkedId + "`````````" + arg3);
			User ca = lt.get(checkedId);
			item_tv_common_address_text.setText(ca.getName());
			item_tv_common_address_text1.setText(ca.getPhone_num());
			if (checkedId != clickPosition)
			{
				clickPosition = checkedId;
			} else
			{
				// clickPosition = -1;
			}
			mla.notifyDataSetChanged();
		}
	}

	public class MailListAdapter extends BaseAdapter
	{
		private ArrayList<User> city;

		private Context context;

		public MailListAdapter(ArrayList<User> city, Context context)
		{
			super();
			this.city = city;
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
						R.layout.item_mail_list, null);
				holder.item_city_tv = (TextView) convertView
						.findViewById(R.id.item_city_tv);
				holder.login_click1 = (ImageView) convertView
						.findViewById(R.id.login_click1);
				holder.rl__ = (RelativeLayout) convertView
						.findViewById(R.id.rl__);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			User message = city.get(position);
			holder.item_city_tv.setText(message.getName() + "  "
					+ message.getPhone_num());
			holder.rl__.setBackgroundDrawable(context.getResources()
					.getDrawable(R.color.color_a));
			if (position == clickPosition)
			{
				holder.rl__.setBackgroundDrawable(context.getResources()
						.getDrawable(R.color.cc));
			}
			holder.login_click1.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub

				}
			});
			return convertView;
		}

		class ViewHolder
		{

			private TextView item_city_tv;
			private ImageView login_click1;
			private RelativeLayout rl__;
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
				is=1;
				item_tv_common_address_text.setText(bb.getString("name"));
				item_tv_common_address_text1.setText(bb.getString("number"));
				break;
			default:
				break;
			}
		}
	}
}

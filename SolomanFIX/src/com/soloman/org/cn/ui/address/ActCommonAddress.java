package com.soloman.org.cn.ui.address;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.model.Text;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.CommonAddress;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.ActCity;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage.IMediaObject;

/**
 * 常用地址
 * 
 * @author Mac
 * 
 */
public class ActCommonAddress extends Activity
{
	CommonAddress  ca;
	private int id;
	private int ids;
	private int item = -1;
	boolean is = false;
	CommonAddress comm;
	Bundle bb;
	private CommonAddressAdapter caa;
	private ArrayList<CommonAddress> lt;
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

	private PreferenceUtils preferences;
	/**
	 * 返回
	 */
	private ImageView act_tv_common_address_return;

	private ListView act_lv_common_address_list;
	private RelativeLayout act_rl_common_address_top;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_common_address);
		MyApplication.getInstance().addActivity(this);
		preferences = PreferenceUtils.getInstance(ActCommonAddress.this,
				PreferenceConstants.LOGIN_PREF);
		Bundle bundle = getIntent().getExtras();
		is = bundle.getBoolean("is");
		item = bundle.getInt("item");
		setupView();
		setupListener();
		commonAddress();
	}

	private void setupView()
	{
		act_tv_common_address_return = (ImageView) findViewById(R.id.fra_iv_viewss);

		act_lv_common_address_list = (ListView) findViewById(R.id.act_lv_common_address_list);
		act_lv_common_address_list.addHeaderView(LayoutInflater.from(this)
				.inflate(R.layout.item_common_address_add, null));
		// act_lv_common_address_list.addFooterView(LayoutInflater.from(this)
		// .inflate(R.layout.item_common_address_add, null));
		act_rl_common_address_top = (RelativeLayout) findViewById(R.id.act_rl_common_address_tops);
		act_lv_common_address_list.setFooterDividersEnabled(false);
		act_lv_common_address_list.setHeaderDividersEnabled(false);
		lt = new ArrayList<CommonAddress>();
		caa = new CommonAddressAdapter(lt, this);
		act_lv_common_address_list.setAdapter(caa);
	}

	private void setupListener()
	{

		act_tv_common_address_return.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				finish();
			}
		});
		act_rl_common_address_top.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActCommonAddress.this,
						ActCommonAddressAdd.class);
				startActivityForResult(intent, 10);
			}
		});
	}

	/*
	 * 创建PopupWindow
	 */
	private void initPopuptWindow()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.item_address, null);
		popupWindow.findViewById(R.id.item_rl_address_edit).setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						// 编辑
						Intent intent = new Intent(ActCommonAddress.this,
								ActCommonAddressModify.class);
						Bundle bundle=new Bundle();
						bundle.putSerializable("ca",ca);
						intent.putExtras(bundle);
						startActivityForResult(intent, 20);
					}
				});
		popupWindow.findViewById(R.id.item_rl_address_delete)
				.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{

						// 删除
						destroyAddress();
						lt.remove(ids);
						caa.notifyDataSetChanged();
						mPopupWindow.dismiss();
					}
				});

		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height
		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// 需要设置一下此参数，点击外边可消失
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		mPopupWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		mPopupWindow.setFocusable(false);
		// 获取屏幕和PopupWindow的width和height
		mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
		mScreenWidth = getWindowManager().getDefaultDisplay().getHeight();
		mPopupWindowWidth = mPopupWindow.getWidth();
		mPopupWindowHeight = mPopupWindow.getHeight();
	}

	// 常用地址
	private void commonAddress()
	{

		HttpRestClient.getHttpHuaShangha(this, "addresses/index", "v2", null,
				new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						System.out.println("asdasdadasd              "
								+ response);
						try
						{
							if (response.getInt("code") == 200)
							{
								JSONObject obj = response.getJSONObject("data");
								JSONArray array;
								JSONObject objs;
								try
								{
									array = obj.getJSONArray("addresses");
									for (int i = 0; i < array.length(); i++)
									{
										CommonAddress fotem = new CommonAddress();
										objs = array.getJSONObject(i);
										fotem.setCity(objs.getString("city"));

										fotem.setComplex(objs
												.getString("complex"));
										fotem.setCreated_at(objs
												.getString("created_at"));
										fotem.setId(objs.getInt("id"));
										fotem.setInfo(objs.getString("info"));
										fotem.setIs_default(objs
												.getBoolean("is_default"));
										fotem.setLocation_info(objs
												.getString("location_info"));
										fotem.setName(objs.getString("name"));
										fotem.setPhone(objs.getString("phone"));
										fotem.setSex(objs.getInt("sex"));
										fotem.setUpdated_at(objs
												.getString("updated_at"));
										fotem.setUser_id(objs.getInt("user_id"));
										lt.add(fotem);
									}
									caa.notifyDataSetChanged();
								} catch (JSONException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else
							{
								Toast.makeText(ActCommonAddress.this,
										response.getString("message"),
										Toast.LENGTH_LONG);
							}
						} catch (JSONException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});

	}

	private void destroyAddress()
	{
		RequestParams requestParams = new RequestParams();
		requestParams.put("id", id + "");
		HttpRestClient.postHttpHuaShangha(this, "addresses/destroy", "v2",
				requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						System.out.println("asdasdadasd              "
								+ response);

					}

					@Override
					public void onFailure(Throwable e, JSONObject errorResponse)
					{
						// TODO Auto-generated method stub
						super.onFailure(e, errorResponse);
						System.out.println("aa");
					}
				});

	}

	class CommonAddressAdapter extends BaseAdapter
	{
		private ArrayList<CommonAddress> city;

		private Context context;

		public CommonAddressAdapter(ArrayList<CommonAddress> city,
				Context context)
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
						R.layout.item_common_address, null);
				holder.item_iv = (ImageView) convertView
						.findViewById(R.id.item_iv);
				holder.item_ivs = (ImageView) convertView
						.findViewById(R.id.item_ivs);
				holder.item_tv_name = (TextView) convertView
						.findViewById(R.id.item_tv_name);
				holder.item_tv_address = (TextView) convertView
						.findViewById(R.id.item_tv_address);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			final CommonAddress address = city.get(position);
			holder.item_tv_name.setText(address.getName());
			holder.item_tv_address.setText(address.getComplex()
					+ address.getInfo() + "    " + address.getPhone());
			if (address.getId() == item)
			{
				address.getPhone();
				holder.item_iv.setVisibility(View.VISIBLE);
			} else
			{
				holder.item_iv.setVisibility(View.INVISIBLE);
			}

			holder.item_ivs.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					ids = position;
					id = address.getId();
					  ca=address;
					lt.size();
					initPopuptWindow();
					if (position + 1 == lt.size())
					{
						mPopupWindow.showAsDropDown(v, -330, -300);// X、Y方向各偏移50
					} else
					{
						mPopupWindow.showAsDropDown(v, -330, 0);// X、Y方向各偏移50
					}
				}
			});
			if (is == true)
			{
				convertView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						// 获取启动这个activity的intent
						Intent intent = getIntent();
						Bundle b = new Bundle();
						b.putInt("id", address.getId());// 把数据塞入intent里面
						b.putString("name", address.getName());
						b.putString("info", address.getInfo());
						b.putString("complex", address.getComplex());
						b.putString("phone", address.getPhone());
						intent.putExtras(b);
						setResult(1, intent);// 跳转回原来的activity
						finish();// 结束当前activity
					}
				});
			}
			return convertView;
		}

		class ViewHolder
		{

			private ImageView item_iv;
			private ImageView item_ivs;
			private TextView item_tv_name;
			private TextView item_tv_address;

		}

		// 删除常用地址
		private void deleteAddress(final int positions)
		{
			RequestParams requestParams = new RequestParams();
			requestParams.put("id", city.get(positions).getId() + "");
			HttpRestClient.postHttpHuaShangha(context, "addresses/destroy",
					"v2", requestParams, new JsonHttpResponseHandler()
					{
						@Override
						public void onSuccess(JSONObject response)
						{
							// TODO Auto-generated method stub
							super.onSuccess(response);
							try
							{
								if (response.getString("message")
										.equals("删除成功"))
								{
									lt.remove(positions);
									caa.notifyDataSetChanged();
								}
							} catch (JSONException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

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
				comm = (CommonAddress) bb.getSerializable("City");
				lt.add(0, comm);
				caa.notifyDataSetChanged();
				// act_tv_address.setText(bb.getString("City").toString());
				break;
			case 20:
				comm = (CommonAddress) bb.getSerializable("address");
				lt.set(ids, comm);
				caa.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}
	}
}

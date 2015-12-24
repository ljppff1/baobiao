package com.soloman.org.cn.adapter;

import java.util.ArrayList;

import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.CityAdapter.ViewHolder;
import com.soloman.org.cn.bean.Indent;
import com.soloman.org.cn.ui.order.ActOrder;
import com.soloman.org.cn.ui.pay.ActPays;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 历史订单ro当前订单
 * 
 * @author Mac
 * 
 */
public class PresentIndentAdapter extends BaseAdapter
{
	FragmentActivity activity;
	int i;
	private String s;
	private ArrayList<Indent> city;
	private String Status;
	private Context context;
	private PopupWindow mPopupWindow;
	// 屏幕的width
	private int mScreenWidth;
	// 屏幕的height
	private int mScreenHeight;
	// PopupWindow的width
	private int mPopupWindowWidth;
	// PopupWindow的height
	private int mPopupWindowHeight;

	public PresentIndentAdapter(ArrayList<Indent> city, Context context)
	{
		super();
		this.city = city;
		this.context = context;
		this.activity = (FragmentActivity) context;
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
					R.layout.item_present_indent, null);
			holder.item_city_tv_start_date = (TextView) convertView
					.findViewById(R.id.item_city_tv_start_date);

			holder.item_city_tv_end_date = (TextView) convertView
					.findViewById(R.id.item_city_tv_end_date);

			holder.item_city_tv_duration = (TextView) convertView
					.findViewById(R.id.item_city_tv_duration);

			holder.item_city_tv_people_count = (TextView) convertView
					.findViewById(R.id.item_present_indent_tv_people_countss);

			holder.item_city_tv_address = (TextView) convertView
					.findViewById(R.id.item_present_indent_tv_address);

			holder.item_city_tv_Money = (TextView) convertView
					.findViewById(R.id.item_present_indent_tv_money);

			holder.item_present_indent_tv_level = (TextView) convertView
					.findViewById(R.id.item_present_indent_tv_level);

			holder.id = (TextView) convertView
					.findViewById(R.id.item_present_indent_tv_id);

			holder.item_present_indent_tv_false = (View) convertView
					.findViewById(R.id.item_present_indent_tv_false);

			holder.item_present_indent_tv_falses = (View) convertView
					.findViewById(R.id.item_present_indent_tv_falses);

			holder.item_present_indent_tv_falsess = (View) convertView
					.findViewById(R.id.item_present_indent_tv_falsess);

			holder.item_present_indent_tv_state = (TextView) convertView
					.findViewById(R.id.item_present_indent_tv_state);

			holder.item_present_indent_tv_click = (Button) convertView
					.findViewById(R.id.item_present_indent_tv_click);
			convertView.setTag(holder);

		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		final Indent indent = city.get(position);
		holder.item_city_tv_start_date.setText(indent.getService_at());
		holder.item_city_tv_end_date.setText(indent.getFinish_at());
		holder.item_city_tv_duration.setText(indent.getDuration());
		holder.item_city_tv_people_count.setText(indent.getPeople_count());
		holder.item_city_tv_address.setText(indent.getAddress());
		// holder.item_city_tv_Money.setText(indent.getFull_price()); //没有优惠的价格
		holder.item_city_tv_Money.setText(indent.getCurrent_price()); // 优惠后的价格
		holder.item_present_indent_tv_level.setText(indent.getLevel());
		holder.id.setText(indent.getId() + "");
		holder.item_present_indent_tv_false.setLayerType(
				View.LAYER_TYPE_SOFTWARE, null);
		holder.item_present_indent_tv_falses.setLayerType(
				View.LAYER_TYPE_SOFTWARE, null);
		holder.item_present_indent_tv_falsess.setLayerType(
				View.LAYER_TYPE_SOFTWARE, null);

		if (indent.getStatus() == -1)
		{
			Status = "已拒绝";
		} else if (indent.getStatus() == 0)
		{
			Status = "待处理";
		} else if (indent.getStatus() == 1)
		{
			Status = "履行中";
		} else if (indent.getStatus() == 2)
		{
			Status = "履行完成";
		} else if (indent.getStatus() == 3)
		{
			Status = " 已放弃";
		}

		holder.item_present_indent_tv_state.setText(Status);
		if (indent.getHas_paid() == 0)
		{
			holder.item_present_indent_tv_click.setText("去付款");
			// 未支付 去支付

		} else
		{
			holder.item_present_indent_tv_click.setText("已支付");
			// 已支付 查看详情
		}
		if (indent.getStatus() == 3 || indent.getStatus() == -1)
		{
			holder.item_present_indent_tv_click.setText("查看详情");
		}
		holder.item_present_indent_tv_click
				.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						if (holder.item_present_indent_tv_click.getText()
								.equals("查看详情")
								|| holder.item_present_indent_tv_click
										.getText().equals("已支付"))
						{
							s = holder.item_present_indent_tv_click.getText()
									.toString();
							i = position;
							// 查看详情
							System.out.println("// 查看详情");
							getPopupWindowInstance();
							mPopupWindow.showAtLocation(arg0, Gravity.CENTER,
									0, 0);
						} else
						{
							Intent intent = new Intent(context, ActPays.class);
							Bundle bundle = new Bundle();
							bundle.putString("dddid",
									String.valueOf(indent.getId()));
							bundle.putInt("id", position);
							bundle.putString("names", indent.getLevel());
							//
							// bundle.putString(
							// "prices",
							// indent.getFull_price()
							// .substring(
							// 0,
							// indent.getFull_price()
							// .indexOf("元")));
							bundle.putString("prices",
									String.valueOf(indent.getPrice()));
							bundle.putString("i", "PresentIndentAdapter");
							intent.putExtras(bundle);
							activity.startActivityForResult(intent, 10);
						}

					}
				});

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				s = holder.item_present_indent_tv_click.getText().toString();
				i = position;
				// 查看详情
				System.out.println("// 查看详情");
				getPopupWindowInstance();
				mPopupWindow.showAtLocation(arg0, Gravity.CENTER, 0, 0);
			}
		});
		return convertView;
	}

	/*
	 * 获取PopupWindow实例
	 */
	private void getPopupWindowInstance()
	{
		// if (null != mPopupWindow)
		// {
		// mPopupWindow.dismiss();
		// return;
		// } else
		// {
		initPopuptWindow();
		// }
	}

	/*
	 * 创建PopupWindow
	 */
	private void initPopuptWindow()
	{
		final Indent indent = city.get(i);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View popupWindow = layoutInflater.inflate(
				R.layout.item_present_indent_details, null);
		ImageView item_present_indent_details_tv_cancel = (ImageView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_cancel);
		// 姓名
		TextView item_present_indent_details_tv_name = (TextView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_name);
		// 电话
		TextView item_present_indent_details_tv_phone = (TextView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_phone);

		// 地址
		TextView item_present_indent_details_tv_addresse = (TextView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_addresse);

		// 开始时间
		TextView item_present_indent_details_tv_start_date = (TextView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_start_date);
		// 结束时间
		TextView item_present_indent_details_tv_end_date = (TextView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_end_date);
		// 时长
		TextView item_present_indent_details_tv_duration = (TextView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_duration);
		// 特卫人数
		TextView item_present_indent_details_tv_number = (TextView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_number);
		// 级别
		TextView item_present_indent_details_tv_level = (TextView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_level);
		// 捎一句
		TextView item_present_indent_details_tv_say = (TextView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_say);

		// 价格
		TextView item_present_indent_details_tv_price = (TextView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_price);

		// 按钮
		final TextView item_present_indent_details_tv_money = (TextView) popupWindow
				.findViewById(R.id.item_present_indent_details_tv_money);
		if (s.equals("查看详情") || s.equals("已支付"))
		{
			System.out.println(s);
			item_present_indent_details_tv_money.setText("返回");
			item_present_indent_details_tv_money.setBackgroundDrawable(context
					.getResources().getDrawable(
							R.drawable.item_present_indent_details_cancel));
		} else
		{
			System.out.println(s);
			item_present_indent_details_tv_money.setText("去付款");
			item_present_indent_details_tv_money.setBackgroundDrawable(context
					.getResources().getDrawable(
							R.drawable.item_present_indent_details));
		}
		item_present_indent_details_tv_money
				.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						if (item_present_indent_details_tv_money.getText()
								.equals("返回"))
						{
							mPopupWindow.setOutsideTouchable(false);
							mPopupWindow.dismiss();
						} else
						{
							// Toast.makeText(context, "支付", Toast.LENGTH_LONG)
							// .show();
							Intent intent = new Intent(context, ActPays.class);
							Bundle bundle = new Bundle();
							bundle.putString("dddid",
									String.valueOf(indent.getId()));
							bundle.putInt("id", i);
							bundle.putString("names", indent.getLevel());
							//
							// bundle.putString(
							// "prices",
							// indent.getFull_price()
							// .substring(
							// 0,
							// indent.getFull_price()
							// .indexOf("元")));
							bundle.putString("prices",
									String.valueOf(indent.getPrice()));
							bundle.putString("i", "PresentIndentAdapter");
							intent.putExtras(bundle);
							activity.startActivityForResult(intent, 10);
						}
					}
				});
		item_present_indent_details_tv_cancel
				.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View arg0)
					{
						// TODO Auto-generated method stub
						mPopupWindow.dismiss();
					}
				});

		item_present_indent_details_tv_name.setText(indent.getGuest_name());
		item_present_indent_details_tv_phone.setText(indent
				.getGuest_phone_number());
//		item_present_indent_details_tv_addresse.setText(indent.getCity() + ","
//				+ indent.getAddress());
		item_present_indent_details_tv_start_date.setText(indent
				.getService_at());
		item_present_indent_details_tv_end_date.setText(indent.getFinish_at());
		item_present_indent_details_tv_duration.setText(indent.getDuration());
		item_present_indent_details_tv_number.setText(indent.getPeople_count());
		item_present_indent_details_tv_level.setText(indent.getLevel());
		item_present_indent_details_tv_say.setText(indent.getWords());
		// 优惠前
		// item_present_indent_details_tv_price.setText(indent.getFull_price());
		// 优惠后
		item_present_indent_details_tv_price.setText(indent.getCurrent_price());

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

		mPopupWindowWidth = mPopupWindow.getWidth();
		mPopupWindowHeight = mPopupWindow.getHeight();
	}

	class ViewHolder
	{
		private TextView item_present_indent_tv_state;
		private TextView id;
		private TextView item_present_indent_tv_level;
		private TextView item_city_tv_start_date;
		private TextView item_city_tv_end_date;
		private TextView item_city_tv_duration;
		private TextView item_city_tv_people_count;
		private TextView item_city_tv_address;
		private TextView item_city_tv_Money;
		private View item_present_indent_tv_false;
		private View item_present_indent_tv_falses;
		private View item_present_indent_tv_falsess;
		private Button item_present_indent_tv_click;
		private TextView item_city_tv_ss;
	}

}

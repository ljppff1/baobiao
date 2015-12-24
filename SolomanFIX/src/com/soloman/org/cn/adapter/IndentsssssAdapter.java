package com.soloman.org.cn.adapter;


import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.IndentsAdapter.ViewHolder;
import com.soloman.org.cn.bean.Indent;
import com.soloman.org.cn.bean.User;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.indent.ActIndentsInfo;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.NetworkJudge;

@SuppressLint("ResourceAsColor")
public class IndentsssssAdapter extends BaseAdapter
{
	private ArrayList<Indent> lt;
	private Context context;

	public IndentsssssAdapter(ArrayList<Indent> lt, Context context)
	{
		super();
		this.lt = lt;
		this.context = context;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return lt.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return lt.get(position);
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
					R.layout.item_indent, null);
			holder.item_tv_task_order_id = (TextView) convertView
					.findViewById(R.id.item_tv_task_order_id);

			holder.item_tv_task_order_ids = (TextView) convertView
					.findViewById(R.id.item_tv_task_order_ids);

			holder.item_tv_task_type = (TextView) convertView
					.findViewById(R.id.item_tv_task_type);

			holder.item_tv_task_date = (TextView) convertView
					.findViewById(R.id.item_tv_task_date);

			holder.item_tv_task_money = (TextView) convertView
					.findViewById(R.id.item_tv_task_money);

			holder.item_tv_task_start_date = (TextView) convertView
					.findViewById(R.id.item_tv_task_start_date);

			holder.item_tv_task_end_date = (TextView) convertView
					.findViewById(R.id.item_tv_task_end_date);

			holder.item_rl_task_click = (RelativeLayout) convertView
					.findViewById(R.id.item_rl_task_click);
			holder.item_rl_task_clicks = (RelativeLayout) convertView
					.findViewById(R.id.item_rl_task_clicks);
			holder.item_tv_task_renshu = (TextView) convertView
					.findViewById(R.id.item_tv_task_renshu);
			holder.item_indent = (TextView) convertView
					.findViewById(R.id.item_indent);
			holder.item_indents = (LinearLayout) convertView
					.findViewById(R.id.item_indents);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		final Indent indent = lt.get(position);
		holder.item_tv_task_order_id.setText("订单 " + indent.getId());
		if (indent.getOrder_status() == 5)
		{
			holder.item_rl_task_click.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.fra_task));
			// 查看详情
			holder.item_indent.setText("查看详情");

		} else if (indent.getOrder_status() == 4)
		{
			// 已完成
			if (indent.getCan_comment().equals("true"))
			{
				holder.item_rl_task_click.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.fra_task));// 评论
				holder.item_indent.setText("去评论");
			} else
			{
				holder.item_rl_task_click.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.fra_task));
				// 查看详情
				holder.item_indent.setText("查看详情");
			}
		} else
		{
			if (indent.getHas_paid() == 1)
			{
				// 已付款
				holder.item_rl_task_click.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.item_indentss));
				holder.item_indent.setText("已付款");
			} else
			{
				holder.item_rl_task_click.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.fra_task));
				// 支付
				holder.item_indent.setText("支付");
			}
		}
		if (indent.getCan_cancel().equals("true"))
		{
			holder.item_rl_task_clicks.setVisibility(View.VISIBLE);
		} else
		{
			holder.item_rl_task_clicks.setVisibility(View.GONE);
		}
		if (indent.getOrder_status() == 1)
		{
			holder.item_tv_task_order_ids.setText("待付款");
		} else if (indent.getOrder_status() == 2)
		{
			holder.item_tv_task_order_ids.setText("待履行");
		} else if (indent.getOrder_status() == 3)
		{
			holder.item_tv_task_order_ids.setText("履行中");
		} else if (indent.getOrder_status() == 4)
		{
			holder.item_tv_task_order_ids.setText("已完成");
		} else if (indent.getOrder_status() == 5)
		{
			holder.item_tv_task_order_ids.setText("已取消");
		}
		holder.item_tv_task_type.setText("服务类型 : "
				+ indent.getBodyguard_level());
		holder.item_tv_task_date.setText("服务时长 : " + indent.getDuration());
		holder.item_tv_task_start_date.setText(indent.getService_at());
		holder.item_tv_task_end_date.setText(indent.getFinish_at());
		holder.item_tv_task_renshu.setText("服务保镖:" + indent.getPeople_count()
				+ "人");
		holder.item_tv_task_money.setText("￥" + indent.getCurrent_price());

		holder.item_rl_task_click.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (holder.item_indent.getText().equals("已付款"))
				{
					return;
				} else if (holder.item_indent.getText().equals("去评论"))
				{

				} else if (holder.item_indent.getText().equals("查看详情"))
				{

				}
				System.out.println("aaaaaaaaaaaaaaaa");

			}
		});
		holder.item_indents.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, ActIndentsInfo.class);
				context.startActivity(intent);
			}
		});
		holder.item_rl_task_clicks.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				lt.remove(position);
				Indent indents = indent;
				System.out.println(indents.getOrder_status());
				System.out.println(indents.getService_at());
				indents.setOrder_status(5);
				indents.setCan_cancel("false");
				System.out.println(indents.getOrder_status());
				System.out.println(indents.getService_at());
				lt.add(position, indents);
				holder.item_rl_task_clicks.setVisibility(View.GONE);
				holder.item_indent.setText("查看详情");
				holder.item_tv_task_order_ids.setText("已取消");
				Toast.makeText(context, "取消订单成功", Toast.LENGTH_LONG).show();
				holder.item_rl_task_click.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.fra_task));

				// TODO Auto-generated method stub
				RequestParams requestParams = new RequestParams();
				requestParams.put("order_id", indent.getId() + "");
				HttpRestClient.postHttpHuaShangha(context,
						"pays/tenpay_refund", "v2", requestParams,
						new JsonHttpResponseHandler()
						{
							@Override
							public void onSuccess(JSONObject response)
							{
								// TODO Auto-generated method stub
								super.onSuccess(response);
							}
						});

				// // 取消订单
				// Netroid.getApiUrl(
				// true,
				// "pays/tenpay_refund",
				// Method.POST,
				// Netroid.getInstance()
				// .returnURL(
				// "pays/tenpay_refund?order_id="
				// + indent.getId(), "v2"), null,
				// new Listener<JSONObject>()
				// {
				// public void onSuccess(JSONObject response)
				// {
				// holder.item_rl_task_clicks
				// .setVisibility(View.GONE);
				// holder.item_indent.setText("查看详情");
				// Toast.makeText(context, "取消订单成功",
				// Toast.LENGTH_LONG).show();
				// }
				//
				// @Override
				// public void onError(NetroidError error)
				// {
				// Toast.makeText(context, "数据加载失败",
				// Toast.LENGTH_LONG).show();
				// }
				//
				// @Override
				// public void onCancel()
				// {
				// super.onCancel();
				// }
				// });
			}
		});
		return convertView;

	}

	class ViewHolder
	{
		/**
		 * 订单id
		 */
		private TextView item_tv_task_order_id;
		/**
		 * 订单状态
		 */
		private TextView item_tv_task_order_ids;
		/**
		 * 服务类型
		 */
		private TextView item_tv_task_type;
		/**
		 * 服务时长
		 */
		private TextView item_tv_task_date;
		/**
		 * 订单金额
		 */
		private TextView item_tv_task_money;
		/**
		 * 开始时间
		 */
		private TextView item_tv_task_start_date;
		/**
		 * 结束时间
		 */
		private TextView item_tv_task_end_date;
		/**
		 * 付款
		 */
		private RelativeLayout item_rl_task_click;

		/**
		 * 取消订单
		 */

		private RelativeLayout item_rl_task_clicks;
		/**
		 * 人数
		 */
		private TextView item_tv_task_renshu;

		private TextView item_indent;
		private LinearLayout item_indents;
	}

}

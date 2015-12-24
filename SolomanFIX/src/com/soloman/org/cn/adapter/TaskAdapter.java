package com.soloman.org.cn.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.Bodyguard;
import com.soloman.org.cn.bean.Indent;
import com.soloman.org.cn.bean.User;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.view.RoundImageView;

/**
 * 任务分类
 * 
 * @author Mac
 * 
 */
public class TaskAdapter extends BaseAdapter
{
	private ArrayList<Indent> lt;

	private Context context;
	ImageLoader imageLoader;

	public TaskAdapter(ArrayList<Indent> lt, Context context)
	{
		super();
		this.lt = lt;
		this.context = context;
		imageLoader = ImageLoader.getInstance();
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
					R.layout.item_task, null);
			holder.item_tv_task_order_id = (TextView) convertView
					.findViewById(R.id.item_tv_task_order_id);
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
			holder.fra_individual_date_rlv_ = (RoundImageView) convertView
					.findViewById(R.id.fra_individual_date_rlv_);
			holder.item_tv_task_name = (TextView) convertView
					.findViewById(R.id.item_tv_task_name);
			holder.item_rl_task_click = (RelativeLayout) convertView
					.findViewById(R.id.item_rl_task_click);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		final Indent indent = lt.get(position);
		holder.item_tv_task_order_id.setText("订单 " + indent.getId());
		holder.item_tv_task_type.setText("服务类型 : "
				+ indent.getBodyguard_level());
		holder.item_tv_task_date.setText("服务时长 : " + indent.getDuration());
		holder.item_tv_task_money.setText("￥" + indent.getCurrent_price());
		holder.item_tv_task_start_date.setText(indent.getService_at());
		holder.item_tv_task_end_date.setText(indent.getFinish_at());

		imageLoader.displayImage("http://7xnd0k.com2.z0.glb.qiniucdn.com/"
				+ indent.getUser().getAvatar_image_key(),
				holder.fra_individual_date_rlv_);
		holder.item_tv_task_name.setText(indent.getUser().getName());

		holder.item_rl_task_click.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				RequestParams requestParams = new RequestParams();
				requestParams.put("order_id", indent.getId() + "");
				HttpRestClient.postHttpHuaShangha(context,
						"bodyguards/recommend", "v2", requestParams,
						new JsonHttpResponseHandler()
						{
							@Override
							public void onSuccess(JSONObject response)
							{
								// TODO Auto-generated method stub
								super.onSuccess(response);
								
									lt.remove(position);
									TaskAdapter.this.notifyDataSetChanged();
							}
						});
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
		 * 头像
		 */
		private RoundImageView fra_individual_date_rlv_;
		/**
		 * 客户名称
		 */
		private TextView item_tv_task_name;
		/**
		 * 接单
		 */
		private RelativeLayout item_rl_task_click;
	}
}

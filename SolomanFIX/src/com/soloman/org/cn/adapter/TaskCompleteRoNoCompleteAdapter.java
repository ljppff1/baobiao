package com.soloman.org.cn.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.TaskAdapter.ViewHolder;
import com.soloman.org.cn.bean.Indent;
import com.soloman.org.cn.view.RoundImageView;
/**
 * 完成任务ro未完成
 * @author Mac
 *
 */
public class TaskCompleteRoNoCompleteAdapter extends BaseAdapter
{
	private ArrayList<String> city;

	private Context context;

	public TaskCompleteRoNoCompleteAdapter(ArrayList<String> lt, Context context)
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
					R.layout.item_task, null);
			holder.item_tv_task_order_id = (TextView) convertView
					.findViewById(R.id.item_city_tv);

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
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		// holder.item_tv_task_order_id.setText(city.get(position).toString());
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
	}

}

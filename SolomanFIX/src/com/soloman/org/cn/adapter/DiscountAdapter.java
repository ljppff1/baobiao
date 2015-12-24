package com.soloman.org.cn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.Discount;

/**
 * 优惠券
 * 
 * @author Mac
 * 
 */
public class DiscountAdapter extends BaseAdapter
{
	private List<Discount> city;

	private Context context;

	public DiscountAdapter(List<Discount> lt, Context context)
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
					R.layout.item_disount, null);
			holder.item_tv = (TextView) convertView.findViewById(R.id.item_tv);
			holder.item_tv1 = (TextView) convertView
					.findViewById(R.id.item_tv1);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		Discount discount = city.get(position);
		holder.item_tv.setText("Soloman" + discount.getValue() + "元代金券x1张");
		holder.item_tv1.setText(discount.getExpect_at() + "到期");
		return convertView;
	}

	class ViewHolder
	{
		private TextView item_tv;
		private TextView item_tv1;
	}
}

package com.soloman.org.cn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.soloman.org.cn.R;

public class CommonMapTextAdapter extends BaseAdapter
{
	private List<String> city;

	private Context context;

	public CommonMapTextAdapter(List<String> city, Context context)
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
					R.layout.route_inputs, null);
			holder.item_common_map_name = (TextView) convertView
					.findViewById(R.id.online_user_list_item_textview);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.item_common_map_name.setText(city.get(position));
		return convertView;
	}

	class ViewHolder
	{

		private TextView item_common_map_name;

	}
}

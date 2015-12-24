package com.soloman.org.cn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.CommonAddress;

public class CommonMapAdapter extends BaseAdapter
{
	private List<PoiItem> city;

	private Context context;

	public CommonMapAdapter(List<PoiItem> city, Context context)
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
					R.layout.item_common_map, null);
			holder.item_common_map_name = (TextView) convertView
					.findViewById(R.id.item_common_map_name);
			holder.item_common_map_address = (TextView) convertView
					.findViewById(R.id.item_common_map_address);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		final PoiItem address = city.get(position);
		holder.item_common_map_name.setText(address.getTitle());
		holder.item_common_map_address.setText(address.getSnippet());
		return convertView;
	}

	class ViewHolder
	{

		private TextView item_common_map_name;
		private TextView item_common_map_address;

	}
}

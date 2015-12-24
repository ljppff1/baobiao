package com.soloman.org.cn.adapter;

import java.util.ArrayList;

import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.TaskAdapter.ViewHolder;
import com.soloman.org.cn.bean.Customer;
import com.soloman.org.cn.view.RoundImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 开通的城市
 * 
 * @author Mac
 * 
 */
public class CityAdapter extends BaseAdapter
{
	
	private ArrayList<String> city;

	private Context context;

	public CityAdapter(ArrayList<String> lt, Context context)
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
					R.layout.item_city, null);
			holder.item_city_tv = (TextView) convertView
					.findViewById(R.id.item_city_tv);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
//		for (int i = 0; i < city.size(); i++)
//		{
			holder.item_city_tv.setText(city.get(position).toString());
//		}
		return convertView;
	}

	class ViewHolder
	{

		private TextView item_city_tv;
	}

}

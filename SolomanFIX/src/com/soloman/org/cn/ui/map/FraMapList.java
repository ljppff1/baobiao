package com.soloman.org.cn.ui.map;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.R;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.NetworkJudge;

/**
 * 地图列表
 * 
 * @author Mac
 * 
 */
public class FraMapList extends Fragment
{
	private ListView fra_lv_map_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fra_former_indent, container,
				false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		setupView(view);
		initBodyguardRequest();
	}

	private void setupView(View view)
	{
		// TODO Auto-generated method stub
		fra_lv_map_list = (ListView) view.findViewById(R.id.fra_lv_map_list);
	}

	/**
	 * 请求保镖类型
	 */
	private void initBodyguardRequest()
	{
//		boolean is = false;
//		if (NetworkJudge.getNetWorkType(getActivity()) != NetworkJudge.NETWORKTYPE_INVALID)
//		{
//			is = true;
//		}
//		Netroid.getApiUrl(
//				is,
//				"FraMapList",
//				Method.GET,
//				"http://yuntuapi.amap.com/datasearch/local?tableid=55ed6884e4b0c074efd83c1c&key=227b8bf3487970487ce09b44099794ce&city="
//						+ URLEncoder.encode("深圳")
//						+ "&keywords=&limit=100000&page=1", null,
//				new Listener<JSONObject>()
//				{
//					public void onSuccess(JSONObject response)
//					{
//						System.out.println(response);
//						JSONObject obj = (JSONObject) response;
//						JSONArray array;
//						// try
//						// {
//						// } catch (JSONException e)
//						// {
//						// // TODO Auto-generated catch block
//						// e.printStackTrace();
//						// }
//					}
//
//					@Override
//					public void onError(NetroidError error)
//					{
//					}
//
//					@Override
//					public void onCancel()
//					{
//						super.onCancel();
//					}
//				});
	}

}

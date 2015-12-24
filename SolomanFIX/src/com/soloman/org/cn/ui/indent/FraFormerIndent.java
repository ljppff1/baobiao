package com.soloman.org.cn.ui.indent;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.PresentIndentAdapter;
import com.soloman.org.cn.bean.Indent;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.pay.ActPays;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.NetworkJudge;
import com.soloman.org.cn.view.XListView;
import com.soloman.org.cn.view.XListView.IXListViewListener;

/**
 * 历史订单
 * 
 * @author Mac
 * 
 */
public class FraFormerIndent extends Fragment implements IXListViewListener
{
	private int isa = 1;
	// 刷新页数
	private int i = 1;
	private XListView xListView;
	public static ArrayList<Indent> lt;
	public static PresentIndentAdapter adapter;
	private TextView act_iv_indext;
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fra_present_indext, null);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setUpView(view);
		RequestMore(i);
	}

	private void setUpView(View v)
	{
		lt = new ArrayList<Indent>();
		act_iv_indext = (TextView) v.findViewById(R.id.act_iv_indext);
		xListView = (XListView) v.findViewById(R.id.xListView);
		xListView.setPullLoadEnable(true);
		adapter = new PresentIndentAdapter(lt, getActivity());
		xListView.setAdapter(adapter);
		// xListView.setPullLoadEnable(false);
		// xListView.setPullRefreshEnable(false);
		xListView.setXListViewListener(this);
		System.out.println("进入FraFormerIndent");
	}

	private void onLoad()
	{
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime("刚刚");
	}

	// 下啦刷新
	@Override
	public void onRefresh()
	{
		i = 1;
		RequestMore(i);
	}

	// 上啦加载
	@Override
	public void onLoadMore()
	{
		RequestMore(++i);
	}

	private void RequestMore(int iss)
	{
//		boolean is = false;
//		if (NetworkJudge.getNetWorkType(getActivity()) != NetworkJudge.NETWORKTYPE_INVALID)
//		{
//			is = true;
//		}
		RequestParams requestParams = new RequestParams();
		requestParams.put("page",iss + "");
		HttpRestClient.getHttpHuaShangha(getActivity(),
				"orders/history", "v2", requestParams,
				new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						// 清除必须放这里否则刷新界面時数据出现类似闪现效果
						if (i == 1)
						{
							lt.clear();
						}
						try
						{
							if (response.getJSONArray("orders").toString()
									.equals("[]")
									&& isa == 1)
							{
								// 可见
								act_iv_indext.setVisibility(View.VISIBLE);
								xListView.setVisibility(View.GONE);
							}
							if (response.getJSONArray("orders").toString()
									.equals("[]")
									&& isa > 1)
							{
								Toast.makeText(getActivity(), "没有更多订单",
										Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						isa += 1;
						System.out.println("当前订单          " + response);
						JSONObject obj = response;
						String newStr;
						try
						{
							JSONArray array = obj.getJSONArray("orders");
							for (int i = 0; i < array.length(); i++)
							{
								Indent indent = new Indent();
								JSONObject jdatas = array.getJSONObject(i);
								indent.setAddress(jdatas.getString("address"));
//								indent.setCity(jdatas.getString("city"));
								indent.setCreated_at(jdatas
										.getString("created_at"));
								indent.setCurrent_price(jdatas
										.getString("current_price"));
								indent.setDiff_price(jdatas
										.getString("diff_price"));

								String finish_at = jdatas
										.getString("finish_at");
								newStr = finish_at.substring(0,
										finish_at.indexOf("T"))
										+ " "
										+ finish_at.substring(
												finish_at.indexOf("T") + 1,
												finish_at.indexOf("."));

								indent.setFinish_at(newStr);
								indent.setFull_price(jdatas
										.getString("full_price"));
								indent.setGuest_name(jdatas
										.getString("guest_name"));
								indent.setGuest_phone_number(jdatas
										.getString("guest_phone_number"));
								indent.setHas_paid(jdatas.getInt("has_paid"));
								indent.setId(jdatas.getInt("id"));
								indent.setLevel(jdatas.getString("level"));
								indent.setPeople_count(jdatas
										.getString("people_count"));
								indent.setPrice(jdatas.getInt("price"));
								String service_at = jdatas
										.getString("service_at");
								newStr = service_at.substring(0,
										service_at.indexOf("T"))
										+ " "
										+ service_at.substring(
												service_at.indexOf("T") + 1,
												service_at.indexOf("."));
								indent.setService_at(newStr);

								indent.setStatus(jdatas.getInt("status"));
								indent.setUpdated_at(jdatas
										.getString("updated_at"));
								indent.setWords(jdatas.getString("words"));
								indent.setDuration(jdatas.getString("duration"));
								lt.add(indent);
							}
							adapter.notifyDataSetChanged();
							onLoad();
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

	}
}
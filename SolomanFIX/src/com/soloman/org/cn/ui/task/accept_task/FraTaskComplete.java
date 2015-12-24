package com.soloman.org.cn.ui.task.accept_task;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.TaskAdapter;
import com.soloman.org.cn.adapter.TaskCompleteRoNoCompleteAdapter;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.NetworkJudge;
import com.soloman.org.cn.view.XListView;
import com.soloman.org.cn.view.XListView.IXListViewListener;

/**
 * 完成的任务
 * 
 * @author Mac
 * 
 */
public class FraTaskComplete extends Fragment implements IXListViewListener
{
	private int i = 1;
	private XListView xListView;
	public static ArrayList<String> lt;
	private TaskCompleteRoNoCompleteAdapter complete;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fra_task_complete, container,
				false);
		// host = (ActHosts) getActivity();
		setupView(view);
		// setListener();
		return view;
	}

	private void setupView(View v)
	{
		// TODO Auto-generated method stub
		lt = new ArrayList<String>();
		xListView = (XListView) v.findViewById(R.id.xListView);
		complete = new TaskCompleteRoNoCompleteAdapter(lt, getActivity());
		xListView.setAdapter(complete);
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

	private void onLoad()
	{
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime("刚刚");
	}

	private void RequestMore(int iss)
	{
		// boolean is = false;
		// if (NetworkJudge.getNetWorkType(getActivity()) !=
		// NetworkJudge.NETWORKTYPE_INVALID)
		// {
		// is = true;
		// }
		RequestParams requestParams = new RequestParams();
		requestParams.put("page", iss + "");
		HttpRestClient.getHttpHuaShangha(getActivity(), "orders/history", "v2",
				requestParams, new JsonHttpResponseHandler()
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
						JSONObject obj = response;
						complete.notifyDataSetChanged();
						onLoad();

					}
				});
	}
}

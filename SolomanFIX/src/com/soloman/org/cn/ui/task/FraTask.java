package com.soloman.org.cn.ui.task;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.TaskAdapter;
import com.soloman.org.cn.bean.Bodyguard;
import com.soloman.org.cn.bean.Indent;
import com.soloman.org.cn.bean.User;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.sliding.ActHosts;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.NetworkJudge;
import com.soloman.org.cn.view.XListView;
import com.soloman.org.cn.view.XListView.IXListViewListener;

/**
 * 任务
 * 
 * @author Mac
 * 
 */
public class FraTask extends Fragment implements OnClickListener,
		IXListViewListener
{
	private ArrayList<String> commented_bodyguards;
	public ArrayList<Indent> lt;
	private ArrayList<Bodyguard> bodyguard;
	private int i = 1;
	private TaskAdapter task;
	private ImageView fra_iv_viewss;
	private XListView xListView;
	ActHosts host;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fra_task, container, false);
		host = (ActHosts) getActivity();
		setupView(view);
		setListener();
		RequestMore(1);
		return view;
	}

	private void setupView(View v)
	{
		// TODO Auto-generated method stub
		lt = new ArrayList<Indent>();
		fra_iv_viewss = (ImageView) v.findViewById(R.id.fra_iv_viewss);
		xListView = (XListView) v.findViewById(R.id.xListView);
		xListView.setPullLoadEnable(true);
		// xListView.setPullLoadEnable(false);
		// xListView.setPullRefreshEnable(false);
		xListView.setXListViewListener(this);
		task = new TaskAdapter(lt, getActivity());
		xListView.setAdapter(task);
	}

	private void setListener()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.fra_iv_viewss:
			host.sm.toggle();
			break;

		default:
			break;
		}
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
		HttpRestClient.getHttpHuaShangha(getActivity(),
				"bodyguards/recommend_orders", "v2", requestParams,
				new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						// 清除必须放这里否则刷新界面時数据出现类似闪现效果
						try
						{
							if (response.getString("code").equals("200"))
							{
								if (i == 1)
								{
									lt.clear();
								}
								JSONObject obj = response.getJSONObject("data");
								JSONArray array = obj.getJSONArray("orders");
								for (int i = 0; i < array.length(); i++)
								{
									JSONObject jsonbje = array.getJSONObject(i);
									Indent indent = new Indent();
									indent.setId(jsonbje.getInt("id"));
									indent.setAddress_info(jsonbje
											.getString("address_info"));

									indent.setAddress_complex(jsonbje
											.getString("address_complex"));
									indent.setGuest_name(jsonbje
											.getString("guest_name"));
									indent.setGuest_phone_number(jsonbje
											.getString("guest_phone_number"));
									indent.setGuest_sex(jsonbje
											.getString("guest_sex"));
									indent.setService_at(jsonbje
											.getString("service_at"));
									indent.setWords(jsonbje.getString("words"));
									indent.setFinish_at(jsonbje
											.getString("finish_at"));
									indent.setLevel(jsonbje.getString("level"));
									indent.setPrice(jsonbje.getInt("price"));
									indent.setCurrent_price(jsonbje
											.getString("current_price"));
									indent.setFull_price(jsonbje
											.getString("full_price"));
									indent.setDiff_price(jsonbje
											.getString("diff_price"));
									indent.setDuration(jsonbje
											.getString("duration"));
									indent.setHas_paid(jsonbje
											.getInt("has_paid"));
									indent.setPeople_count(jsonbje
											.getString("people_count"));
									indent.setMap_id(jsonbje
											.getString("map_id"));
									indent.setBodyguard_level(jsonbje
											.getString("bodyguard_level"));
									indent.setCan_comment(jsonbje
											.getString("can_comment"));
									indent.setCan_cancel(jsonbje
											.getString("can_cancel"));
									indent.setOrder_status(jsonbje
											.getInt("order_status"));
									indent.setImage_qn_key(jsonbje
											.getString("image_qn_key"));
									indent.setBodyguard_reward(jsonbje
											.getString("bodyguard_reward"));
									JSONObject json = jsonbje
											.getJSONObject("user");
									User user = new User();
									user.setId(json.getInt("id"));
									user.setName(json.getString("name"));
									user.setPhone_number(json
											.getString("phone_number"));
									user.setSex(json.getInt("sex"));
									user.setLevel(json.getInt("level"));
									user.setCity(json.getString("city"));
									user.setDescription(json
											.getString("description"));
									user.setAvatar_image_key(json
											.getString("avatar_image_key"));
									user.setBirthday(json.getString("birthday"));
									user.setHeight(json.getString("height"));
									user.setWeight(json.getString("weight"));
									user.setCertificate_type(json
											.getString("certificate_type"));
									user.setCertificate_number(json
											.getString("certificate_number"));
									user.setMap_id(json.getString("map_id"));
									user.setLocation(json.getString("location"));
									user.setUser_id(json.getInt("user_id"));
									user.setIs_bodyguard(json
											.getBoolean("is_bodyguard"));
									indent.setUser(user);

									// 获取已评论的保镖
									commented_bodyguards = new ArrayList<String>();
									JSONArray bodyguards = jsonbje
											.getJSONArray("commented_bodyguards");
									for (int j = 0; j < bodyguards.length(); j++)
									{
										commented_bodyguards.add(bodyguards
												.getString(j) + "");
									}
									// 参与的保镖
									indent.setCommented_bodyguards(commented_bodyguards);
									bodyguard = new ArrayList<Bodyguard>();
									JSONArray bodyguardss = jsonbje
											.getJSONArray("bodyguards");
									for (int j = 0; j < bodyguardss.length(); j++)
									{
										Bodyguard bodyguarf = new Bodyguard();
										JSONObject object = bodyguardss
												.getJSONObject(j);
										bodyguarf.setId(object.getInt("id"));
										bodyguarf.setName(object
												.getString("name"));

										bodyguarf.setPhone_number(object
												.getString("phone_number"));
										bodyguarf.setSex(object.getInt("sex"));
										bodyguarf.setLevel(object
												.getInt("level"));
										bodyguarf.setCity(object
												.getString("city"));
										bodyguarf.setDescription(object
												.getString("description"));
										bodyguarf.setAvatar_image_key(object
												.getString("avatar_image_key"));
										bodyguarf.setBirthday(object
												.getString("birthday"));
										bodyguarf.setHeight(object
												.getString("height"));
										bodyguarf.setWeight(object
												.getString("weight"));
										bodyguarf.setCertificate_type(object
												.getString("certificate_type"));
										bodyguarf.setCertificate_number(object
												.getString("certificate_number"));
										bodyguarf.setMap_id(object
												.getString("map_id"));
										bodyguarf.setLocation(object
												.getString("location"));
										bodyguarf.setUser_id(object
												.getString("user_id"));
										bodyguarf.setAddress(object
												.getString("address"));
										bodyguarf.setBank_name(object
												.getString("bank_name"));
										bodyguarf.setBank_branch_name(object
												.getString("bank_branch_name"));
										bodyguarf.setBank_account(object
												.getString("bank_account"));

										bodyguarf
												.setEmergency_contact_person_name(object
														.getString("emergency_contact_person_name"));
										bodyguarf
												.setEmergency_contact_person_phone_number(object
														.getString("emergency_contact_person_phone_number"));
										bodyguarf
												.setEmergency_contact_person_relationship(object
														.getString("emergency_contact_person_relationship"));
										bodyguarf.setCertificate_status(object
												.getString("certificate_status"));
										bodyguarf.setBank_status(object
												.getString("bank_status"));
										bodyguarf.setVerify_status(object
												.getString("verify_status"));
										bodyguarf
												.setCertificate_image_key(object
														.getString("certificate_image_key"));
										bodyguarf.setBank_image_key(object
												.getString("bank_image_key"));
										bodyguarf.setReceivable(object
												.getString("receivable"));
										bodyguarf.setGross_income(object
												.getString("gross_income"));
										bodyguarf
												.setServiced_people_count(object
														.getString("serviced_people_count"));
										bodyguarf.setServiced_time(object
												.getString("serviced_time"));
										bodyguard.add(bodyguarf);
									}
									indent.setBoy(bodyguard);
									lt.add(indent);

									task.notifyDataSetChanged();
								}
							} else
							{
								Toast.makeText(getActivity(),
										response.getString("message"),
										Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						onLoad();
					}

					@Override
					public void onFailure(Throwable e, JSONObject errorResponse)
					{
						// TODO Auto-generated method stub
						super.onFailure(e, errorResponse);
						System.out.println("aaaa");
					}
				});

	}
}

package com.soloman.org.cn.ui.indent.accept_indent;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.Bodyguard;
import com.soloman.org.cn.bean.Indent;
import com.soloman.org.cn.bean.User;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.appoint.ActAppoints;
import com.soloman.org.cn.ui.indent.ActComment;
import com.soloman.org.cn.ui.indent.ActIndentsInfo;
import com.soloman.org.cn.ui.pay.ActPays;
import com.soloman.org.cn.utis.wx.Constants;
import com.soloman.org.cn.utis.wx.MD5;
import com.soloman.org.cn.utis.zfb.PayResult;
import com.soloman.org.cn.utis.zfb.SignUtils;
import com.soloman.org.cn.view.XListView;
import com.soloman.org.cn.view.XListView.IXListViewListener;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 全部
 * 
 * @author Mac
 * 
 */
public class FraAllIndent extends Fragment implements IXListViewListener
{
	private ArrayList<String> commented_bodyguards;
	private ArrayList<Bodyguard> bodyguard;
	private int i = 1;
	private XListView xListView;
	public ArrayList<Indent> lt;
	private IndentAdapter complete;
	public static boolean AllindentIS = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fra_task_complete, container,
				false);
		// host = (ActHosts) getActivity();
		setupView(view);
		// setListener();
		RequestMore(1);
		return view;
	}

	private void setupView(View v)
	{
		// TODO Auto-generated method stub

		lt = new ArrayList<Indent>();
		xListView = (XListView) v.findViewById(R.id.xListView);
		complete = new IndentAdapter(lt, getActivity());
		xListView.setAdapter(complete);

		xListView.setPullLoadEnable(true);
		// xListView.setPullLoadEnable(false);
		// xListView.setPullRefreshEnable(false);
		xListView.setXListViewListener(this);
		// IntentFilter filter = new IntentFilter("data.broadcast.allindent");
		// getActivity().registerReceiver(broadcastReceiver, filter);
	}

	// BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
	// {
	//
	// @Override
	// public void onReceive(Context context, Intent intent)
	// {
	// // mContext.unregisterReceiver(this);
	// // TODO Auto-generated method stub
	// Bundle bundleReady = intent.getExtras();
	// for (int i = 0; i < lt.size(); i++)
	// {
	// if (bundleReady.getInt("id") == lt.get(i).getId())
	// {
	// lt.get(i).setOrder_status(5);
	// lt.get(i).setCan_cancel("false");
	// }
	// }
	// complete.notifyDataSetChanged();
	//
	// }
	// };

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
		requestParams.put("status", 0 + "");
		requestParams.put("page", iss + "");

		HttpRestClient.getHttpHuaShangha(getActivity(), "orders/index", "v2",
				requestParams, new JsonHttpResponseHandler()
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

									commented_bodyguards = new ArrayList<String>();
									JSONArray bodyguards = jsonbje
											.getJSONArray("commented_bodyguards");
									for (int j = 0; j < bodyguards.length(); j++)
									{
										commented_bodyguards.add(bodyguards
												.getString(j) + "");
									}
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
								}
								complete.notifyDataSetChanged();
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
						System.out.println("ssss");
					}
				});

	}

	public class IndentAdapter extends BaseAdapter
	{
		private ArrayList<Indent> lt;
		private Context context;

		public IndentAdapter(ArrayList<Indent> lt, Context context)
		{
			super();
			this.lt = lt;
			this.context = context;
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
						R.layout.item_indent, null);
				holder.item_tv_task_order_id = (TextView) convertView
						.findViewById(R.id.item_tv_task_order_id);

				holder.item_tv_task_order_ids = (TextView) convertView
						.findViewById(R.id.item_tv_task_order_ids);

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

				holder.item_rl_task_click = (RelativeLayout) convertView
						.findViewById(R.id.item_rl_task_click);
				holder.item_rl_task_clicks = (RelativeLayout) convertView
						.findViewById(R.id.item_rl_task_clicks);
				holder.item_tv_task_renshu = (TextView) convertView
						.findViewById(R.id.item_tv_task_renshu);
				holder.item_indent = (TextView) convertView
						.findViewById(R.id.item_indent);
				holder.item_indents = (LinearLayout) convertView
						.findViewById(R.id.item_indents);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			final Indent indent = lt.get(position);
			holder.item_tv_task_order_id.setText("订单 " + indent.getId());
			if (indent.getOrder_status() == 5)
			{
				holder.item_rl_task_click.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.fra_task));
				// 查看详情
				holder.item_indent.setText("查看详情");

			} else if (indent.getOrder_status() == 4)
			{
				// 已完成
				if (indent.getCan_comment().equals("true"))
				{
					holder.item_rl_task_click.setBackgroundDrawable(context
							.getResources().getDrawable(R.drawable.fra_task));// 评论
					holder.item_indent.setText("去评论");
				} else
				{
					holder.item_rl_task_click.setBackgroundDrawable(context
							.getResources().getDrawable(R.drawable.fra_task));
					// 查看详情
					holder.item_indent.setText("查看详情");
				}
			} else
			{
				if (indent.getHas_paid() == 1)
				{
					// 已付款
					holder.item_rl_task_click.setBackgroundDrawable(context
							.getResources().getDrawable(
									R.drawable.item_indentss));
					holder.item_indent.setText("已付款");
				} else
				{
					holder.item_rl_task_click.setBackgroundDrawable(context
							.getResources().getDrawable(R.drawable.fra_task));
					// 支付
					holder.item_indent.setText("支付");
				}
			}
			if (indent.getCan_cancel().equals("true"))
			{
				holder.item_rl_task_clicks.setVisibility(View.VISIBLE);
			} else
			{
				holder.item_rl_task_clicks.setVisibility(View.GONE);
			}
			if (indent.getOrder_status() == 1)
			{
				holder.item_tv_task_order_ids.setText("待付款");
			} else if (indent.getOrder_status() == 2)
			{
				holder.item_tv_task_order_ids.setText("待履行");
			} else if (indent.getOrder_status() == 3)
			{
				holder.item_tv_task_order_ids.setText("履行中");
			} else if (indent.getOrder_status() == 4)
			{
				holder.item_tv_task_order_ids.setText("已完成");
			} else if (indent.getOrder_status() == 5)
			{
				holder.item_tv_task_order_ids.setText("已取消");
			}
			holder.item_tv_task_type.setText("服务类型 : "
					+ indent.getBodyguard_level());
			holder.item_tv_task_date.setText("服务时长 : " + indent.getDuration());
			holder.item_tv_task_start_date.setText(indent.getService_at());
			holder.item_tv_task_end_date.setText(indent.getFinish_at());
			holder.item_tv_task_renshu.setText("服务保镖:"
					+ indent.getPeople_count() + "人");
			holder.item_tv_task_money.setText("￥" + indent.getCurrent_price());

			holder.item_rl_task_click.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					if (holder.item_indent.getText().equals("支付"))
					{
						Intent intents = new Intent(getActivity(), ActPays.class);
//						bundle.putString("name", name);
//						bundle.putString("info",info);
//						bundle.putString("complex",complex);
//						bundle.putString("phone", phone);
//						bundle.putString("act_tv_message", act_tv_message.getText()
//								.toString());
//						bundle.putString("id",id+"");
//						intents.putExtras(bundle);
						startActivity(intents);
						
						
					} else if (holder.item_indent.getText().equals("去评论"))
					{
						Intent intent = new Intent(getActivity(),
								ActComment.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("bodyguard", bodyguard);
						bundle.putSerializable("Commented_bodyguards",
								indent.getCommented_bodyguards());
						bundle.putString("id", indent.getId() + "");
						intent.putExtras(bundle);
						startActivity(intent);
					} else if (holder.item_indent.getText().equals("查看详情"))
					{
						Intent intent = new Intent(context,
								ActIndentsInfo.class);
						Bundle bundle = new Bundle();
						bundle.putString("id", indent.getId() + "");
						intent.putExtras(bundle);
						context.startActivity(intent);
					}
					System.out.println("aaaaaaaaaaaaaaaa");

				}
			});
			holder.item_indents.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, ActIndentsInfo.class);
					Bundle bundle = new Bundle();
					bundle.putString("id", indent.getId() + "");
					intent.putExtras(bundle);
					context.startActivity(intent);
				}
			});
			holder.item_rl_task_clicks.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					RequestParams requestParams = new RequestParams();
					requestParams.put("order_id", indent.getId() + "");
					HttpRestClient.postHttpHuaShangha(context,
							"pays/tenpay_refund", "v2", requestParams,
							new JsonHttpResponseHandler()
							{
								@Override
								public void onSuccess(JSONObject response)
								{
									// TODO Auto-generated method stub
									super.onSuccess(response);
									Indent indents = indent;
									indents.setOrder_status(5);
									indents.setCan_cancel("false");
									lt.remove(position);
									lt.add(position, indents);
									holder.item_rl_task_clicks
											.setVisibility(View.GONE);
									holder.item_indent.setText("查看详情");
									holder.item_tv_task_order_ids
											.setText("已取消");
									Toast.makeText(context, "取消订单成功",
											Toast.LENGTH_LONG).show();
									holder.item_rl_task_click
											.setBackgroundDrawable(context
													.getResources()
													.getDrawable(
															R.drawable.fra_task));

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
			 * 订单状态
			 */
			private TextView item_tv_task_order_ids;
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
			 * 付款
			 */
			private RelativeLayout item_rl_task_click;

			/**
			 * 取消订单
			 */

			private RelativeLayout item_rl_task_clicks;
			/**
			 * 人数
			 */
			private TextView item_tv_task_renshu;

			private TextView item_indent;
			private LinearLayout item_indents;
		}
	}

}

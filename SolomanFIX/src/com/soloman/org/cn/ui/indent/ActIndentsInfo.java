package com.soloman.org.cn.ui.indent;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.Bodyguard;
import com.soloman.org.cn.bean.Indent;
import com.soloman.org.cn.bean.User;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.view.XListView;

/**
 * V2订单详情
 * 
 * @author Mac
 * 
 */
public class ActIndentsInfo extends Activity
{
	private ArrayList<String> commented_bodyguards;
	private TextView act_indents_s;
	public ArrayList<Indent> lt;
	private ArrayList<Bodyguard> bodyguard;
	// 返回
	private ImageView act_iv_viewss;
	// 被服务的人
	private TextView act_indents_name;
	// 电话号码
	private TextView act_indents_phone;
	// 地址
	private TextView act_indents_address;
	// 订单状态
	private TextView act_indents_state;
	// 服务类型
	private TextView act_tv_type;
	// 服务时长
	private TextView act_tv_date;
	// 开始时间
	private TextView act_tv_date2;
	// 特卫级别
	private View[] view;
	private ImageView act_iv_special_level;
	private ImageView act_iv_special_levels;
	private ImageView act_iv_special_levelss;
	private ImageView act_iv_special_levelsss;
	private ImageView act_iv_special_levelssss;
	// 特卫人数
	private TextView act_tv_number;
	// 与参与的保镖
	private XListView xListView;
	// 优惠价格
	private TextView act_tv_discount_price;
	// 实付价格
	private TextView act_tv_true_price;
	// 订单id
	private TextView act_tv_indents_order_id;
	// 下单时间
	private TextView act_tv_indents_order_date;
	// 捎一句
	private TextView act_tv_indents_word;
	// 取消订单
	private RelativeLayout act_tv_indents;
	// 付款
	private RelativeLayout act_tv_indentss;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_indents_info);
		MyApplication.getInstance().addActivity(this);
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("id");
		setupView();
		RequestMore();
	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		act_indents_s = (TextView) findViewById(R.id.act_indents_s);
		lt = new ArrayList<Indent>();
		act_iv_viewss = (ImageView) findViewById(R.id.act_iv_viewss);
		act_indents_name = (TextView) findViewById(R.id.act_indents_name);
		act_indents_phone = (TextView) findViewById(R.id.act_indents_phone);
		act_indents_address = (TextView) findViewById(R.id.act_indents_address);
		act_indents_state = (TextView) findViewById(R.id.act_indents_state);
		act_tv_type = (TextView) findViewById(R.id.act_tv_type);
		act_tv_date = (TextView) findViewById(R.id.act_tv_date);
		act_tv_date2 = (TextView) findViewById(R.id.act_tv_date2);
		act_iv_special_level = (ImageView) findViewById(R.id.act_iv_special_level);
		act_iv_special_levels = (ImageView) findViewById(R.id.act_iv_special_levels);
		act_iv_special_levelss = (ImageView) findViewById(R.id.act_iv_special_levelss);
		act_iv_special_levelsss = (ImageView) findViewById(R.id.act_iv_special_levelsss);
		act_iv_special_levelssss = (ImageView) findViewById(R.id.act_iv_special_levelssss);
		act_tv_number = (TextView) findViewById(R.id.act_tv_number);

		xListView = (XListView) findViewById(R.id.xListView);
		act_tv_discount_price = (TextView) findViewById(R.id.act_tv_discount_price);
		act_tv_true_price = (TextView) findViewById(R.id.act_tv_true_price);
		act_tv_indents_order_id = (TextView) findViewById(R.id.act_tv_indents_order_id);
		act_tv_indents_order_date = (TextView) findViewById(R.id.act_tv_indents_order_date);
		act_tv_indents_word = (TextView) findViewById(R.id.act_tv_indents_word);
		act_tv_indents = (RelativeLayout) findViewById(R.id.act_tv_indents);
		act_tv_indentss = (RelativeLayout) findViewById(R.id.act_tv_indentss);

		view = new View[]
		{ act_iv_special_level, act_iv_special_levels, act_iv_special_levelss,
				act_iv_special_levelsss, act_iv_special_levelssss };
		// for (int i = 0; i < level; i++)
		// {
		// view[i].setVisibility(View.VISIBLE);
		// }
	}

	private void RequestMore()
	{
		// boolean is = false;
		// if (NetworkJudge.getNetWorkType(getActivity()) !=
		// NetworkJudge.NETWORKTYPE_INVALID)
		// {
		// is = true;
		// }
		RequestParams requestParams = new RequestParams();
		requestParams.put("order_id", id);

		HttpRestClient.getHttpHuaShangha(this, "orders/show", "v2",
				requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						try
						{
							if (response.getString("code").equals("200"))
							{
								JSONObject obj = response.getJSONObject("data");
								JSONObject jsonbje = obj.getJSONObject("order");
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
								indent.setHas_paid(jsonbje.getInt("has_paid"));
								indent.setPeople_count(jsonbje
										.getString("people_count"));
								indent.setMap_id(jsonbje.getString("map_id"));
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
								JSONObject json = jsonbje.getJSONObject("user");
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
								lt.add(indent);

								act_indents_name.setText("被服务人:"
										+ jsonbje.getString("guest_name"));
								act_indents_phone.setText(jsonbje
										.getString("guest_phone_number"));

								act_indents_address.setText("服务地址:"
										+ jsonbje.getString("address_complex")
										+ " "
										+ jsonbje.getString("address_info"));
								if (jsonbje.getString("order_status").equals(
										"1"))
								{
									act_indents_state.setText("待付款");
								} else if (jsonbje.getString("order_status")
										.equals("2"))
								{
									act_indents_state.setText("待履行");
								} else if (jsonbje.getString("order_status")
										.equals("3"))
								{
									act_indents_state.setText("履行中");
								} else if (jsonbje.getString("order_status")
										.equals("4"))
								{
									act_indents_state.setText("已完成");
								} else if (jsonbje.getString("order_status")
										.equals("5"))
								{
									act_indents_state.setText("已取消");
								}
								act_tv_type.setText(jsonbje
										.getString("bodyguard_level"));
								act_tv_date.setText(jsonbje
										.getString("duration"));
								act_tv_date2.setText(jsonbje
										.getString("service_at"));
								for (int i = 0; i < jsonbje.getInt("level"); i++)
								{
									view[i].setVisibility(View.VISIBLE);
								}

								act_tv_number.setText(jsonbje
										.getString("people_count") + "个");

								act_tv_true_price.setText(jsonbje
										.getString("current_price"));

								act_tv_discount_price.setText(jsonbje
										.getString("diff_price"));

								act_tv_indents_order_id.setText("订单编号 : "
										+ jsonbje.getString("id"));

								act_tv_indents_word.setText("捎一句话 : "
										+ jsonbje.getString("words"));

								if (jsonbje.getString("order_status").equals(
										"5"))
								{
									// 已取消
									act_tv_indentss
											.setBackgroundDrawable(getResources()
													.getDrawable(
															R.drawable.item_indentss));
									act_indents_s.setText("已取消");
								} else if (jsonbje.getString("order_status")
										.equals("4"))
								{
									// 已完成
									if (jsonbje.getString("can_comment")
											.equals("true"))
									{
										act_indents_s.setText("去评论");
									} else
									{
										act_tv_indentss
												.setBackgroundDrawable(getResources()
														.getDrawable(
																R.drawable.item_indentss));
										act_indents_s.setText("已完成");
									}

								} else
								{
									if (jsonbje.getString("has_paid").equals(
											"1"))
									{
										// 已付款
										act_tv_indentss
												.setBackgroundDrawable(getResources()
														.getDrawable(
																R.drawable.item_indentss));
										act_indents_s.setText("已付款");
									} else
									{
										// 支付
										act_indents_s.setText("支付");
									}
								}
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
									bodyguarf.setName(object.getString("name"));

									bodyguarf.setPhone_number(object
											.getString("phone_number"));
									bodyguarf.setSex(object.getInt("sex"));
									bodyguarf.setLevel(object.getInt("level"));
									bodyguarf.setCity(object.getString("city"));
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
									bodyguarf.setCertificate_image_key(object
											.getString("certificate_image_key"));
									bodyguarf.setBank_image_key(object
											.getString("bank_image_key"));
									bodyguarf.setReceivable(object
											.getString("receivable"));
									bodyguarf.setGross_income(object
											.getString("gross_income"));
									bodyguarf.setServiced_people_count(object
											.getString("serviced_people_count"));
									bodyguarf.setServiced_time(object
											.getString("serviced_time"));
									bodyguard.add(bodyguarf);
								}
								act_tv_indentss
										.setOnClickListener(new OnClickListener()
										{

											@Override
											public void onClick(View v)
											{
												// TODO Auto-generated method
												// stub
												if (act_indents_s.getText()
														.equals("支付"))
												{

												} else if (act_indents_s
														.getText()
														.equals("去评论"))
												{
													Intent intent = new Intent(
															ActIndentsInfo.this,
															ActComment.class);
													Bundle bundle = new Bundle();
													bundle.putSerializable(
															"Commented_bodyguards",
															commented_bodyguards);
													bundle.putSerializable(
															"id", id);
													bundle.putSerializable(
															"bodyguard",
															bodyguard);
													intent.putExtras(bundle);
													startActivity(intent);
												}
											}
										});
							} else
							{
								Toast.makeText(ActIndentsInfo.this,
										response.getString("message"),
										Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("aaa");
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
}

package com.soloman.org.cn.ui.discount;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.DiscountAdapter;
import com.soloman.org.cn.bean.Discount;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.SwipeBackActivity;

/**
 * 优惠券
 * 
 * @author Mac
 * 
 */
public class ActDiscount extends SwipeBackActivity implements OnClickListener
{
	private TextView act_iv_indext;
	private ImageView act_tv_common_address_return;
	private List<Discount> lt;
	private ListView pop_listview_left;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_discount);
		MyApplication.getInstance().addActivity(this);
		setupView();
		discount();
	}

	private void setupView()
	{
		// TODO Auto-generated method
		pop_listview_left = (ListView) findViewById(R.id.pop_listview_left);
		act_tv_common_address_return = (ImageView) findViewById(R.id.act_tv_common_address_return);
		act_iv_indext = (TextView) findViewById(R.id.act_iv_indext);
		act_tv_common_address_return.setOnClickListener(this);
	}

	private void discount()
	{
		HttpRestClient.getHttpHuaShangha(this,
				"coupons/index", "v2", null,
				new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						System.out.println(response);
						lt = new ArrayList<Discount>();
						JSONObject obj;
						try
						{
							if (response.getJSONArray("coupons").toString()
									.equals("[]"))
							{
								// 可见
								act_iv_indext.setVisibility(View.VISIBLE);
							}
							JSONArray array = response.getJSONArray("coupons");
							for (int i = 0; i < array.length(); i++)
							{
								obj = array.getJSONObject(i);
								Discount discount = new Discount();
								discount.setCreated_at(obj
										.getString("created_at"));

								discount.setEnable(obj.getString("enable"));
								discount.setExpect_at(obj
										.getString("expect_at"));
								discount.setId(obj.getString("id"));
								discount.setUpdated_at(obj
										.getString("updated_at"));
								discount.setUser_id(obj.getString("user_id"));
								discount.setValue(obj.getString("value"));
								lt.add(discount);
							}
							DiscountAdapter dis = new DiscountAdapter(lt,
									ActDiscount.this);
							pop_listview_left.setAdapter(dis);
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.act_tv_common_address_return:
			finish();
			break;

		default:
			break;
		}
	}
}

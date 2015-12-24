package com.soloman.org.cn.ui.address;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.amap.api.mapcore2d.fa;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.CommonAddress;
import com.soloman.org.cn.bean.Customer;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.map.ActMapCity;
import com.soloman.org.cn.ui.map.FraMap;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;

/**
 * 修改地址
 * 
 * @author Mac
 * 
 */
public class ActCommonAddressModify extends Activity implements
		OnClickListener, OnCheckedChangeListener
{
	private int sex = 0;
	private int defaults = 1;
	Bundle bb;
	private CommonAddress ca;
	int id;
	private PreferenceUtils preferences;
	/**
	 * 返回
	 */
	ImageView act_tv_common_address_return;
	/**
	 * 请输入姓名
	 */
	private EditText act_tv_name;
	/**
	 * 男女选择
	 */
	private RadioGroup rg_toptablehost;
	/**
	 * 请输入电话
	 */
	private EditText act_tv_phone;
	/**
	 * 选择城市
	 */
	private LinearLayout act_ll_city;
	private TextView act_tv_city;
	/**
	 * 地址
	 */
	private EditText act_tv_info_address;
	/**
	 * 是否为默认
	 */
	private RadioGroup rg_toptablehosts;
	private TextView fra_tv_addresss;

	private RadioButton rb_gchat;
	private RadioButton rb_discuss;
	private RadioButton rb_s;
	private RadioButton rb_f;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_common_address_modify);
		MyApplication.getInstance().addActivity(this);
		Bundle bundle = getIntent().getExtras();
		ca = (CommonAddress) bundle.getSerializable("ca");
		preferences = PreferenceUtils.getInstance(ActCommonAddressModify.this,
				PreferenceConstants.LOGIN_PREF);
		setupView();
		setupListener();
	}

	private void setupListener()
	{
		act_tv_common_address_return.setOnClickListener(this);
		act_ll_city.setOnClickListener(this);
		rg_toptablehost.setOnCheckedChangeListener(this);
		fra_tv_addresss.setOnClickListener(this);
	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		act_tv_common_address_return = (ImageView) findViewById(R.id.fra_iv_viewss);
		act_tv_name = (EditText) findViewById(R.id.act_tv_name);
		rg_toptablehost = (RadioGroup) findViewById(R.id.rg_toptablehost);
		rg_toptablehosts = (RadioGroup) findViewById(R.id.rg_toptablehosts);
		act_tv_phone = (EditText) findViewById(R.id.act_tv_phone);
		act_ll_city = (LinearLayout) findViewById(R.id.act_ll_city);
		act_tv_city = (TextView) findViewById(R.id.act_tv_city);
		act_tv_info_address = (EditText) findViewById(R.id.act_tv_info_address);
		fra_tv_addresss = (TextView) findViewById(R.id.fra_tv_addresss);
		rb_gchat = (RadioButton) findViewById(R.id.rb_gchat);
		rb_discuss = (RadioButton) findViewById(R.id.rb_discuss);
		rb_s = (RadioButton) findViewById(R.id.rb_s);
		rb_f = (RadioButton) findViewById(R.id.rb_f);

		act_tv_name.setText(ca.getName());
		if (ca.getSex() == 0)
		{
			rb_gchat.setChecked(true);
			rb_discuss.setChecked(false);
		} else
		{
			rb_discuss.setChecked(true);
			rb_gchat.setChecked(false);
		}
		act_tv_phone.setText(ca.getPhone());
		act_tv_city.setText(ca.getComplex());
		act_tv_info_address.setText(ca.getInfo());
		if (ca.getIs_default() == true)
		{
			rb_s.setChecked(true);
			rb_f.setChecked(false);
		} else
		{
			rb_s.setChecked(false);
			rb_f.setChecked(true);
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.fra_iv_viewss:
			finish();
			break;
		case R.id.fra_tv_addresss:
			if (act_tv_name.length() == 0)
			{
				Toast.makeText(ActCommonAddressModify.this, "请输入姓名",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (act_tv_phone.length() == 0)
			{
				Toast.makeText(ActCommonAddressModify.this, "请输入电话",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (act_tv_city.getText().equals("请选择省份"))
			{
				Toast.makeText(ActCommonAddressModify.this, "请选择省份",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (act_tv_info_address.length() == 0)
			{
				Toast.makeText(ActCommonAddressModify.this, "请输入详细地址",
						Toast.LENGTH_LONG).show();
				return;
			}
			modify();
			break;
		case R.id.act_ll_city:
			// 地图选点
			Intent intent = new Intent(ActCommonAddressModify.this,
					ActMapCity.class);
			startActivityForResult(intent, 10);
			break;
		default:
			break;
		}

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1)
		{
			bb = data.getExtras();
			switch (requestCode)
			{
			case 10:
				act_tv_city.setText(bb.getString("Title"));
				break;
			default:
				break;
			}
		}
	}
	private void modify()
	{

		ca.setName(act_tv_name.getText().toString());
		ca.setSex(sex);
		ca.setPhone(act_tv_phone.getText().toString());
		ca.setComplex(act_tv_city.getText().toString());
		ca.setInfo(act_tv_info_address.getText().toString());
		ca.setLocation_info(FraMap.Location);
		ca.setIs_default(defaults == 1 ? true : false);
		// boolean is = false;
		// if (NetworkJudge.getNetWorkType(this) !=
		// NetworkJudge.NETWORKTYPE_INVALID)
		// {
		// is = true;
		// }

		RequestParams requestParams = new RequestParams();

		requestParams.put("id", ca.getId() + "");
		requestParams.put("name", act_tv_name.getText().toString());
		requestParams.put("sex", sex + "");
		requestParams.put("phone", act_tv_phone.getText().toString());
		requestParams.put("complex", act_tv_city.getText().toString());
		requestParams.put("info", act_tv_info_address.getText().toString());
		requestParams.put("location", FraMap.Location);
		requestParams.put("set_default", defaults + "");
		HttpRestClient.postHttpHuaShangha(this, "addresses/update", "v2",
				requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						try
						{
							Toast.makeText(ActCommonAddressModify.this,
									response.getString("message"),
									Toast.LENGTH_LONG).show();
							Intent intent = getIntent();
							Bundle b = new Bundle();
							b.putSerializable("address", ca);//
							// 把数据塞入intent里面
							intent.putExtras(b);
							ActCommonAddressModify.this.setResult(1, intent);// 跳转回原来的activity
							ActCommonAddressModify.this.finish();// 结束当前activity

						} catch (JSONException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
	}

	@Override
	public void onCheckedChanged(RadioGroup rootView, int checkedId)
	{
		// TODO Auto-generated method stub
		switch (checkedId)
		{
		case R.id.rb_gchat:
			// 男0
			sex = 0;
			break;
		case R.id.rb_discuss:
			// 女1
			sex = 1;
			break;
		case R.id.rb_s:
			// 默认
			defaults = 1;
			break;
		case R.id.rb_f:
			// 非默认
			defaults = 0;
			break;
		default:
			break;
		}
	}

}

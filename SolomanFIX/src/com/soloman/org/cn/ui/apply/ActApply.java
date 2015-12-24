package com.soloman.org.cn.ui.apply;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.CommonAddress;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.utis.Netroid;

/**
 * 申请成为保镖
 * 
 * @author Mac
 * 
 */
public class ActApply extends Activity implements OnClickListener
{
	/**
	 * 返回
	 */
	private ImageView act_tv_common_address_return;
	/**
	 * 姓名
	 */
	private EditText editText1;
	private ImageView login_click1;

	/**
	 * 电话
	 */
	private EditText editText2;
	private ImageView login_click2;

	/**
	 * 城市
	 */
	private EditText editText3;
	private ImageView login_click3;

	/**
	 * 地址
	 */
	private EditText editText4;
	private ImageView login_click4;

	/**
	 * 开户银行
	 */
	private EditText editText5;
	private ImageView login_click5;

	/**
	 * 支行名称
	 */
	private EditText editText6;
	private ImageView login_click6;

	/**
	 * 银行账户
	 */
	private EditText editText7;
	private ImageView login_click7;

	private Button item_present_indent_tv_click;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_apply);
		MyApplication.getInstance().addActivity(this);
		setupView();
	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		item_present_indent_tv_click = (Button) findViewById(R.id.item_present_indent_tv_click);
		item_present_indent_tv_click.setOnClickListener(this);
		act_tv_common_address_return = (ImageView) findViewById(R.id.act_tv_common_address_return);
		act_tv_common_address_return.setOnClickListener(this);
		editText1 = (EditText) findViewById(R.id.editText1);
		login_click1 = (ImageView) findViewById(R.id.login_click1);
		editText1.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				// TODO Auto-generated method stub
				if (!editText1.getText().toString().equals(""))
				{
					login_click1.setVisibility(View.VISIBLE);
				} else
				{
					login_click1.setVisibility(View.GONE);

				}
			}
		});

		editText1.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{

				if (hasFocus)
				{// 获得焦点
					if (!editText1.getText().toString().equals(""))
					{
						login_click1.setVisibility(View.VISIBLE);
					}

				} else
				{// 失去焦点
					login_click1.setVisibility(View.GONE);
				}
			}

		});
		editText2 = (EditText) findViewById(R.id.editText2);
		login_click2 = (ImageView) findViewById(R.id.login_click2);
		editText2.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				// TODO Auto-generated method stub
				if (!editText2.getText().toString().equals(""))
				{
					login_click2.setVisibility(View.VISIBLE);
				} else
				{
					login_click2.setVisibility(View.GONE);

				}
			}
		});
		editText2.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{

				if (hasFocus)
				{// 获得焦点
					if (!editText2.getText().toString().equals(""))
					{
						login_click2.setVisibility(View.VISIBLE);
					}

				} else
				{// 失去焦点
					login_click2.setVisibility(View.GONE);
				}
			}

		});
		editText3 = (EditText) findViewById(R.id.editText3);
		login_click3 = (ImageView) findViewById(R.id.login_click3);
		editText3.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				// TODO Auto-generated method stub
				if (!editText3.getText().toString().equals(""))
				{
					login_click3.setVisibility(View.VISIBLE);
				} else
				{
					login_click3.setVisibility(View.GONE);

				}
			}
		});
		editText3.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{

				if (hasFocus)
				{// 获得焦点
					if (!editText3.getText().toString().equals(""))
					{
						login_click3.setVisibility(View.VISIBLE);
					}

				} else
				{// 失去焦点
					login_click3.setVisibility(View.GONE);
				}
			}

		});
		editText4 = (EditText) findViewById(R.id.editText4);
		login_click4 = (ImageView) findViewById(R.id.login_click4);
		editText4.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				// TODO Auto-generated method stub
				if (!editText4.getText().toString().equals(""))
				{
					login_click4.setVisibility(View.VISIBLE);
				} else
				{
					login_click4.setVisibility(View.GONE);

				}
			}
		});
		editText4.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{

				if (hasFocus)
				{// 获得焦点
					if (!editText4.getText().toString().equals(""))
					{
						login_click4.setVisibility(View.VISIBLE);
					}

				} else
				{// 失去焦点
					login_click4.setVisibility(View.GONE);
				}
			}

		});
		editText5 = (EditText) findViewById(R.id.editText5);
		login_click5 = (ImageView) findViewById(R.id.login_click5);
		editText5.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				// TODO Auto-generated method stub
				if (!editText5.getText().toString().equals(""))
				{
					login_click5.setVisibility(View.VISIBLE);
				} else
				{
					login_click5.setVisibility(View.GONE);

				}
			}
		});
		editText5.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{

				if (hasFocus)
				{// 获得焦点
					if (!editText5.getText().toString().equals(""))
					{
						login_click5.setVisibility(View.VISIBLE);
					}

				} else
				{// 失去焦点
					login_click5.setVisibility(View.GONE);
				}
			}

		});
		editText6 = (EditText) findViewById(R.id.editText6);
		login_click6 = (ImageView) findViewById(R.id.login_click6);
		editText6.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				// TODO Auto-generated method stub
				if (!editText6.getText().toString().equals(""))
				{
					login_click6.setVisibility(View.VISIBLE);
				} else
				{
					login_click6.setVisibility(View.GONE);

				}
			}
		});
		editText6.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{

				if (hasFocus)
				{// 获得焦点
					if (!editText6.getText().toString().equals(""))
					{
						login_click6.setVisibility(View.VISIBLE);
					}

				} else
				{// 失去焦点
					login_click6.setVisibility(View.GONE);
				}
			}

		});
		editText7 = (EditText) findViewById(R.id.editText7);
		login_click7 = (ImageView) findViewById(R.id.login_click7);
		editText7.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				// TODO Auto-generated method stub
				if (!editText7.getText().toString().equals(""))
				{
					login_click7.setVisibility(View.VISIBLE);
				} else
				{
					login_click7.setVisibility(View.GONE);

				}
			}
		});
		editText7.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{

				if (hasFocus)
				{// 获得焦点
					if (!editText7.getText().toString().equals(""))
					{
						login_click7.setVisibility(View.VISIBLE);
					}

				} else
				{// 失去焦点
					login_click7.setVisibility(View.GONE);
				}
			}

		});
		findViewById(R.id.keyboard).setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{

				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				editText1.requestFocus(); // 请求获取焦点
				editText1.clearFocus(); // 清除焦点
				editText2.requestFocus();
				editText2.clearFocus();
				editText3.requestFocus();
				editText3.clearFocus();
				editText4.requestFocus();
				editText4.clearFocus();
				editText5.requestFocus();
				editText5.clearFocus();
				editText6.requestFocus();
				editText6.clearFocus();
				editText7.requestFocus();
				editText7.clearFocus();
				return imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
			}
		});

		login_click1.setOnClickListener(this);
		login_click2.setOnClickListener(this);
		login_click3.setOnClickListener(this);
		login_click4.setOnClickListener(this);
		login_click5.setOnClickListener(this);
		login_click6.setOnClickListener(this);
		login_click7.setOnClickListener(this);
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
		case R.id.login_click1:
			editText1.setText("");
			break;
		case R.id.login_click2:
			editText2.setText("");
			break;
		case R.id.login_click3:
			editText3.setText("");
			break;
		case R.id.login_click4:
			editText4.setText("");
			break;
		case R.id.login_click5:
			editText5.setText("");
			break;
		case R.id.login_click6:
			editText6.setText("");
			break;
		case R.id.login_click7:
			editText7.setText("");
			break;
		case R.id.item_present_indent_tv_click:
			applys();
			Toast.makeText(ActApply.this, "提交成功", Toast.LENGTH_LONG).show();
			finish();
			break;
		default:
			break;
		}
	}

	private void applys()
	{

		RequestParams requestParams = new RequestParams();
		requestParams.put("bodyguard[name]",
				editText1.getText().toString());
		requestParams.put("bodyguard[phone_number]",
				editText2.getText().toString());
		requestParams.put("bodyguard[city]",
				editText3.getText().toString());
		requestParams.put("bodyguard[address]",
				editText4.getText().toString());
		requestParams.put("bodyguard[bank_account]",
				editText7.getText().toString());
		HttpRestClient.postHttpHuaShangha(this, "bodyguards/create", "v2",
				requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
					}
				});
	}
}

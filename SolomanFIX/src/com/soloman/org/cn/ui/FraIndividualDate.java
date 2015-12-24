package com.soloman.org.cn.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.Customer;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.address.ActCommonAddress;
import com.soloman.org.cn.ui.apply.ActApply;
import com.soloman.org.cn.ui.discount.ActDiscount;
import com.soloman.org.cn.ui.feedback.ActFeedback;
import com.soloman.org.cn.ui.message.ActMessageList;
import com.soloman.org.cn.utis.FastClick;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.PreferenceUtils;
import com.soloman.org.cn.view.CustomDialog;
import com.soloman.org.cn.view.CustomDialog.Builder;
import com.soloman.org.cn.view.RoundImageView;
import com.soloman.org.cn.webview.ActWebView;

/**
 * 个人资料
 * 
 * @author Mac
 * 
 */
public class FraIndividualDate extends Fragment implements OnClickListener
{
	// private Update update;
	private CustomDialog.Builder builder;
	private Customer cr;
	private RoundImageView fra_individual_date_rlv_;
	private EditText fra_individual_date_et;
	private ImageView fra_individual_date_iv_click1;
	private ImageView fra_individual_date_iv_click2;
	private final int charMaxNum = 50;
	private int i = 0;
	private PreferenceUtils preferences;
	// 消息
	RelativeLayout fra_individual_date_ll_message;
	// 地址
	RelativeLayout fra_individual_date_iv_address;
	// 优惠劵
	RelativeLayout fra_individual_date_ll_discount;
	// 服务范围
	RelativeLayout fra_individual_date_ll_z;
	// 常见问题
	RelativeLayout fra_individual_date_ll_zz;
	// 关于我们
	RelativeLayout fra_individual_date_ll_zzz;
	// 用户协议
	RelativeLayout fra_individual_date_ll_zzzz;
	// 给我们评分
	RelativeLayout fra_individual_date_ll_zzzzz;

	// 意见反馈
	RelativeLayout fra_individual_date_ll_zzzzzz;

	// 当前版本
	RelativeLayout fra_individual_date_ll_zzzzzzz;

	// 退出登录
	RelativeLayout fra_individual_date_ll_zzzzzzzz;

	// 加入嚯嚯
	RelativeLayout fra_individual_date_ll_zzzzzzzzz;
	// 显示版本号
	private TextView fra_tv_;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fra_individual_date, container,
				false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		cr = (Customer) getArguments().getSerializable("cr");
		// update = (Update) getArguments().getSerializable("update");
		// 数据初始化
		// setUpView(view);
		// 事件初始化
		setUpListener();
	}

	// private void setUpView(View v)
	// {
	// preferences = PreferenceUtils.getInstance(getActivity(), "soloman");
	// fra_individual_date_rlv_ = (RoundImageView) v
	// .findViewById(R.id.fra_individual_date_rlv_);
	// fra_individual_date_et = (EditText) v
	// .findViewById(R.id.fra_individual_date_et);
	// fra_individual_date_et.setText(preferences.getString("userName", ""));
	// fra_individual_date_iv_click1 = (ImageView) v
	// .findViewById(R.id.fra_individual_date_iv_click1);
	//
	// fra_individual_date_iv_click2 = (ImageView) v
	// .findViewById(R.id.fra_individual_date_iv_click2);
	// fra_individual_date_ll_message = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_ll_message);
	// fra_individual_date_ll_message.setOnClickListener(this);
	// fra_individual_date_iv_address = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_iv_address);
	// fra_individual_date_iv_address.setOnClickListener(this);
	// fra_individual_date_ll_discount = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_ll_discount);
	// fra_individual_date_ll_discount.setOnClickListener(this);
	// fra_individual_date_ll_z = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_ll_z);
	// fra_individual_date_ll_z.setOnClickListener(this);
	// fra_individual_date_ll_zz = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_ll_zz);
	// fra_individual_date_ll_zz.setOnClickListener(this);
	//
	// fra_individual_date_ll_zzz = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_ll_zzz);
	// fra_individual_date_ll_zzz.setOnClickListener(this);
	//
	// fra_individual_date_ll_zzzz = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_ll_zzzz);
	// fra_individual_date_ll_zzzz.setOnClickListener(this);
	//
	// fra_individual_date_ll_zzzzz = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_ll_zzzzz);
	// fra_individual_date_ll_zzzzz.setOnClickListener(this);
	//
	// fra_individual_date_ll_zzzzzz = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_ll_zzzzzz);
	// fra_individual_date_ll_zzzzzz.setOnClickListener(this);
	//
	// fra_individual_date_ll_zzzzzzz = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_ll_zzzzzzz);
	// fra_individual_date_ll_zzzzzzz.setOnClickListener(this);
	//
	// fra_individual_date_ll_zzzzzzzz = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_ll_zzzzzzzz);
	// fra_individual_date_ll_zzzzzzzz.setOnClickListener(this);
	//
	// fra_individual_date_ll_zzzzzzzzz = (RelativeLayout) v
	// .findViewById(R.id.fra_individual_date_ll_zzzzzzzzz);
	// fra_individual_date_ll_zzzzzzzzz.setOnClickListener(this);
	// fra_tv_ = (TextView) v.findViewById(R.id.fra_tv_);
	//
	// PackageManager manager = getActivity().getPackageManager();
	// PackageInfo info = null;
	// try
	// {
	// info = manager.getPackageInfo(getActivity().getPackageName(), 0);
	// } catch (NameNotFoundException e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// fra_tv_.setText(info.versionName);
	// }

	private void setUpListener()
	{
		fra_individual_date_et.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				// TODO Auto-generated method stub
				Log.i("FraIndividualDate", "输入文字中的状态，count是一次性输入字符数");
				// fra_individual_date_et.setText("还能输入"
				// + (charMaxNum - s.length()) + "字符");
				System.out.println("还能输入" + (charMaxNum - s.length()) + "字符");
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub
				Log.i("FraIndividualDate", "输入文本之前的状态");
			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				if (arg0.length() > 0)
				{
					fra_individual_date_iv_click1.setVisibility(View.VISIBLE);
				} else
				{
					fra_individual_date_iv_click1.setVisibility(View.GONE);
				}
			}
		});

		fra_individual_date_iv_click2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (FastClick.isFastClick())
				{
					return;
				}
				if (i == 1)
				{
					fra_individual_date_iv_click2
							.setImageResource(R.drawable.icon_xiugai);
					fra_individual_date_iv_click1.setVisibility(View.GONE);
					// 不可编辑
					fra_individual_date_et.setEnabled(false);
					i = 0;
					changeUserName();
				} else
				{
					// 如果EditText用有数据则显示清空图标
					if (fra_individual_date_et.getText().length() > 0)
					{
						fra_individual_date_iv_click1
								.setVisibility(View.VISIBLE);
					}

					fra_individual_date_iv_click2
							.setImageResource(R.drawable.icon_wancheng);
					// 获得焦点
					fra_individual_date_et.requestFocus();
					// 可编辑
					fra_individual_date_et.setEnabled(true);
					i = 1;
					upspring();
				}
			}
		});

		fra_individual_date_iv_click1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				fra_individual_date_et.setText("");
			}
		});
	}

	/**
	 * 软盘弹起
	 */
	private void upspring()
	{
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(fra_individual_date_et,
				InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 更改用户名
	 */
	private void changeUserName()
	{
		RequestParams requestParams = new RequestParams();
		try
		{
			requestParams.put("username", URLEncoder.encode(
					fra_individual_date_et.getText().toString(), "utf-8"));
			HttpRestClient.postHttpHuaShangha(getActivity(),
					"users/change_username", "v2", requestParams,
					new JsonHttpResponseHandler()
					{
						@Override
						public void onSuccess(JSONObject response)
						{
							// TODO Auto-generated method stub
							super.onSuccess(response);
							try
							{
								preferences.put("userName",
										response.getString("username")
												.toString());
							} catch (JSONException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

		} catch (UnsupportedEncodingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.fra_individual_date_ll_message:
			Intent intent = new Intent(getActivity(), ActMessageList.class);
			startActivity(intent);
			break;
		// case R.id.fra_individual_date_iv_address:
		// // 地址
		// Intent intentz = new Intent(getActivity(), ActCommonAddress.class);
		// Bundle bundlez = new Bundle();
		// bundlez.putSerializable("cr", cr);
		// intentz.putExtras(bundlez);
		// startActivity(intentz);
		// break;
		case R.id.fra_individual_date_ll_discount:
			// 优惠券
			Intent intenta = new Intent(getActivity(), ActDiscount.class);
			startActivity(intenta);
			break;
		case R.id.fra_individual_date_ll_z:
			// 服务范围
			Intent intents = new Intent(getActivity(), ActWebView.class);
			Bundle bundle = new Bundle();
			bundle.putInt("Mark", 1);
			bundle.putString("URL", "http://soloman.co/service.html");
			intents.putExtras(bundle);
			startActivity(intents);
			break;
		case R.id.fra_individual_date_ll_zz:
			// 常见问题
			Intent intentss = new Intent(getActivity(), ActWebView.class);
			Bundle bundles = new Bundle();
			bundles.putInt("Mark", 2);
			bundles.putString("URL", "http://soloman.co/qa.html");
			intentss.putExtras(bundles);
			startActivity(intentss);
			break;

		case R.id.fra_individual_date_ll_zzz:
			// 关于我们
			Intent intentsss = new Intent(getActivity(), ActWebView.class);
			Bundle bundless = new Bundle();
			bundless.putInt("Mark", 3);
			bundless.putString("URL", "http://soloman.co/about.html");
			intentsss.putExtras(bundless);
			startActivity(intentsss);
			break;
		//
		// case R.id.fra_individual_date_ll_zzzz:
		// // 用户协议
		// Intent intentssss = new Intent(getActivity(), ActWebView.class);
		// Bundle bundlesss = new Bundle();
		// bundlesss.putInt("Mark", 4);
		// bundlesss.putString("URL", "http://soloman.co/agreement.html");
		// intentssss.putExtras(bundlesss);
		// startActivity(intentssss);
		// break;
		// case R.id.fra_individual_date_ll_zzzzz:
		// // 给我们评分
		// // Intent intentsssss = new Intent(getActivity(), ActWebView.class);
		// // Bundle bundlessss = new Bundle();
		// // bundlessss.putString("Mark", 5);
		// // intentsssss.putExtras(bundlessss);
		// // startActivity(intentsssss);
		// break;
		//
		// case R.id.fra_individual_date_ll_zzzzzz:
		// // 意见反馈
		// Intent intentssssss = new Intent(getActivity(), ActFeedback.class);
		// startActivity(intentssssss);
		// break;
		//
		// case R.id.fra_individual_date_ll_zzzzzzz:
		// // 当前版本
		// break;
		// case R.id.fra_individual_date_ll_zzzzzzzz:
		// // 退出登录
		// builder = new Builder(getActivity());
		// builder.setTitle(R.string.prompt);
		// builder.setMessage(R.string.exit_app);
		// builder.setPositiveButton(R.string.confirm,
		// new DialogInterface.OnClickListener()
		// {
		//
		// @Override
		// public void onClick(DialogInterface arg0, int arg1)
		// {
		// // TODO Auto-generated method stub
		// // 清空
		// preferences.clearPreference();
		// Intent intentzzz = new Intent(getActivity(),
		// ActLogin.class);
		// startActivity(intentzzz);
		// getActivity().finish();
		//
		// // System.exit(0); 完全退出杀死进程
		// }
		// });
		// builder.setNegativeButton(R.string.cancel,
		// new DialogInterface.OnClickListener()
		// {
		//
		// @Override
		// public void onClick(DialogInterface arg0, int arg1)
		// {
		// // TODO Auto-generated method stub
		// arg0.dismiss();
		// }
		// });
		// builder.create().show();
		// break;
		// case R.id.fra_individual_date_ll_zzzzzzzzz:
		// // 加入嚯嚯
		// Intent intentq = new Intent(getActivity(), ActApply.class);
		// startActivity(intentq);
		// break;
		default:
			break;
		}
	}
}

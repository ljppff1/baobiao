package com.soloman.org.cn.ui.individualData;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.Customer;
import com.soloman.org.cn.ui.ActLogin;
import com.soloman.org.cn.ui.address.ActCommonAddress;
import com.soloman.org.cn.ui.discount.ActDiscount;
import com.soloman.org.cn.ui.message.ActMessageList;
import com.soloman.org.cn.ui.sliding.ActHosts;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;
import com.soloman.org.cn.view.CustomDialog;
import com.soloman.org.cn.view.CustomDialog.Builder;
import com.soloman.org.cn.view.RoundImageView;

/**
 * 个人资料
 * 
 * @author Mac
 * 
 */
public class ActindividualData extends Fragment implements OnClickListener
{
	private PreferenceUtils preferences;
	private CustomDialog.Builder builder;
	private Customer cr;
	// 返回
	private ImageView act_tv_view;
	// 特卫等级
	private ImageView act_iv_views1, act_iv_views2, act_iv_views3,
			act_iv_views4, act_iv_views5;
	// 头像
	private RoundImageView fra_individual_date_rlv_;
	// 名称
	private TextView act_tv_individual_name;
	// 电话
	private TextView act_tv_individual_phone;
	// 消息
	private LinearLayout fra_individual_date_ll_message;
	// 地址
	private LinearLayout fra_individual_date_ll_address;
	// 优惠劵
	private LinearLayout fra_individual_date_ll_discount;
	// 邀请码
	private RelativeLayout fra_individual_date_ll_z;
	// 个人资料
	private RelativeLayout fra_individual_date_ll_zz;
	// 退出登录
	private RelativeLayout fra_individual_date_ll_zzz;
	ActHosts host;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fra_individual_date, container,
				false);
		host = (ActHosts) getActivity();
		cr = (Customer) getArguments().getSerializable("cr");
		setupView(view);
		setListener();
		return view;
	}

	private void setupView(View v)
	{
		// TODO Auto-generated method stub
		preferences = PreferenceUtils.getInstance(getActivity(),
				PreferenceConstants.LOGIN_PREF);
		act_tv_view = (ImageView) v.findViewById(R.id.act_tv_view);
		act_iv_views1 = (ImageView) v.findViewById(R.id.act_iv_views1);
		act_iv_views2 = (ImageView) v.findViewById(R.id.act_iv_views2);
		act_iv_views3 = (ImageView) v.findViewById(R.id.act_iv_views3);
		act_iv_views4 = (ImageView) v.findViewById(R.id.act_iv_views4);
		act_iv_views5 = (ImageView) v.findViewById(R.id.act_iv_views5);
		fra_individual_date_rlv_ = (RoundImageView) v
				.findViewById(R.id.fra_individual_date_rlv_);
		act_tv_individual_name = (TextView) v
				.findViewById(R.id.act_tv_individual_name);
		act_tv_individual_phone = (TextView) v
				.findViewById(R.id.act_tv_individual_phone);
		fra_individual_date_ll_message = (LinearLayout) v
				.findViewById(R.id.fra_individual_date_ll_message);
		fra_individual_date_ll_address = (LinearLayout) v
				.findViewById(R.id.fra_individual_date_ll_address);
		fra_individual_date_ll_discount = (LinearLayout) v
				.findViewById(R.id.fra_individual_date_ll_discount);
		fra_individual_date_ll_z = (RelativeLayout) v
				.findViewById(R.id.fra_individual_date_ll_z);
		fra_individual_date_ll_zz = (RelativeLayout) v
				.findViewById(R.id.fra_individual_date_ll_zz);
		fra_individual_date_ll_zzz = (RelativeLayout) v
				.findViewById(R.id.fra_individual_date_ll_zzz);
	}

	private void setListener()
	{
		// TODO Auto-generated method stub
		act_tv_view.setOnClickListener(this);
		fra_individual_date_ll_message.setOnClickListener(this);
		fra_individual_date_ll_address.setOnClickListener(this);
		fra_individual_date_ll_discount.setOnClickListener(this);
		fra_individual_date_ll_z.setOnClickListener(this);
		fra_individual_date_ll_zz.setOnClickListener(this);
		fra_individual_date_ll_zzz.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.act_tv_view:
			// 抽屉
			host.sm.toggle();
			break;
		case R.id.fra_individual_date_ll_message:
			// 消息
			Intent intent = new Intent(getActivity(), ActMessageList.class);
			startActivity(intent);
			break;

		case R.id.fra_individual_date_ll_address:
			// 地址

			Intent intentz = new Intent(getActivity(), ActCommonAddress.class);
			Bundle bundlez = new Bundle();
			bundlez.putSerializable("cr", cr);
			intentz.putExtras(bundlez);
			startActivity(intentz);
			break;

		case R.id.fra_individual_date_ll_discount:
			// 优惠券
			Intent intenta = new Intent(getActivity(), ActDiscount.class);
			startActivity(intenta);
			break;
		case R.id.fra_individual_date_ll_z:
			// 邀请码
			break;
		case R.id.fra_individual_date_ll_zz:
			// 个人资料
			Intent intents = new Intent(getActivity(), ActSetIndividual.class);
			startActivity(intents);
			break;
		case R.id.fra_individual_date_ll_zzz:
			// 退出登录
			builder = new Builder(getActivity());
			builder.setTitle(R.string.prompt);
			builder.setMessage(R.string.exit_app);
			builder.setPositiveButton(R.string.confirm,
					new DialogInterface.OnClickListener()
					{

						@Override
						public void onClick(DialogInterface arg0, int arg1)
						{
							// TODO Auto-generated method stub
							// 清空
							preferences.clearPreference();
							Intent intentzzz = new Intent(getActivity(),
									ActLogin.class);
							intentzzz.putExtras(getArguments());
							startActivity(intentzzz);
							// finish();
							MyApplication.getInstance().onTerminate();
							// System.exit(0); 完全退出杀死进程
						}
					});
			builder.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener()
					{

						@Override
						public void onClick(DialogInterface arg0, int arg1)
						{
							// TODO Auto-generated method stub
							arg0.dismiss();
						}
					});
			builder.create().show();
			break;

		default:
			break;
		}
	}
}

package com.soloman.org.cn.wxapi;

import com.soloman.org.cn.R;
import com.soloman.org.cn.ui.appoint.ActAppoints;
import com.soloman.org.cn.ui.indent.FraPresentIndent;
import com.soloman.org.cn.ui.pay.ActPays;
import com.soloman.org.cn.ui.sliding.ActHosts;
import com.soloman.org.cn.utis.wx.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler
{
	RelativeLayout animationHoldingFrame;
	ImageView mapPictures;
	// private Animation anim;
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	private Bundle bundle;
	private IWXAPI api;
	private boolean iss = false;
	private int sj = 1;
	private LinearLayout aa;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);
		setupView();
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);
	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		animationHoldingFrame = (RelativeLayout) findViewById(R.id.animationHoldingFrame);
		mapPictures = (ImageView) findViewById(R.id.mapPictures);
		// anim = AnimationUtils.loadAnimation(this, R.anim.progressbar);
		// mapPictures.setAnimation(anim);
		// bundle = getIntent().getExtras();
	}

	// @Override
	// protected void onPause()
	// {
	// // TODO Auto-generated method stub
	// super.onPause();
	// finish();
	// }

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req)
	{
	}

	@Override
	public void onResp(BaseResp resp)
	{
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		// 获取启动这个activity的intent

		// if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
		// AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setTitle(R.string.app_tip);
		// builder.setMessage(getString(R.string.pay_result_callback_msg,
		// resp.errStr +";code=" + String.valueOf(resp.errCode)));
		// builder.show();
		// }
		if (resp.errCode == -2)
		{
			// 失败
			Toast.makeText(WXPayEntryActivity.this, "支付失败,订单已保存",
					Toast.LENGTH_LONG).show();
		}
		if (resp.errCode == 0)
		{
			Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_LONG)
					.show();
		}
		Intent intent = new Intent("data.broadcast.actions");
		// 发送广播
		sendBroadcast(intent);
		// Intent intents = new Intent(WXPayEntryActivity.this, ActHosts.class);
		// intents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(intents);
		finish();
	}
}
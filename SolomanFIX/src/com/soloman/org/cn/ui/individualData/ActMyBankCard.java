package com.soloman.org.cn.ui.individualData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.Request.Method;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.ImageItem;
import com.soloman.org.cn.bean.qiniuImageg;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;
import com.soloman.org.cn.ui.individualData.imagess.AlbumActivity;
import com.soloman.org.cn.ui.individualData.imagess.GalleryActivity;
import com.soloman.org.cn.utis.Netroid;
import com.soloman.org.cn.utis.SwipeBackActivity;
import com.soloman.org.cn.utis.imagess.Bimp;
import com.soloman.org.cn.utis.imagess.FileUtils;
import com.soloman.org.cn.utis.imagess.PublicWay;
import com.soloman.org.cn.utis.imagess.Res;

/**
 * 银行信息
 * 
 * @author Mac
 * 
 */
public class ActMyBankCard extends SwipeBackActivity
{

	private UploadManager uploadManager;
	private List<qiniuImageg> lt;
	private TextView activity_selectimg_send;
	private List<String> list;
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap;
	private static int is = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.my_bank_card, null);
		setContentView(parentView);
		MyApplication.getInstance().addActivity(this);
		setupView();
	}

	private void setupView()
	{

		uploadManager = new UploadManager();
		// TODO Auto-generated method stub
		activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
		activity_selectimg_send.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				list = new ArrayList<String>();
				for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++)
				{
					// 得到路径
					String path = Bimp.tempSelectBitmap.get(i).imagePath;
					// 根据路径返回Bitmap
					Bitmap selectBitmap = Bimp.tempSelectBitmap.get(i)
							.getBitmap();
					// 截取名称
					String newStr = path.substring(path.lastIndexOf("/") + 1,
							path.lastIndexOf("."));
					list.add(FileUtils.SDPATH + newStr + ".JPEG");
					// 生成图片
					FileUtils.saveBitmap(selectBitmap, newStr);
				}
				initBodyguardRequest();
			}
		});
		pop = new PopupWindow(ActMyBankCard.this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent = new Intent(ActMyBankCard.this,
						AlbumActivity.class);
				startActivity(intent);
				// finish();
				// overridePendingTransition(R.anim.activity_translate_in,
				// R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				if (arg2 == is)
				{
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							ActMyBankCard.this, R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else
				{
					Intent intent = new Intent(ActMyBankCard.this,
							GalleryActivity.class);
					intent.putExtra("position", "3");
					intent.putExtra("ID", arg2);
					startActivity(intent);
					// finish();
				}
			}
		});
		// 注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
		IntentFilter filter = new IntentFilter("data.broadcast.action");
		registerReceiver(broadcastReceiver, filter);
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			// mContext.unregisterReceiver(this);
			// TODO Auto-generated method stub
			is = Bimp.tempSelectBitmap.size();
			adapter.notifyDataSetChanged();
		}
	};

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter
	{
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape()
		{
			return shape;
		}

		public void setShape(boolean shape)
		{
			this.shape = shape;
		}

		public GridAdapter(Context context)
		{
			inflater = LayoutInflater.from(context);
		}

		public void update()
		{
			loading();
		}

		public int getCount()
		{
			if (Bimp.tempSelectBitmap.size() == 9)
			{
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0)
		{
			return null;
		}

		public long getItemId(int arg0)
		{
			return 0;
		}

		public void setSelectedPosition(int position)
		{
			selectedPosition = position;
		}

		public int getSelectedPosition()
		{
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			if (convertView == null)
			{
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size())
			{
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9)
				{
					holder.image.setVisibility(View.GONE);
				}
			} else
			{
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position)
						.getBitmap());
			}

			return convertView;
		}

		public class ViewHolder
		{
			public ImageView image;
		}

		Handler handler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				switch (msg.what)
				{
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading()
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					while (true)
					{
						if (Bimp.max == Bimp.tempSelectBitmap.size())
						{
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else
						{
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s)
	{
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++)
		{
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart()
	{
		adapter.update();
		super.onRestart();
	}

	private static final int TAKE_PICTURE = 0x000001;

	public void photo()
	{
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK)
			{

				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				FileUtils.saveBitmap(bm, fileName);

				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			for (int i = 0; i < PublicWay.activityList.size(); i++)
			{
				if (null != PublicWay.activityList.get(i))
				{
					PublicWay.activityList.get(i).finish();
				}
			}
			// System.exit(0);
		}
		return true;
	}

	private void initBodyguardRequest()
	{
		
		RequestParams requestParams = new RequestParams();
		requestParams.put("image_num", list.size()+"");
		requestParams.put("image_type_value",4+"");
		HttpRestClient.postHttpHuaShangha(this,
				"images/upload_params", "v2", requestParams,
				new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						JSONArray array;
						JSONObject obj;
						try
						{
							lt = new ArrayList<qiniuImageg>();
							array = response.getJSONArray("images");
							for (int i = 0; i < array.length(); i++)
							{
								qiniuImageg qiniu = new qiniuImageg();
								obj = array.getJSONObject(i);

								// qiniu.setQn_key(obj.getString("qn_key"));
								// qiniu.setQn_token(obj.getString("qn_token"));
								doUpload(list.get(i), obj.getString("qn_key"),
										obj.getString("qn_token"));
								// lt.add(qiniu);
							}
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 */
	private void doUpload(String file, String pre, String uptoken)
	{
		File files = new File(file);
		// String expectKey = pre + "_" + UUID.randomUUID().toString();
		String expectKey = pre;
		uploadManager.put(files, expectKey, uptoken, new UpCompletionHandler()
		{
			public void complete(String k, ResponseInfo rinfo,
					JSONObject response)
			{
				String s = k + ", " + rinfo + ", " + response;
				Log.i("qiniutest", s);
				// String key = getKey(k, response);
				// String o = hint.getText() + "\r\n\r\n";
				// hint.setText(o + s + "\n" + "http://" + bucketName +
				// "qiniudn.com/" + key);
			}
		}, new UploadOptions(null, "image/jpegsss", true, null, null));
	}

	private String getKey(String k, JSONObject response)
	{
		if (k != null)
		{
			return k;
		} else
		{
			return response.optString("key", "<获取key失败>");
		}
	}
}

package com.soloman.org.cn.ui.mail_list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.soloman.org.cn.MyApplication;
import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.SortAdapter;
import com.soloman.org.cn.bean.SortModel;
import com.soloman.org.cn.ui.ActCity;
import com.soloman.org.cn.ui.address.ActAppointmentsAddress;
import com.soloman.org.cn.utis.AsyncTaskBase;
import com.soloman.org.cn.utis.CharacterParser;
import com.soloman.org.cn.utis.ConstactUtil;
import com.soloman.org.cn.utis.PinyinComparator;
import com.soloman.org.cn.utis.PreferenceConstants;
import com.soloman.org.cn.utis.PreferenceUtils;
import com.soloman.org.cn.wight.ClearEditText;
import com.soloman.org.cn.wight.LoadingView;
import com.soloman.org.cn.wight.SideBar;
import com.soloman.org.cn.wight.SideBar.OnTouchingLetterChangedListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 通讯录列表
 * 
 * @author Mac
 * 
 */
public class ActMail extends Activity
{
	Bundle bb;
	private PreferenceUtils preferences;
	/**
	 * 返回
	 */
	private ImageView act_tv_common_address_return;
	/**
	 * 拨打客服电话
	 */
	private ImageView act_tv_common_address_phone;
	private TextView tv;
	private PopupWindow mPopupWindow;
	// 屏幕的width
	private int mScreenWidth;
	// 屏幕的height
	private int mScreenHeight;
	// PopupWindow的width
	private int mPopupWindowWidth;
	// PopupWindow的height
	private int mPopupWindowHeight;

	private Context mContext;
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private Map<String, String> callRecords;
	private LoadingView mLoadingView;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mains);
		MyApplication.getInstance().addActivity(this);
		mContext = ActMail.this;
		preferences = PreferenceUtils.getInstance(ActMail.this,
				PreferenceConstants.LOGIN_PREF);
		findView();
		setupListener();
		initData();

	}

	private void findView()
	{
		mLoadingView = (LoadingView) findViewById(R.id.loading);
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		act_tv_common_address_return = (ImageView) findViewById(R.id.act_tv_common_address_return);
		act_tv_common_address_phone = (ImageView) findViewById(R.id.act_tv_common_address_phone);
	}

	private void setupListener()
	{
		// TODO Auto-generated method stub
		act_tv_common_address_return.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				finish();
			}
		});

		act_tv_common_address_phone.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getPopupWindowInstance();
				mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			}
		});
	}

	/*
	 * 获取PopupWindow实例
	 */
	private void getPopupWindowInstance()
	{
		if (null != mPopupWindow)
		{
			mPopupWindow.dismiss();
			return;
		} else
		{
			initPopuptWindow();
		}
	}

	/*
	 * 创建PopupWindow
	 */
	private void initPopuptWindow()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.act_phone, null);
		popupWindow.findViewById(R.id.phone_ll).setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						mPopupWindow.dismiss();
					}
				});
		popupWindow.findViewById(R.id.phone_ll_cancel).setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						mPopupWindow.dismiss();
					}
				});

		tv = (TextView) popupWindow.findViewById(R.id.city_tv__);
		tv.setText(preferences.getString("customer", ""));
		tv.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

			}
		});
		popupWindow.findViewById(R.id.phone_ll_).setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0)
					{
						try
						{
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + tv.getText().toString()));
							startActivity(intent);
						} catch (Exception e)
						{
							// TODO: handle exception
							Toast.makeText(ActMail.this,"无有效手机卡", Toast.LENGTH_LONG).show();
						}

					}
				});
		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height
		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		// 需要设置一下此参数，点击外边可消失
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		mPopupWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		mPopupWindow.setFocusable(true);
		// 获取屏幕和PopupWindow的width和height
		mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
		mScreenWidth = getWindowManager().getDefaultDisplay().getHeight();
		mPopupWindowWidth = mPopupWindow.getWidth();
		mPopupWindowHeight = mPopupWindow.getHeight();
	}

	private void initData()
	{

		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar.setTextView(dialog);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener()
		{

			@SuppressLint("NewApi")
			@Override
			public void onTouchingLetterChanged(String s)
			{
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1)
				{
					sortListView.setSelection(position);
				}
			}
		});

		sortListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				// Toast.makeText(getApplication(),
				// ((SortModel)adapter.getItem(position)).getName(),
				// Toast.LENGTH_SHORT).show();
				String number = callRecords.get(((SortModel) adapter
						.getItem(position)).getName());

				String name = SourceDateList.get(position).getName();
				Intent intent = new Intent(ActMail.this, ActMailText.class);
				Bundle bundle = new Bundle();
				bundle.putString("number", number);
				bundle.putString("name", name);
				intent.putExtras(bundle);
				startActivityForResult(intent, 10);
				

			}
		});

		new AsyncTaskConstact(mLoadingView).execute(0);

	}

	private class AsyncTaskConstact extends AsyncTaskBase
	{

		public AsyncTaskConstact(LoadingView loadingView)
		{
			super(loadingView);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Integer doInBackground(Integer... params)
		{
			int result = -1;
			callRecords = ConstactUtil.getAllCallRecords(mContext);
			result = 1;
			return result;
		}

		@Override
		protected void onPostExecute(Integer result)
		{

			super.onPostExecute(result);
			if (result == 1)
			{
				List<String> constact = new ArrayList<String>();
				for (Iterator<String> keys = callRecords.keySet().iterator(); keys
						.hasNext();)
				{
					String key = keys.next();
					constact.add(key);
				}
				String[] names = new String[]
				{};
				names = constact.toArray(names);
				SourceDateList = filledData(names);

				// 根据a-z进行排序源数据
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new SortAdapter(mContext, SourceDateList);
				sortListView.setAdapter(adapter);

				mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

				// 根据输入框输入值的改变来过滤搜索
				mClearEditText.addTextChangedListener(new TextWatcher()
				{

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count)
					{
						// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
						filterData(s.toString());
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after)
					{

					}

					@Override
					public void afterTextChanged(Editable s)
					{
					}
				});
			}

		}

	}

	private List<SortModel> filledData(String[] date)
	{
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++)
		{
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]"))
			{
				sortModel.setSortLetters(sortString.toUpperCase());
			} else
			{
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr)
	{
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr))
		{
			filterDateList = SourceDateList;
		} else
		{
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList)
			{
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString()))
				{
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
/*
	ljppff
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1)
		{
			bb = data.getExtras();
			switch (requestCode)
			{
			case 10:
				// 获取启动这个activity的intent
				Intent intent = getIntent();
				intent.putExtras(bb);
				ActMail.this.setResult(1, intent);// 跳转回原来的activity
				ActMail.this.finish();// 结束当前activity
				break;
			default:
				break;
			}
		}
	}
}

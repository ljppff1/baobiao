package com.soloman.org.cn.ui.map;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.soloman.org.cn.R;
import com.soloman.org.cn.adapter.CommonMapAdapter;
import com.soloman.org.cn.adapter.CommonMapTextAdapter;
import com.soloman.org.cn.utis.AMapUtil;
import com.soloman.org.cn.utis.ToastUtil;
import com.soloman.org.cn.view.XListView;
import com.soloman.org.cn.view.XListView.IXListViewListener;

/**
 * 地址中的地图选点
 * 
 * @author Mac
 * 
 */
public class ActMapCity extends Activity implements OnPoiSearchListener,
		LocationSource, AMapLocationListener, OnCameraChangeListener,
		IXListViewListener, TextWatcher
{
	private LatLng changed;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private AMap aMap;
	private MapView mapView;
	private ProgressDialog progDialog = null;// 搜索时进度条
	private PoiSearch.Query query;// Poi查询条件类
	private int currentPage = 0;// 当前页面，从0开始计数
	private PoiSearch poiSearch;
	private PoiResult poiResult; // poi返回的结果
	private List<PoiItem> poiItems;// poi数据
	private PoiOverlay poiOverlay;// poi图层
	private XListView act_lv_common_address_list;
	private CommonMapAdapter acs;
	private AutoCompleteTextView searchText;// 输入搜索关键字
	List<Tip> tipLists;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_map_city);
		mapView = (MapView) findViewById(R.id.map);
		mapView.setBackgroundColor(Color.BLACK);
		mapView.onCreate(savedInstanceState);
		init();
	}

	/**
	 * 初始化
	 */
	private void init()
	{
		if (aMap == null)
		{
			aMap = mapView.getMap();
			setupView();
		}

	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		// 移动后的监听
		aMap.setOnCameraChangeListener(this);

		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		searchText = (AutoCompleteTextView) findViewById(R.id.keyWord);
		searchText.addTextChangedListener(this);// 添加文本输入框监听事件
		searchText.setOnItemClickListener(new AutoCompleteTextViews());
		act_lv_common_address_list = (XListView) findViewById(R.id.act_lv_common_address_list);
		poiItems = new ArrayList<PoiItem>();
		acs = new CommonMapAdapter(poiItems, ActMapCity.this);
		act_lv_common_address_list.setAdapter(acs);
		act_lv_common_address_list.setXListViewListener(this);
		act_lv_common_address_list
				.setOnItemClickListener(new MyItemClickListener());
		act_lv_common_address_list.setPullLoadEnable(true);
		act_lv_common_address_list.setPullRefreshEnable(false);

	}

	class AutoCompleteTextViews implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			// TODO Auto-generated method stub
			// 获取启动这个activity的intent
			Intent intent = getIntent();
			Bundle b = new Bundle();
			b.putString("Title", tipLists.get(arg2).getName().toString());// 把数据塞入intent里面
			intent.putExtras(b);
			ActMapCity.this.setResult(1, intent);// 跳转回原来的activity
			ActMapCity.this.finish();// 结束当前activity
		}

	}

	class MyItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			// TODO Auto-generated method stub

			// 获取启动这个activity的intent
			Intent intent = getIntent();
			Bundle b = new Bundle();
			b.putString("Title", poiItems.get(arg2 - 1).getTitle().toString());// 把数据塞入intent里面
			intent.putExtras(b);
			ActMapCity.this.setResult(1, intent);// 跳转回原来的activity
			ActMapCity.this.finish();// 结束当前activity
		}
	}

	@Override
	public void onCameraChange(CameraPosition arg0)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 移动后
	 */
	@Override
	public void onCameraChangeFinish(CameraPosition arg0)
	{
		// TODO Auto-generated method stub
		LatLng mTarget = aMap.getCameraPosition().target;
		// showProgressDialog();// 显示进度框
		currentPage = 0;
		query = new PoiSearch.Query("", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(30);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页
		query.setLimitGroupbuy(false);
		query.setLimitDiscount(false);

		poiSearch = new PoiSearch(this, query);
		poiSearch.setOnPoiSearchListener(this);
		poiSearch.setBound(new SearchBound(AMapUtil
				.convertToLatLonPoint(mTarget), 2000, true));//
		// 设置搜索区域为以lp点为圆心，其周围2000米范围
		/*
		 * List<LatLonPoint> list = new ArrayList<LatLonPoint>(); list.add(lp);
		 * list.add(AMapUtil.convertToLatLonPoint(Constants.BEIJING));
		 * poiSearch.setBound(new SearchBound(list));// 设置多边形poi搜索范围
		 */
		poiSearch.searchPOIAsyn();// 异步搜索
	}

	/**
	 * POI搜索回调方法
	 */
	@Override
	public void onPoiSearched(PoiResult result, int rCode)
	{
		if (rCode == 0)
		{
			if (result != null && result.getQuery() != null)
			{// 搜索poi的结果
				if (result.getQuery().equals(query))
				{// 是否是同一条
					poiResult = result;
					if (currentPage == 0)
					{
						poiItems.clear();
					}
					poiItems.addAll(poiResult.getPois());// 取得第一页的poiitem数据，页数从数字0开始
					acs.notifyDataSetChanged();
					// for (int i = 0; i < poiItems.size(); i++)
					// {
					// System.out.println(poiItems.get(i).getTitle()
					// + "__getTitle");
					// System.out.println(poiItems.get(i).getSnippet()
					// + "__getSnippet");
					//
					// }
					onLoad();
				}
			} else
			{
				ToastUtil.show(ActMapCity.this, "对不起，没有搜索到相关数据！");
			}
		} else if (rCode == 27)
		{
			ToastUtil.show(ActMapCity.this, "搜索失败,请检查网络连接！");
		} else if (rCode == 32)
		{
			ToastUtil.show(ActMapCity.this, "key验证无效！");
		} else
		{
			ToastUtil.show(ActMapCity.this, "未知错误，请稍后重试!错误码为" + rCode);
		}

	}

	/**
	 * poi没有搜索到数据，返回一些推荐城市的信息
	 */
	private void showSuggestCity(List<SuggestionCity> cities)
	{
		String infomation = "推荐城市\n";
		for (int i = 0; i < cities.size(); i++)
		{
			infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
					+ cities.get(i).getCityCode() + "城市编码:"
					+ cities.get(i).getAdCode() + "\n";
		}
		ToastUtil.show(ActMapCity.this, infomation);

	}

	@Override
	public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1)
	{
		// TODO Auto-generated method stub

	}

	// 废弃方法
	@Override
	public void onLocationChanged(Location arg0)
	{
	}

	@Override
	public void onProviderDisabled(String arg0)
	{
	}

	@Override
	public void onProviderEnabled(String arg0)
	{
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2)
	{
	}

	// 定位成功后的回调
	@Override
	public void onLocationChanged(AMapLocation aLocation)
	{
		if (mListener != null && aLocation != null)
		{
			changed = new LatLng(aLocation.getLatitude(),
					aLocation.getLongitude());
			aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(changed, 18));
		}

	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener)
	{
		mListener = listener;
		if (mAMapLocationManager == null)
		{
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			/*
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, -1, 15, this);
			mAMapLocationManager.setGpsEnable(false);
		}

	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate()
	{
		mListener = null;
		if (mAMapLocationManager != null)
		{
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}

	private void onLoad()
	{
		act_lv_common_address_list.stopRefresh();
		act_lv_common_address_list.stopLoadMore();
		act_lv_common_address_list.setRefreshTime("刚刚");
	}

	// 下啦刷新
	@Override
	public void onRefresh()
	{
		System.out.println("aa");
	}

	// 上啦加载
	@Override
	public void onLoadMore()
	{
		currentPage++;
		query.setPageNum(currentPage);// 设置查后一页
		poiSearch.searchPOIAsyn();
	}

	@Override
	public void afterTextChanged(Editable arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		String newText = s.toString().trim();
		Inputtips inputTips = new Inputtips(ActMapCity.this,
				new InputtipsListener()
				{

					@Override
					public void onGetInputtips(List<Tip> tipList, int rCode)
					{
						if (rCode == 0)
						{// 正确返回
							tipLists = tipList;
							List<String> listString = new ArrayList<String>();
							for (int i = 0; i < tipList.size(); i++)
							{
								listString.add(tipList.get(i).getName());
							}
							ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
									getApplicationContext(),
									R.layout.route_inputs, listString);
							searchText.setAdapter(aAdapter);
							aAdapter.notifyDataSetChanged();
						}
					}
				});
		try
		{
			inputTips.requestInputtips(newText, FraMap.city);// 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号

		} catch (AMapException e)
		{
			e.printStackTrace();
		}
	}
}

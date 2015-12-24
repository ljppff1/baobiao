package com.soloman.org.cn.ui.map;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.AMap.OnMarkerDragListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.Bodyguard;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;

/**
 * 地图模式
 * 
 * @author Mac
 * 
 */
public class FraMap extends Fragment implements LocationSource,
		AMapLocationListener, OnCameraChangeListener, OnMarkerClickListener,
		OnInfoWindowClickListener, OnMarkerDragListener, OnMapLoadedListener,
		OnClickListener, InfoWindowAdapter
{
	public static String city;
	public static String Location;
	private MarkerOptions markerOption;
	private List<Bodyguard> lt;
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private Marker marker;// 定位雷达小图标
	private FrameLayout mRootViewGroup;
	private View mNaviMapContainer;
	private static final FrameLayout.LayoutParams FULL_LAYOUT = new FrameLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
	private ImageView fra_map_tvs;
	private ImageView fra_map_ib;
	private LatLng changed;
	private List<Marker> markerlst;
	private SupportMapFragment aMapFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		mRootViewGroup = new FrameLayout(getActivity());
		mRootViewGroup.setLayoutParams(FULL_LAYOUT);
		mNaviMapContainer = inflater.inflate(R.layout.fra_map, null);
		mRootViewGroup.addView(mNaviMapContainer, FULL_LAYOUT);
		fra_map_tvs = (ImageView) mNaviMapContainer
				.findViewById(R.id.fra_map_tvs);
		fra_map_ib = (ImageView) mNaviMapContainer
				.findViewById(R.id.fra_map_ib);
		fra_map_ib.setOnClickListener(this);
		mapView = (MapView) mNaviMapContainer.findViewById(R.id.map);
		mapView.setBackgroundColor(Color.BLACK);
		mapView.onCreate(savedInstanceState);
		init();
		return mRootViewGroup;
	}

	/**
	 * 初始化
	 */
	private void init()
	{
		if (aMap == null)
		{
			aMap = mapView.getMap();
			// aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
			setUpMap();
		}

	}

	private void setUpMap()
	{
		// TODO Auto-generated method stub
		aMap.setOnCameraChangeListener(this);
		aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
		aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
		aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
		aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式

		aMap.setMyLocationRotateAngle(180);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		// aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

	}

	/**
	 * 添加覆盖物
	 */
	private void addMarkersToMap()
	{
		// TODO Auto-generated method stub
		// ArrayList<MarkerOptions> markerOptionlst = new
		// ArrayList<MarkerOptions>();
		markerlst = new ArrayList<Marker>();
		for (int i = 0; i < lt.size(); i++)
		{
			String locations = lt.get(i).getLocation();

			markerOption = new MarkerOptions();
			markerOption.position(new LatLng(
					Double.parseDouble(locations.substring(
							locations.indexOf(",") + 1, locations.length())),
					Double.parseDouble(locations.substring(0,
							locations.indexOf(",")))));
			markerOption.draggable(true);
			markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
					.decodeResource(getResources(), R.drawable.baobiaos3)));
			// 将Marker设置为贴地显示，可以双指下拉看效果
			// markerOption.setFlat(true);
			// markerOptionlst.add(markerOption);
			markerlst.add(aMap.addMarker(markerOption));
		}
		// markerlst = aMap.addMarkers(markerOptionlst, false);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		mapView.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		mapView.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy()
	{
		mapView.onDestroy();
		super.onDestroy();
	}

	@Override
	public void onLocationChanged(Location arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onCameraChange(CameraPosition arg0)
	{
		// TODO Auto-generated method stub
	}

	int i = 0;

	/**
	 * 移动后
	 */
	@Override
	public void onCameraChangeFinish(CameraPosition cameraPosition)
	{
		LatLng mTarget = aMap.getCameraPosition().target;
		System.out.println(mTarget + "!!!aaaaaa");
		LatLng mTargets = LatLngTransformation(mTarget);
		System.out.println(mTargets + "______");
		String longitude = String.valueOf(mTargets.longitude);
		String latitude = String.valueOf(mTargets.latitude);
		String latitudeAndLongitudes = longitude + "," + latitude;
		if (i > 0)
		{
			Locations(latitudeAndLongitudes);

		}
		++i;
		System.out.println("1111111111111111111111");
	}

	/**
	 * 转换
	 * 
	 * @param mTargets
	 * @return
	 */
	private LatLng LatLngTransformation(LatLng mTargets)
	{
		String latitude = String.valueOf(mTargets.latitude);
		String longitude = String.valueOf(mTargets.longitude);
		String latitudes = latitude.substring(0, 9);
		String longitudes = longitude.substring(0, 10);
		double latitudess = Double.parseDouble(latitudes);
		double longitudess = Double.parseDouble(longitudes);
		LatLng lin = new LatLng(latitudess, longitudess);
		return lin;
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation)
	{
		if (mListener != null && aLocation != null)
		{
			changed = new LatLng(aLocation.getLatitude(),
					aLocation.getLongitude());
			city = aLocation.getCity();
			// changed = new LatLng(22.542575, 113.959052);
			System.out.println(aLocation.getLatitude() + "______1");
			System.out.println(aLocation.getLongitude() + "______1");

			markerOption = new MarkerOptions();
			markerOption.position(changed);
			markerOption.draggable(true);
			markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
					.decodeResource(getResources(), R.drawable.yuandian1)));
			// 将Marker设置为贴地显示，可以双指下拉看效果
			// markerOption.setFlat(true);
			aMap.addMarker(markerOption);
			addMarkers();
			aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(changed, 18));
			float bearing = aMap.getCameraPosition().bearing;
			aMap.setMyLocationRotateAngle(bearing);// 设置小蓝点旋转角度
			String latitudeAndLongitudes = String.valueOf(aLocation
					.getLongitude()
					+ ","
					+ String.valueOf(aLocation.getLatitude()));
			Location = latitudeAndLongitudes;
			Locations(latitudeAndLongitudes);
		}
	}

	/**
	 * 添加当前坐标标识
	 */
	private void addMarkers()
	{
		markerOption = new MarkerOptions();
		markerOption.position(changed);
		markerOption.draggable(true);
		markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
				.decodeResource(getResources(), R.drawable.yuandian1)));
		// 将Marker设置为贴地显示，可以双指下拉看效果
		// markerOption.setFlat(true);
		aMap.addMarker(markerOption);
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
			mAMapLocationManager = LocationManagerProxy
					.getInstance(getActivity());
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
			;
		}
		mAMapLocationManager = null;
	}

	// Latitude and longitude
	private void Locations(String latitudeAndLongitude)
	{
		// boolean is = false;
		// if (NetworkJudge.getNetWorkType(getActivity()) !=
		// NetworkJudge.NETWORKTYPE_INVALID)
		// {
		// is = true;
		// }
		RequestParams requestParams = new RequestParams();
		requestParams.put("location", latitudeAndLongitude);
		requestParams.put("level", 0 + "");
		requestParams.put("page", 1 + "");
		requestParams.put("size", 999 + "");
		HttpRestClient.getHttpHuaShangha(getActivity(), "bodyguards/index",
				"v2", requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						System.out.println(response + "  aaaaa");
						try
						{
							if (response.getInt("code") == 200)
							{
								JSONObject obj = response.getJSONObject("data");
								lt = new ArrayList<Bodyguard>();
								try
								{
									JSONArray ja = obj
											.getJSONArray("bodyguards");
									if (ja.toString().equals("[]"))
									{
										aMap.clear();
										addMarkers();
									}
									for (int i = 0; i < ja.length(); i++)
									{
										Bodyguard bd = new Bodyguard();
										JSONObject jbo = ja.getJSONObject(i);
										bd.setId(jbo.getInt("id"));
										bd.setName(jbo.getString("name"));
										bd.setPhone_number(jbo
												.getString("phone_number"));
										bd.setSex(jbo.getInt("sex"));
										bd.setLevel(jbo.getInt("level"));
										bd.setCity(jbo.getString("city"));
										bd.setDescription(jbo
												.getString("description"));
										bd.setAvatar_image_key(jbo
												.getString("avatar_image_key"));
										bd.setBirthday(jbo
												.getString("birthday"));
										bd.setHeight(jbo.getString("height"));
										bd.setWeight(jbo.getString("weight"));
										bd.setCertificate_type(jbo
												.getString("certificate_type"));
										bd.setCertificate_number(jbo
												.getString("certificate_number"));
										bd.setMap_id(jbo.getString("map_id"));
										bd.setLocation(jbo
												.getString("location"));
										bd.setUser_id(jbo.getString("user_id"));
										bd.setAddress(jbo.getString("address"));
										bd.setBank_name(jbo
												.getString("bank_name"));
										bd.setBank_branch_name(jbo
												.getString("bank_branch_name"));
										bd.setBank_account(jbo
												.getString("bank_account"));
										bd.setEmergency_contact_person_name(jbo
												.getString("emergency_contact_person_name"));
										bd.setEmergency_contact_person_phone_number(jbo
												.getString("emergency_contact_person_phone_number"));
										bd.setEmergency_contact_person_relationship(jbo
												.getString("emergency_contact_person_relationship"));
										bd.setCertificate_status(jbo
												.getString("certificate_status"));
										bd.setBank_status(jbo
												.getString("bank_status"));
										bd.setVerify_status(jbo
												.getString("verify_status"));
										bd.setCertificate_image_key(jbo
												.getString("certificate_image_key"));
										bd.setBank_image_key(jbo
												.getString("bank_image_key"));
										bd.setReceivable(jbo
												.getString("receivable"));
										bd.setGross_income(jbo
												.getString("gross_income"));
										bd.setServiced_people_count(jbo
												.getString("serviced_people_count"));
										bd.setServiced_time(jbo
												.getString("serviced_time"));
										bd.setDistance(jbo
												.getString("distance"));
										lt.add(bd);
									}
									addMarkersToMap();

								} catch (JSONException e)
								{

									e.printStackTrace();
								}
							}
						} catch (JSONException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});

	}

	/**
	 * 监听自定义infowindow窗口的infocontents事件回调
	 */
	@Override
	public View getInfoContents(Marker marker)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 监听自定义infowindow窗口的infowindow事件回调
	 */
	@Override
	public View getInfoWindow(Marker marker)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.fra_map_ib:
			// 定位当前
			aMap.animateCamera(CameraUpdateFactory.changeLatLng(changed));
			break;

		default:
			break;
		}
	}

	/**
	 * 监听amap地图加载成功事件回调
	 */
	@Override
	public void onMapLoaded()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 监听拖动marker时事件回调
	 */
	@Override
	public void onMarkerDrag(Marker marker)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 监听拖动marker结束事件回调
	 */
	@Override
	public void onMarkerDragEnd(Marker marker)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 监听开始拖动marker事件回调
	 */
	@Override
	public void onMarkerDragStart(Marker marker)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 监听点击infowindow窗口事件回调
	 */
	@Override
	public void onInfoWindowClick(Marker marker)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 对marker标注点点击响应事件
	 */
	@Override
	public boolean onMarkerClick(Marker marker)
	{
		// TODO Auto-generated method stub
		for (int i = 0; i < markerlst.size(); i++)
		{
			boolean is = marker.getPosition() == markerlst.get(i).getPosition();
			System.out.println(is + "@@0");
			// 判断点击是否为同一个覆盖物
			if (is)
			{
				System.out.println(lt.get(i).get_location() + "@@1");
				System.out.println(marker.getPosition() + "@@2");
				System.out.println(markerlst.get(i).getPosition() + "@@3");
			}
		}
		return true;
	}
}

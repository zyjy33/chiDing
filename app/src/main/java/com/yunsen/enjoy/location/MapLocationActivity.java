package com.yunsen.enjoy.location;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.amap.api.services.geocoder.StreetNumber;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.model.PoiAddressInfo;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.AndroidAdjustResizeBugFix;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/7/007.
 */

public class MapLocationActivity extends BaseFragmentActivity implements AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener, MultiItemTypeAdapter.OnItemClickListener {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.action_back_layout)
    LinearLayout actionBackLayout;
    @Bind(R.id.team_top_layout)
    LinearLayout teamTopLayout;
    @Bind(R.id.map_view)
    MapView mapView;
    //初始化地图控制器对象
    AMap aMap;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.search_edt)
    EditText searchEdt;
    @Bind(R.id.search_btn)
    Button searchBtn;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    private LocationManager mLm;
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mlocationClient;
    private LatLng mLatLng = new LatLng(39.906901, 116.397972);
    private MarkerOptions mMarkerOption;
    private Marker mMarker;

    private boolean mLocationUpMarker = true;
    private String mSearchKey = "银行";
    private String mCityCode;
    private ArrayList<PoiAddressInfo> mDatas;
    private PoiAddressAdapter mAdapter;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_map_location;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("地址定位");
         mapView.getLayoutParams().height = DeviceUtil.getHeight(this) / 3;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatas = new ArrayList<>();
        mAdapter = new PoiAddressAdapter(this, R.layout.poi_address_item, mDatas);
        recyclerView.setAdapter(mAdapter);


        if (aMap == null) {
            aMap = mapView.getMap();
        }
        requestPermission(new String[]{Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION}, 1);
        upMarker(mLatLng, true);

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                upMarker(latLng, true);
            }
        });
        aMap.setMyLocationEnabled(true);
        // 定义 Marker拖拽的监听
        AMap.OnMarkerDragListener markerDragListener = new AMap.OnMarkerDragListener() {
            // 当marker开始被拖动时回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDragStart(Marker arg0) {
                Log.e(TAG, "onMarkerDragStart: ");
            }

            // 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                LatLng position = arg0.getPosition();
                upMarker(position, true);
                Log.e(TAG, "onMarkerDragEnd: ");
            }

            // 在marker拖动过程中回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDrag(Marker arg0) {
                Log.e(TAG, "onMarkerDrag: ");
            }
        };
        // 绑定marker拖拽事件
        aMap.setOnMarkerDragListener(markerDragListener);

        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e(TAG, "onMarkerClick: ");
                upMarker(marker.getPosition(), true);
                return false;
            }
        };
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);

    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        //得到系统的位置服务，判断GPS是否激活
        mLm = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean ok = mLm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {
            if (mlocationClient != null) {
                mlocationClient.startLocation();
            } else {
                initAddress();
            }
            initAddress();
        } else {
            Toast.makeText(this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    private void initAddress() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        mLocationOption.setOnceLocation(true);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        Log.e(TAG, "onLocationChanged: aa");
        if (amapLocation != null) {
            String city = amapLocation.getCity();
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                //定位完成之后更新数据
                mLatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                if (mLocationUpMarker) {
                    mLocationUpMarker = false;
                    upMarker(mLatLng, true);
                    mapView.invalidate();
                }
                Log.e(TAG, "onLocationChanged: amapLocation.getLongitude()=" + amapLocation.getLongitude() + "   amapLocation.getLatitude()=" + amapLocation.getLatitude());
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                CityPicker.getInstance().locateComplete(new LocatedCity(city,
                        amapLocation.getProvince(), amapLocation.getCityCode()), LocateState.FAILURE);
            }
        }
    }

    private void upMarker(LatLng latLng, boolean needSearch) {
        if (mMarker != null) {
            mMarker.remove();
        }

        mMarkerOption = new MarkerOptions();
        aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng));
        mMarkerOption.position(latLng);
        mMarkerOption.draggable(true);//设置Marker可拖动
        mMarkerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.upload_location)));
        //点标记是否可拖拽
        //mk.draggable(true);
        //点标记的锚点
//        mMarkerOption.anchor(1.5f, 3.5f);
        //点的透明度
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        mMarker = aMap.addMarker(mMarkerOption);
        if (needSearch) {
            regeocodeSearch(latLng.latitude, latLng.longitude, 3000);

        }
    }


    /**
     * 开始默认查询附近地点
     */
    private void seartchPoiStart(String key, String cityCode, Marker locationMarker) {
        PoiSearch.Query query = new PoiSearch.Query(key, "", cityCode);
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
        if (locationMarker != null) {
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(locationMarker.getPosition().latitude,
                    locationMarker.getPosition().longitude), 1000));//设置周边搜索的中心点以及半径
        }

    }

    private static final String TAG = "MapLocationActivity";
    private boolean mBackEnable = false;
    private boolean mIsBtnBack = false;
    private int rootBottom = Integer.MIN_VALUE;
    public int mHeight;
    public static int keyboardHeight = 0;

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(mOnGlobalLayoutListener);
//        } else {
//            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
//        }
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        if (rCode == 1000) {//成功
            ArrayList<PoiItem> pois = poiResult.getPois();
            ArrayList<PoiAddressInfo> lists = new ArrayList<>();
            for (int i = 0; i < pois.size(); i++) {
                PoiItem poiItem = pois.get(i);
                lists.add(new PoiAddressInfo(poiItem.getTitle(), poiItem.getAdName(), poiItem.getSnippet(), poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude()));
            }
            mAdapter.upBaseDatas(lists);

        } else {//失败

        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        Log.e(TAG, "onPoiItemSearched: " + i);
    }


    private void regeocodeSearch(double lat, double lng, float radius) {
        LatLonPoint point = new LatLonPoint(lat, lng);
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point, radius, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        String preAdd = "";//地址前缀
        if (1000 == rCode) {
            RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
            StringBuffer stringBuffer = new StringBuffer();
            String area = address.getProvince();//省或直辖市
            String loc = address.getCity();//地级市或直辖市
            String subLoc = address.getDistrict();//区或县或县级市
            String ts = address.getTownship();//乡镇
            String thf = null;//道路
            List<RegeocodeRoad> regeocodeRoads = address.getRoads();//道路列表
            if (regeocodeRoads != null && regeocodeRoads.size() > 0) {
                RegeocodeRoad regeocodeRoad = regeocodeRoads.get(0);
                if (regeocodeRoad != null) {
                    thf = regeocodeRoad.getName();
                }
            }
            String subthf = null;//门牌号
            StreetNumber streetNumber = address.getStreetNumber();
            if (streetNumber != null) {
                subthf = streetNumber.getNumber();
            }
            String fn = address.getBuilding();//标志性建筑,当道路为null时显示
            if (area != null) {
                stringBuffer.append(area);
                preAdd += area;
            }
            if (loc != null && !area.equals(loc)) {
                stringBuffer.append(loc);
                preAdd += loc;
            }
            if (subLoc != null) {
                stringBuffer.append(subLoc);
                preAdd += subLoc;
            }
            if (ts != null)
                stringBuffer.append(ts);
            if (thf != null)
                stringBuffer.append(thf);
            if (subthf != null)
                stringBuffer.append(subthf);
            if ((thf == null && subthf == null) && fn != null && !subLoc.equals(fn))
                stringBuffer.append(fn + "附近");
            String ps = "poi";
            List<PoiItem> pois = address.getPois();//获取周围兴趣点
            ArrayList<PoiAddressInfo> datas = new ArrayList<>();
            if (pois != null && pois.size() > 0) {
                for (int i = 0; i < pois.size(); i++) {
                    String title = pois.get(i).getTitle();
                    String adName = pois.get(i).getAdName();
                    String snippet = pois.get(i).getSnippet();
                    LatLonPoint latLonPoint = pois.get(i).getLatLonPoint();
                    double latitude = latLonPoint.getLatitude();
                    double longitude = latLonPoint.getLongitude();
                    datas.add(new PoiAddressInfo(title, adName, snippet, latitude, longitude));
                }
                mAdapter.upBaseDatas(datas);
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    @OnClick({R.id.action_back, R.id.search_btn, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.search_btn:
                seartchPoiStart(searchEdt.getText().toString(), "", null);
                break;
            case R.id.submit_btn:
                Intent data = new Intent();
                String value = searchEdt.getText().toString();
                if (TextUtils.isEmpty(value)) {
                    ToastUtils.makeTextShort("请选择地址");
                } else {
                    data.putExtra(Constants.ADDRESS_KEY, value);
                    setResult(RESULT_OK, data);
                    finish();
                }

                break;
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (position >= 0 && position < mDatas.size()) {
            PoiAddressInfo addressInfo = mDatas.get(position);
            searchEdt.setText(addressInfo.getTitle());
            upMarker(new LatLng(addressInfo.getLatitude(), addressInfo.getLongitude()), false);
        }

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }


}

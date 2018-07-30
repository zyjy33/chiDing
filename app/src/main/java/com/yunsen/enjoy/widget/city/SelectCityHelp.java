package com.yunsen.enjoy.widget.city;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpCityEvent;
import com.yunsen.enjoy.utils.SharedPreference;
import com.yunsen.enjoy.utils.ToastUtils;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class SelectCityHelp {
    private static SelectCityHelp instants;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private SelectCityHelp() {
    }

    public static SelectCityHelp getInstants() {
        if (instants == null) {
            synchronized (SelectCityHelp.class) {
                if (instants == null) {
                    instants = new SelectCityHelp();
                }
            }
        }
        return instants;
    }

    public void show(final FragmentActivity act) {
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
        CityPicker.getInstance()
                .setFragmentManager(act.getSupportFragmentManager())    //此方法必须调用
//                .enableAnimation(true)    //启用动画效果
//                .setAnimationStyle(anim)	//自定义动画
                .setLocatedCity(new LocatedCity("北京", "北京", "101210101"))  //APP自身已定位的城市，默认为null（定位失败）
                .setHotCities(hotCities)    //指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        SharedPreference.getInstance().putString(SpConstants.CITY_KEY, data.getName());
                        EventBus.getDefault().postSticky(new UpCityEvent(EventConstants.UP_CITY, data.getName()));
                        if (!act.isDestroyed()) {
                            act.finish();

                        }
                    }

                    @Override
                    public void onLocate() {
                        //开始定位，这里模拟一下定位
                        initAddress(act);
                    }
                })
                .show();
    }

    private void initAddress(Context ctx) {
        mlocationClient = new AMapLocationClient(ctx);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        //定位完成之后更新数据
                        CityPicker.getInstance().locateComplete(new LocatedCity(amapLocation.getCity(),
                                amapLocation.getProvince(), amapLocation.getCityCode()), LocateState.SUCCESS);
                    } else {
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        CityPicker.getInstance().locateComplete(new LocatedCity(amapLocation.getCity(),
                                amapLocation.getProvince(), amapLocation.getCityCode()), LocateState.FAILURE);
                    }
                }
            }
        });
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


}

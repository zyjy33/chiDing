package com.yunsen.enjoy.activity.buy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.GoodsListAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.NoScrollLinearLayoutManager;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.GlobalStatic;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.widget.BaseScrollView;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/8/3/003.
 */

public class FoodDescriptionActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener, BaseScrollView.OnScrollListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.scroll_view)
    BaseScrollView scrollView;
    @Bind(R.id.action_back_1)
    ImageView actionBack;
    @Bind(R.id.action_bar_title_1)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_share)
    ImageView actionBarShare;
    @Bind(R.id.action_back_complaint)
    TextView actionBackComplaint;
    @Bind(R.id.look_img_tv)
    TextView lookImgTv;
    @Bind(R.id.pay_money_tv)
    TextView payMoneyTv;
    @Bind(R.id.action_bar_layout_1)
    LinearLayout actionBarLayout;
    @Bind(R.id.look_img_img)
    ImageView lookImgImg;
    @Bind(R.id.collect_tv)
    TextView collectTv;
    @Bind(R.id.distance_tv)
    TextView distanceTv;
    @Bind(R.id.service_time_tv)
    TextView serviceTimeTv;
    @Bind(R.id.location_tv)
    TextView locationTv;
    @Bind(R.id.good_food_title)
    TextView goodFoodTitle;
    @Bind(R.id.sell_sum_tv)
    TextView sellSumtv;

    private GoodsListAdapter mAdapter;
    private ArrayList<SProviderModel> mDatas;
    private int mPageIndex = 1;
    private String mGoodsId;
    private SProviderModel mData;
    private boolean mIsCollect;
    private int mCompanyUserId = 0;
    private boolean mHasFinish;
    private AlertDialog mSelectMapDialog;
    private String mGoodsName;

    @Override
    public int getLayout() {
        return R.layout.activity_food_description;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarLayout.getBackground().mutate().setAlpha(30);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mGoodsId = intent.getStringExtra(Constants.GOODS_ID_KEY);
        mGoodsName = intent.getStringExtra(Constants.GOODS_TITLE_KEY);
        actionBarTitle.setText(mGoodsName);
        goodFoodTitle.setText(mGoodsName);
        NoScrollLinearLayoutManager layout = new NoScrollLinearLayoutManager(this);
        layout.setScrollEnabled(false);
        recyclerView.setLayoutManager(layout);
        mDatas = new ArrayList<>();
        mAdapter = new GoodsListAdapter(this, R.layout.goods_list_item, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
        scrollView.setOnScrollListener(this);
    }

    @Override
    public void requestData() {

//        HttpClient.get(URLConstants.CAR_DETAILS_URL + mGoodsId, new HashMap<String, String>(), new HttpResponseHandler<CarDetailsResponse>() {
//            @Override
//            public void onSuccess(CarDetailsResponse response) {
//                super.onSuccess(response);
//                upView(response);
//            }
//
//            @Override
//            public void onFailure(Request request, Exception e) {
//                super.onFailure(request, e);
//            }
//        });


//        HttpProxy.getChangeGoodsList(String.valueOf(mPageIndex), "", "", new HttpCallBack<List<CarDetails>>() {
//            @Override
//            public void onSuccess(List<CarDetails> responseData) {
//                mAdapter.upBaseDatas(responseData);
//            }
//
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//        });


        HttpProxy.getServiceShopInfo(mGoodsId, new HttpCallBack<SProviderModel>() {

            @Override
            public void onSuccess(SProviderModel responseData) {
                mData = responseData;
                mCompanyUserId = responseData.getUser_id();
                isFavorite();
                upView(responseData);
            }


            @Override
            public void onError(Request request, Exception e) {

            }
        });

        HttpProxy.getServiceMoreProvider(mPageIndex, "4", "0", "buzz", "<10", new HttpCallBack<List<SProviderModel>>() {
            @Override
            public void onSuccess(List<SProviderModel> responseData) {
                mAdapter.addBaseDatas(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    /**
     * 是否已经收藏
     */
    private void isFavorite() {
        HttpProxy.getFavoriteCompanyExists(String.valueOf(mCompanyUserId), new HttpCallBack<String>() {
            @Override
            public void onSuccess(String responseData) {
                //关注
                collectTv.setText("已收藏");
                collectTv.setSelected(true);
                mIsCollect = true;
                mHasFinish = true;
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    //未关注
                    collectTv.setText("收藏");
                    collectTv.setSelected(false);
                    mIsCollect = false;
                }
                mHasFinish = true;
            }
        });

    }

    private void upView(SProviderModel data) {
        Glide.with(this)
                .load(data.getImg_url())
                .into(lookImgImg);
        sellSumtv.setText(String.valueOf(data.getPromotion()));
        double lng = data.getLat();
        double lat = data.getLng();
        if (lat != 0 && lng != 0 && GlobalStatic.latitude != 0.0 && GlobalStatic.longitude != 0.0) {
            double algorithm = Utils.algorithm(GlobalStatic.longitude, GlobalStatic.latitude, lng, lat) / 1000;
            BigDecimal b = new BigDecimal(algorithm);
            double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (df < 1) {
                double m = df * 1000;
                distanceTv.setText(m + "m");
            } else {
                distanceTv.setText(df + "km");
            }

        } else {
//            view.setVisibility(View.GONE);
            distanceTv.setText("0.00 km");
        }
        String address = mData.getProvince() + mData.getCity() + mData.getArea() + mData.getAddress();
        locationTv.setText(address);
        Glide.with(this)
                .load(data.getImg_url())
                .into(lookImgImg);
        serviceTimeTv.setText(data.getService_time());
        if (data.getArticle() != null) {
            List<?> list = data.getArticle();
            lookImgTv.setText(list.size());
        } else {
            lookImgTv.setText("0");
        }
    }

    private void addFavorite() {
        if (!mHasFinish) {
            return;
        }
        HttpProxy.getFavoriteCompanyAdd(mGoodsId, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String responseData) {
                ToastUtils.makeTextShort("关注成功");
                collectTv.setText("已收藏");
                collectTv.setSelected(true);
                mIsCollect = true;
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    private void removeFavorite() {
        if (!mHasFinish) {
            return;
        }
        HttpProxy.getFavoriteCompanyChannel(mGoodsId, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String responseData) {
                ToastUtils.makeTextShort("取消关注");
                collectTv.setText("收藏");
                collectTv.setSelected(false);
                mIsCollect = false;
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }


    @OnClick({R.id.action_back_1, R.id.action_bar_share, R.id.action_back_complaint,
            R.id.look_img_tv, R.id.look_img_img, R.id.pay_money_tv, R.id.collect_tv,
            R.id.phone_img, R.id.location_img, R.id.location_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back_1:
                finish();
                break;
            case R.id.action_bar_share:
                if (mData != null) {
                    UIHelper.showShareGoodsActivity(this, mData.getShop_name(), mData.getContact(), "路径暂无", mData.getImg_url());
                }
                break;
            case R.id.action_back_complaint:
                UIHelper.showComplaintActivity(this, String.valueOf(mData.getUser_id()), mData.getShop_name());
                break;
            case R.id.look_img_img:
            case R.id.look_img_tv:
                if (mData != null && mData.getArticle() != null && mData.getArticle().size() > 0) {
                    //查看商家相册
                    UIHelper.showShoppingPhotoActivity(this);
                }
                break;
            case R.id.pay_money_tv:
                if (mData != null) {
                    if (AccountUtils.hasLogin()) {
                        if (AccountUtils.hasBoundPhone()) {
                            UIHelper.showPayActivity(this, String.valueOf(mData.getUser_id()), mData.getShop_name(), mData.getLogo_url());
                        } else {
                            UIHelper.showBundPhoneActivity(this);
                        }
                    } else {
                        UIHelper.showUserLoginActivity(this);
                    }
                } else {
                    ToastUtils.makeTextShort("网络慢，请稍后。。。 ");
                }
                break;
            case R.id.collect_tv:
                if (mIsCollect) {
                    removeFavorite();
                } else {
                    addFavorite();
                }
                break;
            case R.id.phone_img:
                requestPermission(Permission.CALL_PHONE, Constants.CALL_PHONE);
                break;
            case R.id.location_img:
            case R.id.location_tv:
                if (mData != null) {
                    UIHelper.showMapActivity(this, mData, mData.getLng(), mData.getLat(), mData.getBLng(), mData.getBLat(), mData.getAddress());
                }
                break;
        }
    }


    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        if (requestCode == Constants.CALL_PHONE && mData != null) {
            UIHelper.showPhoneNumberActivity(this, mData.getMobile());
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (position >= 0 && position < mDatas.size()) {
            SProviderModel data = mDatas.get(position);
            UIHelper.showFoodDescriptionActivity(this, String.valueOf(data.getUser_id()), data.getShop_name());
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    private static final String TAG = "FoodDescriptionActivity";

    @Override
    public void onScrollChanged(int scrollX, int scrollY) {
        Log.e(TAG, "onScrollChanged: scrollY=" + scrollY);
        if (scrollY > 255) {
            scrollY = 255;
        }
        if (scrollY < 30) {
            scrollY = 30;
        }

        actionBarLayout.getBackground().mutate().setAlpha(scrollY);
        int btnAlpha = 255 - scrollY;
        if (btnAlpha < 0) {
            btnAlpha = 0;
        }
        if (btnAlpha > 180) {
            btnAlpha = 180;
        }
        actionBack.getBackground().mutate().setAlpha(btnAlpha);
        actionBarShare.getBackground().mutate().setAlpha(btnAlpha);
        actionBackComplaint.getBackground().mutate().setAlpha(btnAlpha);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        actionBarLayout.getBackground().mutate().setAlpha(255);
        actionBack.getBackground().mutate().setAlpha(255);
        actionBackComplaint.getBackground().mutate().setAlpha(255);
    }

}

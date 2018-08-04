package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.GoodsListAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpClient;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.HttpResponseHandler;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.model.response.CarDetailsResponse;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.NoScrollLinearLayoutManager;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.BaseScrollView;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_share)
    ImageView actionBarShare;
    @Bind(R.id.action_back_complaint)
    TextView actionBackComplaint;
    @Bind(R.id.look_img_tv)
    TextView lookImgTv;
    @Bind(R.id.action_bar_layout)
    LinearLayout actionBarLayout;
    @Bind(R.id.look_img_img)
    ImageView lookImgImg;

    private GoodsListAdapter mAdapter;
    private ArrayList<SProviderModel> mDatas;
    private int mPageIndex = 1;
    private String mGoodsId;

    @Override
    public int getLayout() {
        return R.layout.activity_food_description;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarLayout.getBackground().setAlpha(30);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mGoodsId = intent.getStringExtra(Constants.GOODS_ID_KEY);

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
                upView(responseData);
            }


            @Override
            public void onError(Request request, Exception e) {

            }
        });

        HttpProxy.getServiceMoreProvider(mPageIndex, "4", new HttpCallBack<List<SProviderModel>>() {
            @Override
            public void onSuccess(List<SProviderModel> responseData) {
                mAdapter.addBaseDatas(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    private void upView(SProviderModel data) {
        Glide.with(this)
                .load(data.getImg_url())
                .into(lookImgImg);
    }

    @OnClick({R.id.action_back, R.id.action_bar_share, R.id.action_back_complaint,
            R.id.look_img_tv, R.id.look_img_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.action_bar_share:
                UIHelper.showShareGoodsActivity(this, "测试标题", "测试描述", "测试路径", "测试图片路径");
                break;
            case R.id.action_back_complaint:
                ToastUtils.makeTextShort("投诉");
                break;
            case R.id.look_img_img:
            case R.id.look_img_tv: //查看商家相册
                UIHelper.showShoppingPhotoActivity(this);
                break;
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (position >= 0 && position < mDatas.size()) {
            SProviderModel data = mDatas.get(position);
            UIHelper.showFoodDescriptionActivity(this, String.valueOf(data.getUser_id()), data.getName());
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

        actionBarLayout.getBackground().setAlpha(scrollY);
        int btnAlpha = 255 - scrollY;
        if (btnAlpha < 0) {
            btnAlpha = 0;
        }
        if (btnAlpha > 180) {
            btnAlpha = 180;
        }
        actionBack.getBackground().setAlpha(btnAlpha);
        actionBarShare.getBackground().setAlpha(btnAlpha);
        actionBackComplaint.getBackground().setAlpha(btnAlpha);
    }
}

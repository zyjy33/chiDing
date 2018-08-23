package com.yunsen.enjoy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.pay.MonneyChongZhiActivity;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.DeviceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/8/7/007.
 */

public class ApplyServiceFragment extends BaseFragment {
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
    @Bind(R.id.not_service_layout)
    LinearLayout notServiceLayout;
    @Bind(R.id.next_btn)
    Button nextBtn;
    @Bind(R.id.apply_top_img)
    ImageView applyTopImg;
    @Bind(R.id.goto_shopping)
    Button gotoShopping;
    @Bind(R.id.setting_shopping_money)
    Button settingShoppingMoney;
    private boolean mIsService = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply_service;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        actionBack.setVisibility(View.INVISIBLE);
        actionBarTitle.setText("申请商家");
        applyTopImg.getLayoutParams().height = (int) (DeviceUtil.getScreenWidth() * 0.45);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        requestIsService();
    }

    private void requestIsService() {
        HttpProxy.getIsFacilitator(AccountUtils.getUser_id(), new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                actionBarTitle.setText("商家");
                nextBtn.setText("进入商家商城");
                notServiceLayout.setVisibility(View.GONE);
                gotoShopping.setVisibility(View.VISIBLE);
                settingShoppingMoney.setVisibility(View.VISIBLE);
                mIsService = true;
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    actionBarTitle.setText("申请商家");
                    nextBtn.setText("立即入驻");
                    notServiceLayout.setVisibility(View.VISIBLE);
                    gotoShopping.setVisibility(View.GONE);
                    settingShoppingMoney.setVisibility(View.GONE);
                    mIsService = false;
                }
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(UpUiEvent event) {
        switch (event.getEventId()) {
            case EventConstants.APP_LOGIN:
                requestIsService();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.next_btn, R.id.goto_shopping, R.id.setting_shopping_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next_btn:
                if (mIsService) {
                    UIHelper.showPartsShopActivity(getActivity());
                } else {
                    UIHelper.showApplyServiceSecondActivity(getActivity());
                }
                break;
            case R.id.goto_shopping:
                if (!AccountUtils.hasLogin()) {
                    UIHelper.showUserLoginActivity(getActivity());
                } else if (!AccountUtils.hasBoundPhone()) {
                    UIHelper.showBundPhoneActivity(getActivity());
                }else {
                    UIHelper.showShopCar(getActivity());
                }
                break;
            case R.id.setting_shopping_money:
                UIHelper.showSettingShopMoneyActivity(getActivity());
                break;
        }
    }
}

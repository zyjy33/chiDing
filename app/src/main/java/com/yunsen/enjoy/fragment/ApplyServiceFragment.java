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
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.DeviceUtil;

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
    @Bind(R.id.next_btn)
    Button nextBtn;
    @Bind(R.id.apply_top_img)
    ImageView applyTopImg;
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
        applyTopImg.getLayoutParams().height= (int) (DeviceUtil.getScreenWidth()*0.45);
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
        HttpProxy.getIsFacilitator(AccountUtils.getUser_id(), new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                actionBarTitle.setText("商家");
                nextBtn.setText("进入商家商城");
                mIsService = true;
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    actionBarTitle.setText("申请商家");
                    nextBtn.setText("立即入驻");
                    mIsService = false;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.next_btn)
    public void onViewClicked() {
        if (mIsService) {
            UIHelper.showPartsShopActivity(getActivity());
        } else {
            UIHelper.showApplyServiceSecondActivity(getActivity());
        }

    }
}

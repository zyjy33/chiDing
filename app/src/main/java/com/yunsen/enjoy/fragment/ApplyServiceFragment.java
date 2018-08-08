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
import com.yunsen.enjoy.ui.UIHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply_service;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        actionBack.setVisibility(View.INVISIBLE);
        actionBarTitle.setText("申请商家");
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.next_btn)
    public void onViewClicked() {
        UIHelper.showApplyServiceSecondActivity(getActivity());

    }
}

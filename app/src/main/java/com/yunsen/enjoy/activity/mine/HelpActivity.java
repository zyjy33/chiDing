package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.WebActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.ui.UIHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/2/002.
 */

public class HelpActivity extends BaseFragmentActivity {
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
    @Bind(R.id.service_online_layout)
    LinearLayout serviceOnlineLayout;
    @Bind(R.id.one_layout)
    LinearLayout oneLayout;
    @Bind(R.id.two_layout)
    LinearLayout twoLayout;
    @Bind(R.id.three_layout)
    LinearLayout threeLayout;
    @Bind(R.id.four_layout)
    LinearLayout fourLayout;
    @Bind(R.id.five_layout)
    LinearLayout fiveLayout;

    @Override
    public int getLayout() {
        return R.layout.activity_help;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("吃定帮助中心");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.service_online_layout, R.id.one_layout, R.id.two_layout, R.id.three_layout, R.id.four_layout, R.id.five_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.service_online_layout:
                requestPermission(Permission.CALL_PHONE, Constants.CALL_PHONE);
                break;
            case R.id.one_layout:
                UIHelper.showWebActivity(this, "http://www.baidu.com", "消费卷使用说明");
            case R.id.two_layout:
                UIHelper.showWebActivity(this, "http://www.baidu.com", "退款说明");
                break;
            case R.id.three_layout:
                UIHelper.showWebActivity(this, "http://www.baidu.com", "退款步骤说明");
                break;
            case R.id.four_layout:
                UIHelper.showWebActivity(this, "http://www.baidu.com", "恶意刷单处罚");
                break;
            case R.id.five_layout:
                UIHelper.showWebActivity(this, "http://www.baidu.com", "什么事消费卷,如何做到折扣!");
                break;
        }
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        if (requestCode == Constants.CALL_PHONE) {
            UIHelper.showPhoneNumberActivity(this, "123456");
        }
    }
}

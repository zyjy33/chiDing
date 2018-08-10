package com.yunsen.enjoy.activity.pay;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/9/009.
 */

public class PayActivity extends BaseFragmentActivity {
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
    @Bind(R.id.pay_type_img)
    ImageView payTypeImg;
    @Bind(R.id.pay_type_layout)
    LinearLayout payTypeLayout;
    @Bind(R.id.pay_type_img_2)
    ImageView payTypeImg2;
    @Bind(R.id.pay_type_layout_2)
    LinearLayout payTypeLayout2;
    @Bind(R.id.pay_type_img_3)
    ImageView payTypeImg3;
    @Bind(R.id.pay_type_layout_3)
    LinearLayout payTypeLayout3;
    @Bind(R.id.pay_type_img_4)
    ImageView payTypeImg4;
    @Bind(R.id.pay_type_layout_4)
    LinearLayout payTypeLayout4;
    @Bind(R.id.pay_type_layout_root)
    LinearLayout payTypeLayoutRoot;
    private int mPayType=Constants.WEI_XIN_PAY_TYPE;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_pay;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("付款给商家");
        payTypeImg.setSelected(true);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.action_back, R.id.pay_type_layout, R.id.pay_type_layout_2, R.id.pay_type_layout_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.pay_type_layout:
                payTypeImg.setSelected(true);
                payTypeImg2.setSelected(false);
                payTypeImg3.setSelected(false);
                payTypeImg4.setSelected(false);
                mPayType = Constants.WEI_XIN_PAY_TYPE;
                break;
            case R.id.pay_type_layout_2:
                payTypeImg.setSelected(false);
                payTypeImg2.setSelected(true);
                payTypeImg3.setSelected(false);
                payTypeImg4.setSelected(false);
                mPayType = Constants.ALIPAY_TYPE;
                break;
            case R.id.pay_type_layout_3:
                payTypeImg.setSelected(false);
                payTypeImg2.setSelected(false);
                payTypeImg3.setSelected(true);
                payTypeImg4.setSelected(false);
                mPayType = Constants.BALANCE_PAY_TYPE;
                break;
        }
    }
}

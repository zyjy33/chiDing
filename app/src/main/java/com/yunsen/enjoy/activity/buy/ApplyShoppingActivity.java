package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/27.
 * 商家加盟
 */

public class ApplyShoppingActivity extends BaseFragmentActivity {

    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.action_back_layout)
    LinearLayout actionBackLayout;
    @Bind(R.id.team_top_layout)
    LinearLayout teamTopLayout;
    @Bind(R.id.name_edt)
    EditText nameEdt;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.shopping_name)
    EditText shoppingName;
    @Bind(R.id.shopping_type)
    EditText shoppingType;
    @Bind(R.id.shopping_address)
    EditText shoppingAddress;
    @Bind(R.id.shopping_introduce)
    EditText shoppingIntroduce;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    @Bind(R.id.online_btn)
    Button onlineBtn;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_apply_shopping;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("商家加盟");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.action_back, R.id.submit_btn, R.id.online_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.submit_btn:
                break;
            case R.id.online_btn:
                break;
        }
    }
}

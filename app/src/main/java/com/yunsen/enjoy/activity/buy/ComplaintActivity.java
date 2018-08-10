package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.model.DatatypeBean;
import com.yunsen.enjoy.widget.FlowLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/10/010.
 */

public class ComplaintActivity extends BaseFragmentActivity {
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
    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_complaint;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("投诉店铺");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ArrayList<DatatypeBean> datas = new ArrayList<>();
        datas.add(new DatatypeBean("吃币被拒绝消费"));
        datas.add(new DatatypeBean("店铺地址不正确"));
        datas.add(new DatatypeBean("服务态度差"));
        datas.add(new DatatypeBean("菜品不卫生"));
        datas.add(new DatatypeBean("卫生添加差"));
        datas.add(new DatatypeBean("其他"));
        flowLayout.setDatas(datas);//超值


    }

    @Override
    protected void initListener() {
    }


    @OnClick({R.id.action_back, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.submit_btn:
                break;
        }
    }
}

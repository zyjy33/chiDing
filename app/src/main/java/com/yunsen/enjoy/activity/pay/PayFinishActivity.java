package com.yunsen.enjoy.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/21/021.
 */

public class PayFinishActivity extends BaseFragmentActivity {
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.pay_success_money_tv)
    TextView paySuccessMoneyTv;

    @Override
    public int getLayout() {
        return R.layout.activity_pay_finish;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("支付结果");

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String payMoney = intent.getStringExtra(Constants.PAY_MONEY);
        paySuccessMoneyTv.setText(payMoney + "元");
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.back_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
            case R.id.back_btn:
                finish();
                break;
        }
    }
}

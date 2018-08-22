package com.yunsen.enjoy.activity.pay;

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
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.RechargeNoBean;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/8/22/022.
 */

public class SettingShopMoneyActivity extends BaseFragmentActivity {
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.action_back_layout)
    LinearLayout actionBackLayout;
    @Bind(R.id.team_top_layout)
    LinearLayout teamTopLayout;
    @Bind(R.id.pay_money_edt)
    EditText payMoneyEdt;
    @Bind(R.id.submit_btn)
    Button submitBtn;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_setting_shop_money;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

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
                String moneyStr = payMoneyEdt.getText().toString();
                double money = Double.parseDouble(moneyStr);
                if (money <= 0) {
                    ToastUtils.makeTextShort("预售金额不能小于等于0");
                } else {
                    HttpProxy.settingShopMoneyRequest(moneyStr, new HttpCallBack<RechargeNoBean>() {
                        @Override
                        public void onSuccess(RechargeNoBean responseData) {
                            UIHelper.showSettingShopMoneyTishiActivity(SettingShopMoneyActivity.this, responseData.getRecharge_no(),payMoneyEdt.getText().toString());
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            if (e instanceof DataException) {
                                ToastUtils.makeTextShort(e.getMessage());
                            }
                        }
                    });
                }
                break;
        }
    }
}

package com.yunsen.enjoy.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.ApplyAgentActivity;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.BecomeVipActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.PayMoneyProxy;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.MyOrderInfo;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.model.request.WatchCarModel;
import com.yunsen.enjoy.thirdparty.PayProxy;
import com.yunsen.enjoy.thirdparty.alipay.PayResult;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

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
    @Bind(R.id.submit_btn)
    Button submitBtn;
    @Bind(R.id.pay_money_edt)
    EditText payMoneyEdt;
    @Bind(R.id.my_card_tv)
    TextView myCardTv;
    private int mPayType = Constants.WEI_XIN_PAY_TYPE;
    private MyHandler mMyHandler;
    private WatchCarModel mRequestData;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_pay;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("付款给商家");
        payTypeImg3.setSelected(true);
        mPayType = Constants.BALANCE_PAY_TYPE;
        payTypeLayout.setVisibility(View.GONE);
        payTypeLayout2.setVisibility(View.GONE);
        Intent intent = getIntent();
        String companyId = intent.getStringExtra(Constants.COMPANY_ID);
        String companyName = intent.getStringExtra(Constants.COMPANY_NAME);

        mRequestData = new WatchCarModel();
        mRequestData.setGoods_id("9221");
        mRequestData.setArticle_id("16944");
        mRequestData.setPayment_id("9");
        mRequestData.setIs_invoice("0");
        mRequestData.setUser_id(AccountUtils.getUser_id());
        mRequestData.setUser_name(AccountUtils.getUserName());
        mRequestData.setCompany_id(companyId);
        mRequestData.setCompany_name(companyName);
        //没有用的参数
        mRequestData.setCity("无");
        mRequestData.setArea("无");
        mRequestData.setAddress("无");
        mRequestData.setProvince("无");
        mRequestData.setAccept_name("无");
        mRequestData.setMobile("000");

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String cardStr = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE).getString(SpConstants.CARD, "0");
        myCardTv.setText("我有消费券:" + cardStr);
        mMyHandler = new MyHandler(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == Constants.BALANCE_PAY_REQUEST) {
//            HttpProxy.getUserInfoNoSave(AccountUtils.getUserName(), new HttpCallBack<UserInfo>() {
//                @Override
//                public void onSuccess(UserInfo responseData) {
//                    myCardTv.setText("我有消费券:" + responseData.getCard());
//                }
//
//                @Override
//                public void onError(Request request, Exception e) {
//
//                }
//            });
            UIHelper.showPayFinishActivity(this, payMoneyEdt.getText().toString());
            finish();
        }
    }

    @OnClick({R.id.action_back, R.id.pay_type_layout, R.id.pay_type_layout_2, R.id.pay_type_layout_3,
            R.id.submit_btn, R.id.recharge_tv})
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
            case R.id.submit_btn:
                String payMoneyStr = payMoneyEdt.getText().toString();
                double payMoney = 0.0;
                if (!TextUtils.isEmpty(payMoneyStr)) {
                    payMoney = Double.parseDouble(payMoneyStr);
                }
                if (payMoney <= 0) {
                    ToastUtils.makeTextShort("消费金额不能小于等于0");
                    return;
                }
                switch (mPayType) {
                    case Constants.ALIPAY_TYPE:

                        HttpProxy.gotoShopPay(mRequestData, new HttpCallBack<MyOrderInfo>() {
                            @Override
                            public void onSuccess(MyOrderInfo responseData) {
                                PayMoneyProxy.getInstance().aliayPay(PayActivity.this, AccountUtils.getUser_id(), AccountUtils.getUserName(),
                                        payMoneyEdt.getText().toString(), responseData.getTrade_no(), mMyHandler);
                            }

                            @Override
                            public void onError(Request request, Exception e) {

                            }
                        });
                        break;
                    case Constants.WEI_XIN_PAY_TYPE:
                        break;
                    case Constants.BALANCE_PAY_TYPE:
                        mRequestData.setExpress_id("7");
                        mRequestData.setAmount(payMoneyStr);
                        HttpProxy.gotoShopPay(mRequestData, new HttpCallBack<MyOrderInfo>() {
                            @Override
                            public void onSuccess(MyOrderInfo responseData) {
                                PayMoneyProxy.getInstance().cardPay(PayActivity.this, responseData.getTrade_no());
                            }

                            @Override
                            public void onError(Request request, Exception e) {

                            }
                        });
                        break;
                }
                break;
            case R.id.recharge_tv:
                UIHelper.showMonneyChongZhiActivity(this, "16");
                break;
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<PayActivity> weakReference;

        public MyHandler(PayActivity act) {
            this.weakReference = new WeakReference<PayActivity>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PayActivity act = weakReference.get();
            if (!act.isFinishing()) {
                switch (msg.what) {
                    case PayProxy.SDK_PAY_FLAG: //支付完成

                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            Toast.makeText(act, "支付成功", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                            act.finish();
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(act, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case PayProxy.PAY_FAIL:

                        break;
                }
            }
        }
    }
}

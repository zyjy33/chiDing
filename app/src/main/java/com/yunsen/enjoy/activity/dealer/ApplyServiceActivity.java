package com.yunsen.enjoy.activity.dealer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.request.ApplyFacilitatorModel;
import com.yunsen.enjoy.model.request.ApplyProxyServiceRequest;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * 申请商家（我是商家）
 */
public class ApplyServiceActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.name_edt)
    EditText nameEdt;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.address_edt)
    EditText addressEdt;
    @Bind(R.id.address_point)
    EditText addressPoint;
    private ApplyFacilitatorModel mRequestData;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_apply_service;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("代理商加盟");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mRequestData = new ApplyFacilitatorModel(AccountUtils.getUser_id(), AccountUtils.getUserName());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    private void submit() {
        String name = nameEdt.getText().toString();
        String address = addressEdt.getText().toString();
        String point = addressPoint.getText().toString();
        String phone = phoneEdt.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.makeTextShort("请输入姓名");
        } else if (TextUtils.isEmpty(phone)) {
            ToastUtils.makeTextShort("请输入电话");
        } else if (TextUtils.isEmpty(address)) {
            ToastUtils.makeTextShort("请输入地址");
        } else if (TextUtils.isEmpty(point)) {
            ToastUtils.makeTextShort("请填写地区优势");
        } else {
            mRequestData.setAddress(address);
            mRequestData.setAdvantage(point);
            mRequestData.setMobile(phone);
            mRequestData.setContact(name);
            mRequestData.setTrade_id("0");
            mRequestData.setSort_id("0");
            HttpProxy.getApplyProxyService(this, mRequestData, new HttpCallBack<RestApiResponse>() {
                @Override
                public void onSuccess(RestApiResponse responseData) {
                    ToastUtils.makeTextShort("提交成功，请保持联系电话畅通，我们会尽快联系您!");
                    finish();
                }

                @Override
                public void onError(Request request, Exception e) {
                    if (e instanceof DataException) {
                        ToastUtils.makeTextShort(e.getMessage());
                    }
                }
            });
        }
    }


    @OnClick({R.id.submit_btn, R.id.online_btn, R.id.action_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                submit();
                break;
            case R.id.online_btn:
                requestPermission(Permission.CALL_PHONE, Constants.CALL_PHONE);
                break;
            case R.id.action_back:
                finish();
                break;
        }
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        if (requestCode == Constants.CALL_PHONE) {
            UIHelper.showPhoneNumberActivity(this, "444****120");
        }
    }
}

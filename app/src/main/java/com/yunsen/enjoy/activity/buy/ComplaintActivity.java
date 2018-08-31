package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.DatatypeBean;
import com.yunsen.enjoy.model.request.ComplaintRequest;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.StringUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.FlowLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

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
    @Bind(R.id.shop_name_tv)
    TextView shopNameTv;
    @Bind(R.id.complaint_time_tv)
    TextView complaintTimeTv;
    @Bind(R.id.complaint_content_edt)
    EditText complaintContentEdt;
    @Bind(R.id.online_phone_edt)
    EditText onlinePhoneEdt;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    private ComplaintRequest mRequest;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_complaint;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("投诉店铺");
        Intent intent = getIntent();
        mRequest = new ComplaintRequest();

        mRequest.setCompany_id(intent.getStringExtra(SpConstants.COMPANY_ID));
        String shopName = intent.getStringExtra(SpConstants.COMPANY_NAME);
        mRequest.setCompany_name(shopName);
        mRequest.setUser_id(AccountUtils.getUser_id());
        mRequest.setUser_name(AccountUtils.getUserName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        complaintTimeTv.setText(time);
        shopNameTv.setText(shopName);

        mRequest.setUser_sign(AccountUtils.getLoginSign());

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ArrayList<DatatypeBean> datas = new ArrayList<>();
        datas.add(new DatatypeBean("消费券被拒绝消费"));
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


    @OnClick({R.id.action_back, R.id.submit_btn, R.id.complaint_time_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.complaint_time_layout:
                break;
            case R.id.submit_btn:
                String otherReason = complaintContentEdt.getText().toString();
                String phone = onlinePhoneEdt.getText().toString();
                String reason = flowLayout.getSelectData();
                if (TextUtils.isEmpty(reason)) {
                    ToastUtils.makeTextShort("请选择至少一项原因");
                } else if (TextUtils.isEmpty(phone)) {
                    ToastUtils.makeTextShort("请填写联系电话");
                } else {
                    mRequest.setMobile(phone);
                    mRequest.setOther_reason(otherReason);
                    mRequest.setReason(reason);
                    submitData();
                }
                break;
        }
    }

    private void submitData() {
        HttpProxy.submitAddUserComplaint(mRequest, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                ToastUtils.makeTextShort("投诉成功,我们会尽快给您处理!");
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

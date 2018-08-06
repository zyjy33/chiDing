package com.yunsen.enjoy.activity.mine;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/2/002.
 */

public class TransferOtherActivity extends BaseFragmentActivity {
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.transfer_balance_tv)
    TextView transferBalanceTv;
    @Bind(R.id.transfer_account_edt)
    EditText transferAccountEdt;
    @Bind(R.id.transfer_coin_edt)
    EditText transferCoinEdt;
    private AlertDialog mEnterDialog;

    @Override
    public int getLayout() {
        return R.layout.activity_transfer_other;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("转赠他人");

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
                String acount = transferAccountEdt.getText().toString();
                String coin = transferCoinEdt.getText().toString();
                if (TextUtils.isEmpty(acount)) {
                    ToastUtils.makeTextShort("请输入转赠账户");
                } else if (TextUtils.isEmpty(coin)) {
                    ToastUtils.makeTextShort("请输入转赠消费卷金额");
                } else {
                    showEnterDialog(acount, coin);
                }
                break;
        }
    }


    private void showEnterDialog(String acount, String coin) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.transfer_remind, null);
        TextView accountTv = (TextView) view.findViewById(R.id.transfer_account_tv);
        TextView coinTv = (TextView) view.findViewById(R.id.transfer_coin_tv);
        accountTv.setText(acount);
        coinTv.setText(coin);
        Button okBtn = (Button) view.findViewById(R.id.ok_btn);
        Button cancelBtn = (Button) view.findViewById(R.id.cancel_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.makeTextShort("提交");
                if (mEnterDialog != null) {
                    mEnterDialog.dismiss();
                }

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEnterDialog != null) {
                    mEnterDialog.dismiss();
                }
            }
        });

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
        if (mEnterDialog != null && mEnterDialog.isShowing()) {
            mEnterDialog.dismiss();
        }
        mEnterDialog = new AlertDialog.Builder(this).create();
        mEnterDialog.setCanceledOnTouchOutside(false);
        mEnterDialog.show();
        Window window = mEnterDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setContentView(view);
    }
}

package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.WebActivity;
import com.yunsen.enjoy.activity.mine.adapter.HelpListAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.notice.adapter.NoticeListAdapter;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.NoticeModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.NoScrollLinearLayoutManager;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/8/2/002.
 */

public class HelpActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener {
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
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<NoticeModel> mDatas;
    private HelpListAdapter mAdapter;
    private String mPhoneNumber = Constants.EMPTY;

    @Override
    public int getLayout() {
        return R.layout.activity_help;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("大道网帮助中心");
        recyclerView.setLayoutManager(new NoScrollLinearLayoutManager(this));
        mDatas = new ArrayList<>();
        mAdapter = new HelpListAdapter(this, R.layout.help_item, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void requestData() {
        super.requestData();
        HttpProxy.getHelpList(new HttpCallBack<List<NoticeModel>>() {
            @Override
            public void onSuccess(List<NoticeModel> responseData) {
                mAdapter.upBaseDatas(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
        HttpProxy.getTelUrl(new HttpCallBack<String>() {
            @Override
            public void onSuccess(String responseData) {
                mPhoneNumber = responseData;
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    ToastUtils.makeTextShort(e.getMessage());
                }
            }
        });
    }

    @OnClick({R.id.action_back, R.id.service_online_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.service_online_layout:
                requestPermission(Permission.CALL_PHONE, Constants.CALL_PHONE);
                break;
        }
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        if (requestCode == Constants.CALL_PHONE) {
            UIHelper.showPhoneNumberActivity(this, mPhoneNumber);
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (position >= 0 && position < mDatas.size()) {
            UIHelper.showHelpWebActivity(this, mDatas.get(position).getId());
        }

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}

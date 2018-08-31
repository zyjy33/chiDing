package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.ComplaintAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.ComplaintBean;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.NoticeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/8/2/002.
 */

public class MyTranslateActivity extends BaseFragmentActivity implements ComplaintAdapter.ResetComplaintListanner {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.notice_view)
    NoticeView noticeView;
    //    @Bind(R.id.refreshLayout)
//    TwinklingRefreshLayout refreshLayout;
    private ArrayList<ComplaintBean> mDatas;
    private ComplaintAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_my_transfer;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("我的投诉");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatas = new ArrayList<>();
        mAdapter = new ComplaintAdapter(this, R.layout.translate_item_layout, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setListanner(this);
    }

    @Override
    public void requestData() {
        HttpProxy.getUserComplaintList(new HttpCallBack<List<ComplaintBean>>() {
            @Override
            public void onSuccess(List<ComplaintBean> responseData) {
                if (responseData != null && responseData.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    mAdapter.upBaseDatas(responseData);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    noticeView.showNoticeType(NoticeView.Type.NO_DATA);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                recyclerView.setVisibility(View.GONE);
                noticeView.showNoticeType(NoticeView.Type.NO_DATA);
            }
        });
    }

    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }


    @Override
    public void onResetComplaint(String id) {
        HttpProxy.getEditUserComplaint(id, new HttpCallBack<List<ComplaintBean>>() {
            @Override
            public void onSuccess(List<ComplaintBean> responseData) {
                ToastUtils.makeTextShort("撤销成功，感谢您的参与！");
                requestData();
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }
}

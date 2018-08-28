package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.widget.NoticeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/2/002.
 */

public class MyTranslateActivity extends BaseFragmentActivity {
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
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

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
        refreshLayout.setVisibility(View.GONE);
        noticeView.showNoticeType(NoticeView.Type.NO_DATA);
    }

    @Override
    protected void initListener() {

    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }


}

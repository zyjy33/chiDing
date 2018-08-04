package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.GoodsListAdapter;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.NoScrollLinearLayoutManager;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.BaseScrollView;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/3/003.
 */

public class FoodDescriptionActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener, BaseScrollView.OnScrollListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.scroll_view)
    BaseScrollView scrollView;
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_share)
    ImageView actionBarShare;
    @Bind(R.id.action_back_complaint)
    TextView actionBackComplaint;
    @Bind(R.id.action_bar_layout)
    LinearLayout actionBarLayout;
    private GoodsListAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_food_description;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarLayout.getBackground().setAlpha(0);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        NoScrollLinearLayoutManager layout = new NoScrollLinearLayoutManager(this);
        layout.setScrollEnabled(false);
        recyclerView.setLayoutManager(layout);
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            datas.add("" + i);
        }
        mAdapter = new GoodsListAdapter(this, R.layout.goods_list_item, datas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
        scrollView.setOnScrollListener(this);
    }


    @OnClick({R.id.action_back, R.id.action_bar_share, R.id.action_back_complaint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.action_bar_share:
                UIHelper.showShareGoodsActivity(this, "测试标题", "测试描述", "测试路径", "测试图片路径");
                break;
            case R.id.action_back_complaint:
                ToastUtils.makeTextShort("投诉");
                break;
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    private static final String TAG = "FoodDescriptionActivity";

    @Override
    public void onScrollChanged(int scrollX, int scrollY) {
        Log.e(TAG, "onScrollChanged: scrollY=" + scrollY);
        if (scrollY > 255) {
            scrollY = 255;
        }
        if (scrollY < 0) {
            scrollY = 0;
        }

        actionBarLayout.getBackground().setAlpha(scrollY);
        int btnAlpha = 255 - scrollY;
        if (btnAlpha < 0) {
            btnAlpha = 0;
        }
        if (btnAlpha > 180) {
            btnAlpha = 180;
        }
        actionBack.getBackground().setAlpha(btnAlpha);
        actionBarShare.getBackground().setAlpha(btnAlpha);
        actionBackComplaint.getBackground().setAlpha(btnAlpha);
    }
}

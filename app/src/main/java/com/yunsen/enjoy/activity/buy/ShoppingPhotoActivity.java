package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.PartsShopFragmentPager;
import com.yunsen.enjoy.fragment.PhotoFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/4/004.
 */

public class ShoppingPhotoActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private PartsShopFragmentPager mFragmentAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_shopping_photo;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("大道网商家相册");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> listTitle = new ArrayList<>();
        fragments.add(new PhotoFragment());
        fragments.add(new PhotoFragment());
        listTitle.add("全部");
        listTitle.add("环境");
        mFragmentAdapter = new PartsShopFragmentPager(getSupportFragmentManager(), fragments, listTitle);
        viewPager.setAdapter(mFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initListener() {

    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }
}

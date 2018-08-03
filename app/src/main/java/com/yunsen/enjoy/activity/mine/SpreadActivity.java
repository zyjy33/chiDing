package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.CollectFragmentAdapter;
import com.yunsen.enjoy.activity.mine.adapter.SpreadFragmentAdapter;
import com.yunsen.enjoy.activity.mine.fragment.SpreadFragment;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.ui.UIHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/2/002.
 */

public class SpreadActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    TextView actionBarRight;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private SpreadFragment oneFragment;
    private SpreadFragment twoFragment;

    @Override
    public int getLayout() {
        return R.layout.activity_spread;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        oneFragment = new SpreadFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.FRAGMENT_TYPE_KEY, Constants.VIP_TAG);
        oneFragment.setArguments(bundle);

        twoFragment = new SpreadFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt(Constants.FRAGMENT_TYPE_KEY, Constants.SHOPPING_TAG);
        twoFragment.setArguments(bundle2);
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(oneFragment);
        fragments.add(twoFragment);
        viewPager.setAdapter(new SpreadFragmentAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.action_bar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.action_bar_right:
                UIHelper.showSpreadActivity2(this);
                break;
        }
    }
}

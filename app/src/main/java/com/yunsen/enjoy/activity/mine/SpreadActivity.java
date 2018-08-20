package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
    ImageView actionBarRight;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private SpreadFragment oneFragment;
    private SpreadFragment twoFragment;
    private PopupWindow popupWindow;

    @Override
    public int getLayout() {
        return R.layout.activity_spread;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarRight.setVisibility(View.VISIBLE);
        actionBarRight.setImageResource(R.drawable.share_app_seletor);
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
//                UIHelper.showSpreadActivity2(this);
                showSharePopupWindow();
                break;
        }
    }

    private void showSharePopupWindow() {
        // 加载popupWindow的布局文件
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = layoutInflater.inflate(R.layout.share_app_layout, null, false);
        View qrCodeView = contentView.findViewById(R.id.qr_code_layout);
        View shareLayout = contentView.findViewById(R.id.share_layout);
        popupWindow = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.share_layout_bg));
// 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        qrCodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.makeTextShort("面对面");
                UIHelper.showExtensionActivity(SpreadActivity.this);
                popupWindow.dismiss();
            }
        });
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShareAppInfoActivity(SpreadActivity.this, "");
                popupWindow.dismiss();
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(actionBarRight, -10, 0);
    }
}

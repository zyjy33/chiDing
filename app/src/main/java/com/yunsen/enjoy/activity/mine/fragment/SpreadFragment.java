package com.yunsen.enjoy.activity.mine.fragment;

import android.os.Bundle;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.BaseFragment;

/**
 * Created by Administrator on 2018/8/2/002.
 */

public class SpreadFragment extends BaseFragment {

    private int fragmentType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_spread;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        fragmentType = bundle.getInt(Constants.FRAGMENT_TYPE_KEY);
        if (fragmentType == Constants.VIP_TAG) {

        } else if (fragmentType == Constants.SHOPPING_TAG) {

        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {

    }
}

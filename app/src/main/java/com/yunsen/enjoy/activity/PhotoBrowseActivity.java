package com.yunsen.enjoy.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.adapter.PhotoBrowseAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.model.PhotoInfo;
import com.yunsen.enjoy.ui.loopviewpager.LoopViewPager;
import com.yunsen.enjoy.ui.photoview.PhotoViewAdapter;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/8/008.
 */

public class PhotoBrowseActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.pager)
    LoopViewPager pager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    @Bind(R.id.layout_ent_gallery)
    RelativeLayout layoutEntGallery;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.current_num_tv)
    TextView currentNumTv;
    private ArrayList<PhotoInfo> mDatas;
    private int mSize = 0;
    private int mCurrentIndex;

    @Override
    public int getLayout() {
        return R.layout.activity_photo_browse;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        mDatas = bundle.getParcelableArrayList(Constants.PHOTO_BROWSE_KEY);
        mCurrentIndex = bundle.getInt(Constants.PHOTO_BROWSE_INDEX_KEY);
        if (mDatas != null) {
            mSize = mDatas.size();
        }
        pager.setAdapter(new PhotoBrowseAdapter(this, mDatas));
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);
        if (mCurrentIndex < mSize) {
            pager.setCurrentItem(mCurrentIndex);
            titleTv.setText(mDatas.get(mCurrentIndex).getTitle());
            currentNumTv.setText((mCurrentIndex + 1) + "/" + mSize);
        }
    }

    @Override
    protected void initListener() {
        indicator.setOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int text = position + 1;
        currentNumTv.setText(text + "/" + mSize);
        titleTv.setText(mDatas.get(position).getTitle());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

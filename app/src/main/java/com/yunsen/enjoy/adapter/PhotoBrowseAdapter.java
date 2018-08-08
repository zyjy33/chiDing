package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.HouseDetailActivity;
import com.yunsen.enjoy.model.PhotoInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/8/8/008.
 */

public class PhotoBrowseAdapter extends PagerAdapter {
    private Context mContext;
    private List<PhotoInfo> mDatas;

    public PhotoBrowseAdapter(Context context, List<PhotoInfo> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(mContext);
        Glide.with(mContext)
                .load(mDatas.get(position).getUrl())
                .error(R.mipmap.ic_launcher)
                .into(item);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.ui.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunsenA on 2018/4/18.
 */

public class DiscoverBannerAdapter extends PagerAdapter {
    private static final String TAG = "BannerAdapter";
    private List<AdvertModel> mDatas;
    private Context mContext;

    public DiscoverBannerAdapter(List<AdvertModel> datas, Context context) {
        if (datas == null) {
            this.mDatas = new ArrayList<>();
        } else {
            this.mDatas = datas;
        }
        this.mContext = context;
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
        final AdvertModel data = mDatas.get(position);
        if (data.getRseImg() != 0) {
            item.setImageResource(data.getRseImg());
        } else {
            Glide.with(mContext)
                    .load(data.getAd_url())
                    .placeholder(R.mipmap.adv_home)
                    .into(item);
            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int articleId = data.getId();
                    if (articleId != 0) {
                        UIHelper.showCarDetailsActivity(mContext, articleId);
                    }
                }
            });
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    public void upData(List<AdvertModel> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }
}

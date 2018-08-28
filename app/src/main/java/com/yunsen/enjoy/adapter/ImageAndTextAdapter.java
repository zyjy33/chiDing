package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.TradeData;
import com.yunsen.enjoy.model.UsedFunction;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class ImageAndTextAdapter extends CommonAdapter<TradeData> {
    public ImageAndTextAdapter(Context context, int layoutId, List<TradeData> datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(ViewHolder holder, TradeData usedFunction, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.top_img);
        holder.setText(R.id.bottom_tv, usedFunction.getTitle());
        Glide.with(mContext)
                .load(usedFunction.getIcon_url())
                .error(R.mipmap.app_icon)
                .into(imageView);
    }
}

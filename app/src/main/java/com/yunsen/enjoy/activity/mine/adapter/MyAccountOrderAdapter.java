package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.widget.GlideCircleTransform;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/8/30/030.
 */

public class MyAccountOrderAdapter extends CommonAdapter<WalletCashBean> {

    public MyAccountOrderAdapter(Context context, int layoutId, List<WalletCashBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, WalletCashBean data, int position) {
        ImageView view = (ImageView) holder.getView(R.id.left_img);
        Glide.with(mContext)
                .load(R.mipmap.app_icon)
                .transform(new GlideCircleTransform(mContext))
                .into(view);
        holder.setText(R.id.title_tv, data.getRemark());
        holder.setText(R.id.time_tv, data.getUpdate_time());
        holder.setText(R.id.money_tv, "+" + String.valueOf(data.getTo_income()));


    }
}

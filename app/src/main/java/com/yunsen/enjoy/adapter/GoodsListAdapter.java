package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DefaultSpecItemBean;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.utils.GlobalStatic;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/8/3/003.
 */

public class GoodsListAdapter extends CommonAdapter<SProviderModel> {
    public GoodsListAdapter(Context context, int layoutId, List<SProviderModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, SProviderModel data, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.goods_list_img);
        holder.setText(R.id.goods_list_title_tv, data.getName());
        String address = data.getProvince() + data.getCity() + data.getArea() + data.getAddress();
        holder.setText(R.id.goods_list_address_tv, address);
//        String text = "可用消费券" + 88 + "元";
        String text = mContext.getResources().getString(R.string.available_volume, "88.88");
        TextView textView = (TextView) holder.getView(R.id.goods_list_coin_tv);
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan redSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_theme));
        ForegroundColorSpan graySpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_888));
        //这里注意一定要先给textview赋值
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        int startEnd = text.indexOf("券");
        int End = text.indexOf("元");
        //为不同位置字符串设置不同颜色
        builder.setSpan(graySpan, 0, startEnd + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(redSpan, startEnd + 1, End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(graySpan, End, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //最后为textview赋值
        textView.setText(builder);

        holder.setText(R.id.goods_list_type_tv, "商品");
        Glide.with(mContext)
                .load(data.getImg_url())
                .placeholder(R.mipmap.default_img)
                .into(imageView);
        double lng = data.getLat();
        double lat = data.getLng();
//        Log.e(TAG, "convert: " + position + "  data.getLat()=" + lat + "  data.getLng() =" + lng);
        if (lat > 0 && lng >= 0 && GlobalStatic.latitude != 0.0 && GlobalStatic.longitude != 0.0) {
            double algorithm = Utils.algorithm(GlobalStatic.longitude, GlobalStatic.latitude, lng, lat) / 1000;

            BigDecimal b = new BigDecimal(algorithm);
            double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (df < 0.1) {
                holder.setText(R.id.goods_list_distance_tv, "小于0.1kmm");
            } else {
                holder.setText(R.id.goods_list_distance_tv, df + "km");
            }

        } else {
//            view.setVisibility(View.GONE);
            holder.setText(R.id.goods_list_distance_tv, "0.0 km");
        }

    }

    private static final String TAG = "GoodsListAdapter";
}

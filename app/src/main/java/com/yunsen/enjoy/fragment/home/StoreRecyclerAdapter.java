package com.yunsen.enjoy.fragment.home;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.model.ShopCollectionBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class StoreRecyclerAdapter extends CommonAdapter<ShopCollectionBean> {
    public StoreRecyclerAdapter(Context context, int layoutId, List<ShopCollectionBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ShopCollectionBean carStoreMode, int position) {
        holder.setText(R.id.shop_item_title, carStoreMode.getName());
        holder.setText(R.id.shop_item_sub_t1, carStoreMode.getName());
        holder.setText(R.id.shop_item_address, carStoreMode.getAddress());
        if (carStoreMode.getImg_url() != null) {
            Glide.with(mContext)
                    .load(carStoreMode.getImg_url())
                    .placeholder(R.mipmap.default_img)
                    .centerCrop()
                    .into((ImageView) holder.getView(R.id.shop_item_img));
        } else {
            holder.setImageResource(R.id.shop_item_img, R.mipmap.default_img);
        }
    }


    public void upDatas(List<ShopCollectionBean> datas) {
        this.mDatas.clear();
        if (datas != null) {
            this.mDatas.addAll(datas);
            this.notifyDataSetChanged();
        }

    }

    public boolean addDatas(List<ShopCollectionBean> datas) {
        if (datas != null) {
            this.mDatas.addAll(datas);
            this.notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    public void clearData() {
        this.mDatas.clear();
        this.notifyDataSetChanged();
    }
}

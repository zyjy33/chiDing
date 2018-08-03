package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.SelectStringModel;
import com.yunsen.enjoy.widget.city.SelectCityHelp;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3/003.
 */

public class SelectStringAdapter extends CommonAdapter<SelectStringModel> {

    public SelectStringAdapter(Context context, int layoutId, List<SelectStringModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, SelectStringModel data, int position) {
        TextView titleTv = (TextView) holder.getView(R.id.title_tv);
        ImageView selectImg = (ImageView) holder.getView(R.id.select_img);
        titleTv.setText(data.getName());
        if (data.isSelected()) {
            selectImg.setVisibility(View.VISIBLE);
        } else {
            selectImg.setVisibility(View.INVISIBLE);
        }
        titleTv.setSelected(data.isSelected());
    }

    public void selectItem(int position) {
        if (position >= 0 && position < mDatas.size()) {
            int size = mDatas.size();
            for (int i = 0; i < size; i++) {
                if (position == i) {
                    mDatas.get(i).setSelected(true);
                } else {
                    mDatas.get(i).setSelected(false);
                }
            }
            notifyDataSetChanged();
        }

    }
}

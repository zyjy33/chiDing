package com.yunsen.enjoy.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3/003.
 */

public class TypeListAdapter extends CommonAdapter<String> {
    public TypeListAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.type_item_tv, s);

    }
}

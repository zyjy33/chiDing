package com.yunsen.enjoy.adapter;

import android.content.Context;

import com.yunsen.enjoy.model.UsedFunction;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/8/4/004.
 */

public class PhotoImgAdapter extends CommonAdapter<UsedFunction> {

    public PhotoImgAdapter(Context context, int layoutId, List<UsedFunction> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UsedFunction usedFunction, int position) {

    }
}

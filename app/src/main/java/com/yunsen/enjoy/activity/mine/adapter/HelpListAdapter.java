package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.NoticeModel;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/9/4/004.
 */

public class HelpListAdapter extends CommonAdapter<NoticeModel> {
    public HelpListAdapter(Context context, int layoutId, List<NoticeModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NoticeModel noticeModel, int position) {
        holder.setText(R.id.help_item_title, noticeModel.getTitle());
    }
}

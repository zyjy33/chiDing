package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.view.View;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CheckedData;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class CheckItemAdapter extends CommonAdapter<CheckedData> {

    public CheckItemAdapter(Context context, int layoutId, List<CheckedData> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CheckedData checkedData, int position) {
        holder.setText(R.id.popup_select_tv, checkedData.getName());
        View textView = holder.getView(R.id.popup_select_tv);
        if (checkedData.isChecked()) {
            textView.setSelected(true);
        } else {
            textView.setSelected(false);
        }
    }

    public void setSelected(int position) {
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            CheckedData data = mDatas.get(i);
            data.setChecked(i == position);
        }
        this.notifyDataSetChanged();
    }

    public void reset() {
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            mDatas.get(i).setChecked(false);
        }
        this.notifyDataSetChanged();
    }

    public CheckedData getCurrentModel() {
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            if (mDatas.get(i).isChecked()) {
                return mDatas.get(i);
            }
        }
        return null;
    }
}

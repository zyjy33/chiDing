package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.TradeData;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3/003.
 */

public class TypeListAdapter extends CommonAdapter<TradeData> {
    public TypeListAdapter(Context context, int layoutId, List<TradeData> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, TradeData s, int position) {
        holder.setText(R.id.type_item_tv, s.getTitle());
        TextView view = (TextView) holder.getView(R.id.type_item_tv);
        view.setSelected(s.isSelected());
    }

    public void upSelected(int position) {
        if (mDatas != null && position >= 0 && position < mDatas.size()) {
            int size = mDatas.size();
            for (int i = 0; i < size; i++) {
                TradeData tradeData = mDatas.get(i);
                if (i == position) {
                    tradeData.setSelected(true);
                } else {
                    tradeData.setSelected(false);
                }
            }
            this.notifyDataSetChanged();
        }
    }
    public  void clearSelected(){
        if (mDatas != null) {
            int size = mDatas.size();
            for (int i = 0; i < size; i++) {
                TradeData tradeData = mDatas.get(i);
                    tradeData.setSelected(false);
            }
            this.notifyDataSetChanged();
        }
    }
}

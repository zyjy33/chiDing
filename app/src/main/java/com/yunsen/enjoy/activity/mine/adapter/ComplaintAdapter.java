package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.ComplaintBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/8/30/030.
 * 1 处理中
 * 0 已处理
 * 2 已撤销
 */

public class ComplaintAdapter extends CommonAdapter<ComplaintBean> {
    private ResetComplaintListanner mListanner;

    public ComplaintAdapter(Context context, int layoutId, List<ComplaintBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ComplaintBean transferBean, int position) {
        holder.setText(R.id.translate_title, "投诉:" + transferBean.getCompany_name());
        final int statusX = transferBean.getStatusX();
        TextView textView = (TextView) holder.getView(R.id.translate_state);
        TextView translateBtn = (TextView) holder.getView(R.id.translate_btn);
        TextView timeTv = (TextView) holder.getView(R.id.translate_rest_day);
        if (statusX == 0) {
            textView.setText("已处理");
            translateBtn.setVisibility(View.GONE);
            timeTv.setVisibility(View.GONE);
        } else if (statusX == 1) {
            textView.setText("等待处理中...");
            translateBtn.setVisibility(View.VISIBLE);
            timeTv.setVisibility(View.INVISIBLE);
        } else if (statusX == 2) {
            translateBtn.setVisibility(View.GONE);
            timeTv.setVisibility(View.GONE);
            textView.setText("已撤销");
        }
        final String fId = String.valueOf(transferBean.getId());
        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListanner != null) {
                    mListanner.onResetComplaint(fId);
                }
            }
        });
        holder.setText(R.id.translate_time, "提交时间：" + transferBean.getAdd_time());
        String otherReason = transferBean.getOther_reason();
        if (!TextUtils.isEmpty(otherReason)) {
            otherReason = transferBean.getReason() + "、" + otherReason;
        } else {
            otherReason = transferBean.getReason();
        }
        holder.setText(R.id.translate_reason, "原因：" + otherReason);

    }

    public void setListanner(ResetComplaintListanner listanner) {
        this.mListanner = listanner;
    }

    public interface ResetComplaintListanner {
        public void onResetComplaint(String id);
    }
}

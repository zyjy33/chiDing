package com.yunsen.enjoy.fragment.buy;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class FilterRecAdapter extends CommonAdapter<GoodsData> {
    public FilterRecAdapter(Context context, int layoutId, List<GoodsData> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsData goodsData, int position) {
        holder.setText(R.id.goods_title_2, goodsData.getTitle());
        holder.setText(R.id.goods_sub_title_2, goodsData.getSubtitle());
        holder.setText(R.id.goods_money, "¥" + goodsData.getSell_price());//sell_price
//        holder.setText(R.id.goods_first_money, "首付" + goodsData.getFirst_payment() + "万元");
        holder.setText(R.id.goods_address, goodsData.getAddress());
        Picasso.with(mContext)
                .load(goodsData.getImg_url())
                .resize(DeviceUtil.dp2px(mContext, 122), DeviceUtil.dp2px(mContext, 90))
                .centerCrop()
                .into(((ImageView) holder.getView(R.id.goods_left_img)));
    }

    /**
     * 如果返回的数据多余或等于0条 说明有更多数据
     *
     * @param responseData
     * @return
     */
    public boolean upData(List<GoodsData> responseData) {
        boolean flag = false;
        mDatas.clear();
        if (responseData != null && responseData.size() > 0) {
            mDatas.addAll(responseData);
            flag = true;
        }
        notifyDataSetChanged();
        return flag;
    }

    /**
     * @param datas
     * @return
     */
    public boolean addData(List<GoodsData> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            this.notifyDataSetChanged();
//            EventBus.getDefault().post(new UpUiEvent(EventConstants.UP_VIEW_PAGER_HEIGHT,true));
            return true;
        }
//        EventBus.getDefault().post(new UpUiEvent(EventConstants.UP_VIEW_PAGER_HEIGHT,false));
        return false;
    }


    public void removePosition(int position) {
        if (position >= 0 && position < mDatas.size()) {
            mDatas.remove(position);
            this.notifyItemRemoved(position);
        }
    }
}

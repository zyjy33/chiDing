package com.yunsen.enjoy.location;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.PoiAddressInfo;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/8/7/007.
 */

public class PoiAddressAdapter extends CommonAdapter<PoiAddressInfo> {
    public PoiAddressAdapter(Context context, int layoutId, List<PoiAddressInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, PoiAddressInfo poiAddressInfo, int position) {
        holder.setText(R.id.title_tv, poiAddressInfo.getTitle());
        holder.setText(R.id.address_tv, poiAddressInfo.getSnippet());
    }
}

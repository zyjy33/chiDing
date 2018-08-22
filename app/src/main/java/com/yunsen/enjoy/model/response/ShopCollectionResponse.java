package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.ShopCollectionBean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22/022.
 */

public class ShopCollectionResponse extends RestApiResponse {

    private List<ShopCollectionBean> data;

    public List<ShopCollectionBean> getData() {
        return data;
    }

    public void setData(List<ShopCollectionBean> data) {
        this.data = data;
    }

}

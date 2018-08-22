package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.MyOrderInfo;

/**
 * Created by Administrator on 2018/8/21/021.
 */

public class MyOrderResponse extends RestApiResponse {

    /**
     * data : {"total_amount":1,"trade_no":"T180821112642264255"}
     */

    private MyOrderInfo data;

    public MyOrderInfo getData() {
        return data;
    }

    public void setData(MyOrderInfo data) {
        this.data = data;
    }

}

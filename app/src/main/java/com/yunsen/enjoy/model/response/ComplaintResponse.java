package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.ComplaintBean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/30/030.
 */

public class ComplaintResponse extends RestApiResponse {

    private List<ComplaintBean> data;

    public List<ComplaintBean> getData() {
        return data;
    }

    public void setData(List<ComplaintBean> data) {
        this.data = data;
    }


}

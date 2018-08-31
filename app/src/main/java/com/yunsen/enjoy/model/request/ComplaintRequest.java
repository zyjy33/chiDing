package com.yunsen.enjoy.model.request;

/**
 * Created by Administrator on 2018/8/29/029.
 */

public class ComplaintRequest {
    private String company_id;//店铺id
    private String company_name;//店铺名称
    private String user_id;//消费者id
    private String user_name;//消费者姓名
    private String mobile;//消费者电话
    private String reason;//投诉原因
    private String other_reason = "无";//其他原因
    private String user_sign;

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setOther_reason(String other_reason) {
        this.other_reason = other_reason;
    }

    public void setUser_sign(String user_sign) {
        this.user_sign = user_sign;
    }
}

package com.yunsen.enjoy.model.request;

/**
 * Created by Administrator on 2018/8/24/024.
 */

public class ApplyProxyServiceRequest {
    private String user_id;//  用户名ID：user_id
    private String user_name;//  用户名：user_name
    private String contact;//   联系人：contact
    private String mobile;//   联系人电话：mobile
    private String address;//   地址：address
    private String advantage;//   企业优势：advantage
    private String trade_id;//   行业Id：trade_id

    public ApplyProxyServiceRequest(String user_id, String user_name) {
        this.user_id = user_id;
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }
}

package com.yunsen.enjoy.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2018/8/30/030.
 */

public  class ComplaintBean {
    /**
     * id : 2
     * company_id : 17104
     * company_name : 烤鸭店
     * user_id : 16090
     * user_name : 13249089599
     * mobile : 13249089599
     * reason : 其他
     * other_reason : 测试
     * feedback : null
     * status : 1
     * add_time : 2018-08-30 14:38:39
     * replay_time : 2018-08-30 14:38:39
     * update_time : 2018-08-30 14:38:39
     */

    private int id;
    private int company_id;
    private String company_name;
    private int user_id;
    private String user_name;
    private String mobile;
    private String reason;
    private String other_reason;
    private Object feedback;
    @JSONField(name = "status")
    private int statusX;
    private String add_time;
    private String replay_time;
    private String update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOther_reason() {
        return other_reason;
    }

    public void setOther_reason(String other_reason) {
        this.other_reason = other_reason;
    }

    public Object getFeedback() {
        return feedback;
    }

    public void setFeedback(Object feedback) {
        this.feedback = feedback;
    }

    public int getStatusX() {
        return statusX;
    }

    public void setStatusX(int statusX) {
        this.statusX = statusX;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getReplay_time() {
        return replay_time;
    }

    public void setReplay_time(String replay_time) {
        this.replay_time = replay_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}

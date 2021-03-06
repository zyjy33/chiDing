package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

/**
 * Created by Administrator on 2018/5/10.
 */

public class TradeData {
    private int imgResId;
    private int id;
    private int parent_id;
    private String title;
    private String img_url;
    private String icon_url;
    private boolean selected;

    public TradeData() {
    }

    public TradeData(int imgResId, String title) {
        this.imgResId = imgResId;
        this.title = title;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        if (img_url != null && img_url.startsWith("http")) {
            return img_url;
        } else {
            return URLConstants.REALM_URL + img_url;
        }
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getIcon_url() {
        if (icon_url != null && icon_url.startsWith("http")) {
            return icon_url;
        } else {
            return URLConstants.REALM_URL + icon_url;
        }
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
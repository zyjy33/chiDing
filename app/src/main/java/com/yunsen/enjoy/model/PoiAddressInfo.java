package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/8/7/007.
 */

public class PoiAddressInfo {
    //    title=源兴科技大厦,adName=,snippet=松坪山路1号
    private String title;
    private String adName;
    private String snippet;

    public PoiAddressInfo() {
    }

    public PoiAddressInfo(String title, String adName, String snippet) {
        this.title = title;
        this.adName = adName;
        this.snippet = snippet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}

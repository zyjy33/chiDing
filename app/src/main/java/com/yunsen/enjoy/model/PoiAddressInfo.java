package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/8/7/007.
 */

public class PoiAddressInfo {
    //    title=源兴科技大厦,adName=,snippet=松坪山路1号
    private String title;
    private String adName;
    private String snippet;
    double latitude;
    double longitude;

    public PoiAddressInfo() {
    }

    public PoiAddressInfo(String title, String adName, String snippet) {
        this.title = title;
        this.adName = adName;
        this.snippet = snippet;
    }

    public PoiAddressInfo(String title, String adName, String snippet, double latitude, double longitude) {
        this.title = title;
        this.adName = adName;
        this.snippet = snippet;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

package com.yunsen.enjoy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/8/8/008.
 */

public class PhotoInfo implements Parcelable {
    private String url;
    private String title;
    private int resId;

    public PhotoInfo(String url, String title) {
        this.url = url;
        this.title = title;
    }

    protected PhotoInfo(Parcel in) {
        url = in.readString();
        title = in.readString();
        resId = in.readInt();
    }

    public static final Creator<PhotoInfo> CREATOR = new Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel in) {
            return new PhotoInfo(in);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
        dest.writeInt(resId);
    }
}

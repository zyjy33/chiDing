package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/8/3/003.
 */

public class SelectStringModel {
    private String name;
    private boolean isSelected =false;

    public SelectStringModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

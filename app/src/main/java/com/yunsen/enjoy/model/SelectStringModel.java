package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/8/3/003.
 */

public class SelectStringModel {
    private String name;
    private boolean isSelected = false;
    private String value;

    public SelectStringModel(String name, String value) {
        this.name = name;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

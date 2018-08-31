package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/5/16.
 */

public class CheckedData {
    private String id;
    private String name;
    private String value;
    private String flag;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;

    public CheckedData() {
    }

    public CheckedData(String id, String name, boolean isChecked) {
        this.id = id;
        this.name = name;
        this.isChecked = isChecked;
    }

    public CheckedData(String id, String name, String value, boolean isChecked) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.isChecked = isChecked;
    }

    public CheckedData(String id, String name, String value, boolean isChecked, String flag) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.flag = flag;
        this.isChecked = isChecked;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.jayson.bms_back.utils;

/**
 * ztreenodes视图对象
 */
public class ZtreeNodeVo {
    //模块id
    private String id;
    //父模块id
    private String pId;
    private String name;
    //是否展开
    private boolean open = false;
    //是否选中
    private boolean checked =false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}

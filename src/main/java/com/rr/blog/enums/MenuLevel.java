package com.rr.blog.enums;

public enum  MenuLevel {
    TOP_MENU(1,"顶级菜单"),MAIN_MENU(2,"主题菜单");
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    MenuLevel(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    private  Integer value;
    private  String message;
}

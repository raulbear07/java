package com.rr.blog.enums;

public enum CategoryStatus {
    NORMAL(1, "正常"),
    HIDDEN(0, "隐藏");

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

    CategoryStatus(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    private  Integer value;
    private  String message;
}

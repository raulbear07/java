package com.rr.blog.enums;

public enum OptionStatus {
    ;

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

    OptionStatus(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    private  Integer value;
    private  String message;
}

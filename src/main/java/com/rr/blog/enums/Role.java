package com.rr.blog.enums;

public enum Role {
    ADMIN(1,"管理员"),VISITOR(0,"访客")
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

    Role(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    private  Integer value;
    private  String message;
}

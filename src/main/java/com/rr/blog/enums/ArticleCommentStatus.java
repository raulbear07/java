package com.rr.blog.enums;

public enum ArticleCommentStatus {
    ALLOW(1,"允许"),NOT_ALLOW(2,"不允许");

    ArticleCommentStatus(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    private Integer value;

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

    private String message;

}

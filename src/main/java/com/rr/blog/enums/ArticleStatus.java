package com.rr.blog.enums;

public enum ArticleStatus {
    PUBLISH(1,"已发布"),
    DRAFT(2,"草稿");

    ArticleStatus(Integer value, String message) {
        this.value = value;
        this.message = message;
    }
    private Integer value;
    private String message;

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}

package com.rr.blog.enums;

public enum LimitCount {
    COUNT_LOW(5),COUNT_MID(8),COUNT_HIGH(10);

    private  Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    LimitCount(Integer value) {
        this.value = value;
    }
}

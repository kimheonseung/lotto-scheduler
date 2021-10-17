package com.devh.common.constant;

public enum AttributeConstant {
    HREF("href"),
    SRC("src");

    private final String value;
    AttributeConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

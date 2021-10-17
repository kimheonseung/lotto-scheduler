package com.devh.common.constant;

public enum TagConstant {
    BR("<br>");

    private final String value;
    TagConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

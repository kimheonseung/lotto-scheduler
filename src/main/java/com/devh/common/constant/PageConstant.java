package com.devh.common.constant;

import lombok.Getter;

@Getter
public enum PageConstant {
    /* 한번에 보여질 페이지 갯수 (10인 경우 1~10, 21~30, ...) */
    DEFAULT_SIZE(10, 10.0);
    private final int intValue;
    private final double doubleValue;
    PageConstant(int intValue, double doubleValue) {
        this.intValue = intValue;
        this.doubleValue = doubleValue;
    }
}

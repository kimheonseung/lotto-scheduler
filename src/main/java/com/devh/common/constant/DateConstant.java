package com.devh.common.constant;

import lombok.Getter;

@Getter
public enum DateConstant {
    JANUARY("january", 1, "01"),
    FEBRUARY("february", 2, "02"),
    MARCH("march", 3, "03"),
    APRIL("april", 4, "04"),
    MAY("may", 5, "05"),
    JUNE("june", 6, "06"),
    JULY("july", 7, "07"),
    AUGUST("august", 8, "08"),
    SEPTEMBER("september", 9, "09"),
    OCTOBER("october", 10, "10"),
    NOVEMBER("november", 11, "11"),
    DECEMBER("december", 12, "12");

    private final String lowerCaseValue;
    private final int numberValue;
    private final String twoDigitNumberValue;

    DateConstant(String lowerCaseValue, int numberValue, String twoDigitNumberValue) {
        this.lowerCaseValue = lowerCaseValue;
        this.numberValue = numberValue;
        this.twoDigitNumberValue = twoDigitNumberValue;
    }
}

package com.devh.common.constant;

import lombok.Getter;

@Getter
public enum StringConstant {
    BLANK(""),
    COLON(":"),
    COMMA(","),
    DASH("-"),
    DOT("."),
    PARENTHESIS_CLOSE(")"),
    PARENTHESIS_OPEN("("),
    SLASH("/"),
    SPACE(" "),
    UNDERBAR("_");

    private final String value;
    StringConstant(String value) {
        this.value = value;
    }
}

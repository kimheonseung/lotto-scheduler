package com.devh.scheduler.lotto.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * Description :
 *     Lotto 관련 엘라스틱서치 상수값
 *     lotto_result_store
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-05-05
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum LottoIndex {
    INDEX_NAME_PRE("lotto_result_store"),
    TEMPLATE_ALIAS("lotto_result_store_template"),
    PATTERN("lotto_result_store*");
    private final String value;
}

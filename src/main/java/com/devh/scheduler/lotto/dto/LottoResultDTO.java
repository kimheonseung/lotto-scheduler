package com.devh.scheduler.lotto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * Description :
 *     LottoResult Entity에 대응되는 DTO
 * ===============================================
 * Member fields :
 *     private Integer turn
 *     private String date
 *     private String number1
 *     private String number2
 *     private String number3
 *     private String number4
 *     private String number5
 *     private String number6
 *     private String number7
 *     private Long totalSalesPrice;
 *     private Integer autoWinnerCount;
 *     private Integer semiAutoWinnerCount;
 *     private Integer manualWinnerCount;
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LottoResultDTO {
    private final String DATE_SPLITTER = "-";

    private Integer turn;
    private String date;
    private String number1;
    private String number2;
    private String number3;
    private String number4;
    private String number5;
    private String number6;
    private String number7;
    private Long totalSalesPrice;
    private Integer autoWinnerCount;
    private Integer semiAutoWinnerCount;
    private Integer manualWinnerCount;

    public String getYear() {
        return date.split(DATE_SPLITTER)[0];
    }

    public String getMonth() {
        return date.split(DATE_SPLITTER)[1];
    }

    public String getDay() {
        return date.split(DATE_SPLITTER)[2];
    }
}

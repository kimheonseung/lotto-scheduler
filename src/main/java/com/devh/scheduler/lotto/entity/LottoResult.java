package com.devh.scheduler.lotto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * <pre>
 * Description :
 *     로또 당첨 정보 테이블 관련 Entity
 * ===============================================
 * Member fields :
 *     private int turn
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
 * Date   : 2021-02-27
 * </pre>
 */
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LottoResult {
    @Id
    private Integer turn;                   /* 회차 정보 */
    @Column(length = 20)
    private String date;                    /* 날짜 정보 (yyyy-MM-dd) */
    @Column(length = 2)
    private String number1;                 /* 1번째 숫자 */
    @Column(length = 2)
    private String number2;                 /* 2번째 숫자 */
    @Column(length = 2)
    private String number3;                 /* 3번째 숫자 */
    @Column(length = 2)
    private String number4;                 /* 4번째 숫자 */
    @Column(length = 2)
    private String number5;                 /* 5번째 숫자 */
    @Column(length = 2)
    private String number6;                 /* 6번째 숫자 */
    @Column(length = 2)
    private String number7;                 /* 보너스 숫자 */
    private Long totalSalesPrice;           /* 총 판매 금액 */
    private Integer autoWinnerCount;        /* 자동 당첨자 수 */
    private Integer semiAutoWinnerCount;    /* 반자동 당첨자 수 */
    private Integer manualWinnerCount;      /* 수동 당첨자 수 */
}

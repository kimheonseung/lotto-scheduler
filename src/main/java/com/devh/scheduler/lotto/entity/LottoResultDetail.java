package com.devh.scheduler.lotto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <pre>
 * Description :
 *     로또 세부 당첨 정보 테이블 관련 Entity
 * ===============================================
 * Member fields :
 *     private Long rowId
 *     private Integer rank
 *     private Long totalPrize
 *     private Long perPersonPrize
 *     private Integer totalWinnerCount
 *     private LottoResult lottoResult
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LottoResultDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rowId;                     /* Primary Key 자동 증가치 */
    private Integer rank;                   /* 순위 정보 (1 ~ 5) */
    private Long totalPrize;                /* 총 상금 */
    private Long perPersonPrize;            /* 1인당 상금 */
    private Integer totalWinnerCount;       /* 총 당첨자 수 */
    @ManyToOne(fetch = FetchType.LAZY)
    private LottoResult lottoResult;        /* 회차 정보 */
}

package com.devh.scheduler.lotto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * Description :
 *     LottoResultDetail Entity에 대응되는 DTO
 * ===============================================
 * Member fields :
 *     private Long rowId
 *     private Integer rank
 *     private Long totalPrize
 *     private Long perPersonPrize
 *     private Long totalSalesPrice
 *     private Integer totalWinnerCount
 *     private Integer turn
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
public class LottoResultDetailDTO {
    private Long rowId;
    private Integer rank;
    private Long totalPrize;
    private Long perPersonPrize;
    private Long totalSalesPrice;
    private Integer totalWinnerCount;
    private Integer turn;
}

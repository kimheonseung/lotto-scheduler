package com.devh.scheduler.lotto.vo;

import com.devh.scheduler.lotto.entity.LottoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * Description :
 *     LottoWinningStore Entity에 대응되는 DTO
 * ===============================================
 * Member fields :
 *     String rowId
 *     Integer turn
 *     Integer rank
 *     String method
 *     Integer id
 *     String name
 *     String address
 *     String addressDivision1
 *     String addressDivision2
 *     String addressDivision3
 *     double latitude
 *     double longitude
 *     String phone
 *     String mapId
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-10-23
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LottoWinningStoreVO {
    private String rowId;
    private Integer turn;
    private Integer rank;
    private String method;
    private Integer id;
    private String name;
    private String address;
    private String addressDivision1;
    private String addressDivision2;
    private String addressDivision3;
    private double latitude;
    private double longitude;
    private String phone;
    private String mapId;
}

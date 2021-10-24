package com.devh.scheduler.lotto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * <pre>
 * Description :
 *     DB용 로또 당첨 판매점 엔티티
 * ===============================================
 * Member fields :
 *     String rowId
 *     LottoResult lottoResult
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
 * Date   : 2021/03/11
 * </pre>
 */
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LottoWinningStore {
    @Id
    private String rowId;              /* Primary Key (turn_rank_number) */
    @ManyToOne(fetch = FetchType.LAZY)
    private LottoResult lottoResult;   /* 회차 정보 참조 */
    private Integer rank;              /* 순위 정보 (1, 2) */
    private String method;             /* 당첨 방법 (auto, semi_auto, manual) */
    private Integer id;                /* 판매점 번호 */
    private String name;               /* 판매점 이름 */
    private String address;            /* 판매점 주소 */
    private String addressDivision1;   /* 판매점 주소 1 */
    private String addressDivision2;   /* 판매점 주소 2 */
    private String addressDivision3;   /* 판매점 주소 3 */
    private double latitude;             /* 위치 위도 */
    private double longitude;            /* 위치 경도 */
    private String phone;              /* 판매점 연락처 */
    private String mapId;              /* 사이트 자체 판매점 고유값 */
}

package com.devh.scheduler.lotto.entity;

import lombok.*;
//import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * <pre>
 * Description :
 *     ElasticSearch용 로또 당첨 판매점 엔티티
 * ===============================================
 * Member fields :
 *     private Integer turn
 *     private Integer rank
 *     private String method
 *     private Integer storeNumber
 *     private String storeName
 *     private String storeAddress
 *     private String storeAddress1
 *     private String storeAddress2
 *     private String storeAddress3
 *     private String storeLocation
 *     private String storePhone
 *     private String storeMapId
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021/03/11
 * </pre>
 */
@Deprecated
//@Document(indexName = "lotto_result_store", createIndex = false)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LottoResultStore {
//    @Id
//    @Field(type = FieldType.Keyword, name = "row_id")
    private String rowId;
//    @Field(type = FieldType.Integer, name = "turn")
    private Integer turn;               /* 회차 정보 */
//    @Field(type = FieldType.Integer, name = "rank")
    private Integer rank;               /* 순위 정보 (1, 2) */
//    @Field(type = FieldType.Keyword, name = "method")
    private String method;              /* 당첨 방법 (auto, semi_auto, manual) */
//    @Field(type = FieldType.Integer, name = "store_number")
    private Integer storeNumber;        /* 판매점 번호 */
//    @Field(type = FieldType.Keyword, name = "store_name")
    private String storeName;           /* 판매점 이름 */
//    @Field(type = FieldType.Keyword, name = "store_address")
    private String storeAddress;        /* 판매점 주소 */
//    @Field(type = FieldType.Keyword, name = "store_address1")
    private String storeAddress1;        /* 판매점 주소 1 */
//    @Field(type = FieldType.Keyword, name = "store_address2")
    private String storeAddress2;        /* 판매점 주소 2 */
//    @Field(type = FieldType.Keyword, name = "store_address3")
    private String storeAddress3;        /* 판매점 주소 3 */
//    @Field(name = "store_location", ignoreMalformed = true)
//    @GeoPointField
//    private GeoPoint storeLocation;      /* 판매점 위치의 위도 / 경도 */
//    @Field(type = FieldType.Keyword, name = "store_phone")
    private String storePhone;           /* 판매점 연락처 */
//    @Field(type = FieldType.Keyword, name = "store_map_id")
    private String storeMapId;           /* 사이트 자체 판매점 고유값 */
}

package com.devh.scheduler.lotto.vo;

import com.devh.scheduler.lotto.constant.NewsResultStoreField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description :
 *     LottoResultStore Entity에 대응되는 DTO
 * ===============================================
 * Member fields :
 *     private String rowId
 *     private Integer rank
 *     private String method
 *     private Integer storeNumber
 *     private String storeName
 *     private String storeAddress
 *     private String storePhone
 *     private String storeMapId
 *     private Integer turn
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-03-09
 * </pre>
 */
@Deprecated
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LottoResultStoreVO {
    private String rowId;
    private Integer rank;
    @Builder.Default
    private String method = "";
    private Integer storeNumber;
    private String storeName;
    private String storeAddress;
    private String storeAddress1;
    private String storeAddress2;
    private String storeAddress3;
    @Builder.Default
    private Double storeLatitude = null;
    @Builder.Default
    private Double storeLongitude = null;
    private String storePhone;
    private String storeMapId;
    private Integer turn;

    public Map<String, Object> getMapForElasticsearch() {
        Map<String, Object> geoMap = null;
        if(this.storeLatitude != null && this.storeLongitude != null) {
            geoMap = new HashMap<>();
            geoMap.put("lat", this.storeLatitude);
            geoMap.put("lon", this.storeLongitude);
        }

        Map<String, Object> map = new HashMap<>();
        map.put(NewsResultStoreField.ROW_ID.getSnakeCase(), this.rowId);
        map.put(NewsResultStoreField.TURN.getSnakeCase(), this.turn);
        map.put(NewsResultStoreField.RANK.getSnakeCase(), this.rank);
        map.put(NewsResultStoreField.METHOD.getSnakeCase(), this.method);
        map.put(NewsResultStoreField.STORE_NUMBER.getSnakeCase(), this.storeNumber);
        map.put(NewsResultStoreField.STORE_NAME.getSnakeCase(), this.storeName);
        map.put(NewsResultStoreField.STORE_ADDRESS.getSnakeCase(), this.storeAddress);
        map.put(NewsResultStoreField.STORE_ADDRESS1.getSnakeCase(), this.storeAddress1);
        map.put(NewsResultStoreField.STORE_ADDRESS2.getSnakeCase(), this.storeAddress2);
        map.put(NewsResultStoreField.STORE_ADDRESS3.getSnakeCase(), this.storeAddress3);
        map.put(NewsResultStoreField.STORE_LOCATION.getSnakeCase(), geoMap);
        map.put(NewsResultStoreField.STORE_PHONE.getSnakeCase(), this.storePhone);
        map.put(NewsResultStoreField.STORE_MAP_ID.getSnakeCase(), this.storeMapId);
        return map;
    }
}

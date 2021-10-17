package com.devh.scheduler.lotto.constant;

/**
 * <pre>
 * Description :
 *     lotto_result_store 컬럼명 또는 필드명 관련 상수
 * ===============================================
 * Member fields :
 *     private String rowId
 *     private Integer turn
 *     private Integer rank
 *     private String method
 *     private Integer storeNumber
 *     private String storeName
 *     private String storeAddress
 *     private String storeAddress1
 *     private String storeAddress2
 *     private String storeAddress3
 *     private String storePhone
 *     private String storeMapId
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-03-13
 * </pre>
 */
public enum NewsResultStoreField {
    ROW_ID("row_id", "rowId"),
    TURN("turn", "turn"),
    RANK("rank", "rank"),
    METHOD("method", "method"),
    STORE_NUMBER("store_number", "storeNumber"),
    STORE_NAME("store_name", "storeName"),
    STORE_ADDRESS("store_address", "storeAddress"),
    STORE_ADDRESS1("store_address1", "storeAddress1"),
    STORE_ADDRESS2("store_address2", "storeAddress2"),
    STORE_ADDRESS3("store_address3", "storeAddress3"),
    STORE_LOCATION("store_location", "storeLocation"),
    STORE_LOCATION_LAT("lat", "lat"),
    STORE_LOCATION_LON("lon", "lon"),
    STORE_PHONE("store_phone", "storePhone"),
    STORE_MAP_ID("store_map_id", "storeMapId");

    final String snakeCase;
    final String camelCase;
    NewsResultStoreField(String snakeCase, String camelCase) {
        this.snakeCase = snakeCase;
        this.camelCase = camelCase;
    }

    public String getCamelCase() {
        return this.camelCase;
    }

    public String getSnakeCase() {
        return this.snakeCase;
    }
}

package com.devh.scheduler.lotto.constant;

/**
 * <pre>
 * Description :
 *     로또 당첨 결과 테이블 관련 컬럼 상수
 * ===============================================
 * Member fields :
 *     LottoMethod
 *     LottoRank
 *     LottoSelector
 *     LottoURL
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-04-10
 * </pre>
 */
public enum LottoResultColumn {
    TURN("turn", "turn"),
    DATE("date", "date"),
    NUMBER1("number1", "number1"),
    NUMBER2("number2", "number2"),
    NUMBER3("number3", "number3"),
    NUMBER4("number4", "number4"),
    NUMBER5("number5", "number5"),
    NUMBER6("number6", "number6"),
    NUMBER7("number7", "number7"),
    TOTAL_SALES_PRICE("total_sales_price", "totalSalesPrice"),
    AUTO_WINNER_COUNT("auto_winner_count", "autoWinnerCount"),
    SEMI_AUTO_WINNER_COUNT("semi_auto_winner_count", "semiAutoWinnerCount"),
    MANUAL_WINNER_COUNT("manual_winner_count", "manualWinnerCount");

    final String snakeCase;
    final String camelCase;
    LottoResultColumn(String snakeCase, String camelCase) {
        this.snakeCase = snakeCase;
        this.camelCase = camelCase;
    }

    public String getCamelCase() {
        return camelCase;
    }

    public String getSnakeCase() {
        return snakeCase;
    }
}

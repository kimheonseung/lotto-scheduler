package com.devh.scheduler.lotto.constant;

/**
 * <pre>
 * Description :
 *     로또 당첨 상세 테이블 관련 컬럼 상수
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
public enum LottoResultDetailColumn {
    ROW_ID("row_id", "rowId"),
    RANK("rank", "rank"),
    TOTAL_WINNER_COUNT("total_winner_count", "totalWinnerCount"),
    TOTAL_PRIZE("total_prize", "totalPrize"),
    PER_PERSON_PRIZE("per_person_prize", "perPersonPrize"),
    LOTTO_RESULT_TURN("lotto_result_turn", "lottoResultTurn");
    final String snakeCase;
    final String camelCase;
    LottoResultDetailColumn(String snakeCase, String camelCase) {
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

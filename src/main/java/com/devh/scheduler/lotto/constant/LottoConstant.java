package com.devh.scheduler.lotto.constant;

/**
 * <pre>
 * Description :
 *     로또 관련 상수 모음 클래스
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
public class LottoConstant {

    /**
     * <pre>
     * Description :
     *     당첨 방식 관련 상수
     * ===============================================
     * Member fields :
     *     AUTO
     *     SEMI_AUTO
     *     MANUAL
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-03-09
     * </pre>
     */
    public enum LottoMethod {
        AUTO("자동", "auto"),
        SEMI_AUTO("반자동", "semi_auto"),
        MANUAL("수동", "manual");

        private final String koreanValue;
        private final String englishValue;
        LottoMethod(String koreanValue, String englishValue) {
            this.koreanValue = koreanValue;
            this.englishValue = englishValue;
        }

        public String getKoreanValue() {
            return koreanValue;
        }

        public String getEnglishValue() {
            return englishValue;
        }
    }

    /**
     * <pre>
     * Description :
     *     로또 순위 관련 상수
     * ===============================================
     * Member fields :
     *     FIRST
     *     SECOND
     *     THIRD
     *     FOURTH
     *     FIFTH
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    public enum LottoRank {
        FIRST(1, "당첨번호 6개 숫자일치"),
        SECOND(2, "당첨번호 5개 숫자일치 + 보너스 숫자일치"),
        THIRD(3, "당첨번호 5개 숫자일치"),
        FOURTH(4, "당첨번호 4개 숫자일치"),
        FIFTH(5, "당첨번호 3개 숫자일치");

        private final int rank;
        private final String description;
        LottoRank(int rank, String description) {
            this.rank = rank;
            this.description = description;
        }

        public int getRank() {
            return rank;
        }
        public String getDescription() {
            return description;
        }
    }

    /**
     * <pre>
     * Description :
     *     로또 URL에서 각 정보를 얻어오기 위한 html 셀렉터 관련 상수
     * ===============================================
     * Member fields :
     *     TURN
     *     DATE
     *     NUMBER1
     *     NUMBER2
     *     NUMBER3
     *     NUMBER4
     *     NUMBER5
     *     NUMBER6
     *     NUMBER7
     *     PER_PERSON_PRIZE_1
     *     TOTAL_PRIZE_1
     *     TOTAL_WINNER_COUNT_1
     *     WINNER_METHOD
     *     PER_PERSON_PRIZE_2
     *     TOTAL_PRIZE_2
     *     TOTAL_WINNER_COUNT_2
     *     PER_PERSON_PRIZE_3
     *     TOTAL_PRIZE_3
     *     TOTAL_WINNER_COUNT_3
     *     PER_PERSON_PRIZE_4
     *     TOTAL_PRIZE_4
     *     TOTAL_WINNER_COUNT_4
     *     PER_PERSON_PRIZE_5
     *     TOTAL_PRIZE_5
     *     TOTAL_WINNER_COUNT_5
     *     TOTAL_SALES_PRICE
     *     STORE_FIRST_TBODY
     *     STORE_FIRST_NODATA
     *     STORE_SECOND_TBODY
     *     STORE_SECOND_NODATA
     *     STORE_SECOND_PAGINATE
     *     STORE_PHONE
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    public enum LottoSelector {

        /* LottoResult 관련 상수 */
        TURN("#article > div:nth-child(2) > div > div.win_result > h4 > strong"),
        DATE("#article > div:nth-child(2) > div > div.win_result > p"),
        NUMBER1("#article > div:nth-child(2) > div > div.win_result > div > div.num.win > p > span:nth-child(1)"),
        NUMBER2("#article > div:nth-child(2) > div > div.win_result > div > div.num.win > p > span:nth-child(2)"),
        NUMBER3("#article > div:nth-child(2) > div > div.win_result > div > div.num.win > p > span:nth-child(3)"),
        NUMBER4("#article > div:nth-child(2) > div > div.win_result > div > div.num.win > p > span:nth-child(4)"),
        NUMBER5("#article > div:nth-child(2) > div > div.win_result > div > div.num.win > p > span:nth-child(5)"),
        NUMBER6("#article > div:nth-child(2) > div > div.win_result > div > div.num.win > p > span:nth-child(6)"),
        NUMBER7("#article > div:nth-child(2) > div > div.win_result > div > div.num.bonus > p > span"),

        /* LottoResultDetail 관련 상수 */
        /* 1등 관련 정보 */
        PER_PERSON_PRIZE_1("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(1) > td:nth-child(4)"),
        TOTAL_PRIZE_1("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(1) > td:nth-child(2) > strong"),
        TOTAL_WINNER_COUNT_1("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(1) > td:nth-child(3)"),
        WINNER_METHOD("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(1) > td:nth-child(6)"),
        /* 2등 관련 정보 */
        PER_PERSON_PRIZE_2("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(2) > td:nth-child(4)"),
        TOTAL_PRIZE_2("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(2) > td:nth-child(2) > strong"),
        TOTAL_WINNER_COUNT_2("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(2) > td:nth-child(3)"),
        /* 3등 관련 정보 */
        PER_PERSON_PRIZE_3("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(3) > td:nth-child(4)"),
        TOTAL_PRIZE_3("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(3) > td:nth-child(2) > strong"),
        TOTAL_WINNER_COUNT_3("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(3) > td:nth-child(3)"),
        /* 4등 관련 정보 */
        PER_PERSON_PRIZE_4("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(4) > td:nth-child(4)"),
        TOTAL_PRIZE_4("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(4) > td:nth-child(2) > strong"),
        TOTAL_WINNER_COUNT_4("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(4) > td:nth-child(3)"),
        /* 5등 관련 정보 */
        PER_PERSON_PRIZE_5("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(5) > td:nth-child(4)"),
        TOTAL_PRIZE_5("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(5) > td:nth-child(2) > strong"),
        TOTAL_WINNER_COUNT_5("#article > div:nth-child(2) > div > table > tbody > tr:nth-child(5) > td:nth-child(3)"),
        /* 총 판매 금액 */
        TOTAL_SALES_PRICE("#article > div:nth-child(2) > div > ul > li:nth-child(2) > strong"),

        /* 1등 당첨 판매점 테이블 BODY */
        STORE_FIRST_TBODY("#article > div:nth-child(2) > div > div:nth-child(4) > table > tbody"),
        /* 1등 당첨 판매점 테이블 정보 없음 선택자 */
        STORE_FIRST_NODATA("#article > div:nth-child(2) > div > div:nth-child(4) > table > tbody > tr > .nodata"),
        /* 2등 당첨 판매점 테이블 BODY */
        STORE_SECOND_TBODY("#article > div:nth-child(2) > div > div:nth-child(5) > table > tbody"),
        /* 2등 당첨 판매점 테이블 정보 없음 선택자 */
        STORE_SECOND_NODATA("#article > div:nth-child(2) > div > div:nth-child(5) > table > tbody > tr > .nodata"),
        /* 2등 페이징 */
        STORE_SECOND_PAGINATE("#page_box"),
        /* 판매점 상세정보에서 연락처 */
        STORE_PHONE("body > section > div > table > tbody > tr:nth-child(2) > td");

        private final String selector;
        LottoSelector(String selector) {
            this.selector = selector;
        }

        public String getSelector() {
            return selector;
        }
    }

    /**
     * <pre>
     * Description :
     *     파싱할 로또 URL 관련 상수
     * ===============================================
     * Member fields :
     *     LATEST
     *     PAST_PREFIX
     *     STORE_PREFIX
     *     STORE_MAP_PREFIX
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    public enum LottoURL {
        /* 최신 회차 정보가 표기되는 URL */
        LATEST("https://dhlottery.co.kr/gameResult.do?method=byWin"),
        /* 특정 회차 정보가 표기되는 URL. 제일 뒤에 회차를 추가해야 한다. */
        PAST_PREFIX("https://dhlottery.co.kr/gameResult.do?method=byWin&drwNo="),
        /* 판매점 정보가 표기되는 URL */
        STORE_PREFIX("https://dhlottery.co.kr/store.do?method=topStore&pageGubun=L645&drwNo="),
        /* 판매점 상세정보 새창 URL */
        STORE_MAP_PREFIX("https://dhlottery.co.kr/store.do?method=topStoreLocation&gbn=lotto&rtlrId=");

        private final String url;
        LottoURL(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}

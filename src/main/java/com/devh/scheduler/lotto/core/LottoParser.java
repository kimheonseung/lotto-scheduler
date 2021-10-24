package com.devh.scheduler.lotto.core;

import com.devh.common.util.ExceptionUtils;
import com.devh.common.util.component.JsoupUtils;
import com.devh.scheduler.lotto.constant.LottoConstant;
import com.devh.scheduler.lotto.vo.LottoResultVO;
import com.devh.scheduler.lotto.vo.LottoResultDetailVO;
import com.devh.scheduler.lotto.vo.LottoResultStoreVO;
import com.devh.scheduler.lotto.vo.LottoWinningStoreVO;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LottoParser {
    private final String BLANK = "";
    private final String SPACE = " ";
    private final String DASH = "-";
    private final String UNDERBAR = "_";
    private final String PARENTHESIS_OPEN = "(";
    private final String COMMA = ",";

    private final String ONCLICK = "onclick";
    private final String TR = "tr";
    private final String TD = "td";
    private final String A = "a";

    /* 1등 당첨 방식 배열 관련 Index 상수 */
    private final int INDEX_WINNER_AUTO_COUNT      = 0;
    private final int INDEX_WINNER_MANUAL_COUNT    = 1;
    private final int INDEX_WINNER_SEMI_AUTO_COUNT = 2;

    private final Logger logger = LoggerFactory.getLogger(LottoParser.class);

    /* DI */
    private final JsoupUtils jsoupUtils;

    /**
     * <pre>
     * Description
     *     웹 페이지에서 최신 회차 정보를 조회
     * ===============================================
     * Parameters
     *     Document document
     * Returns
     *     Integer
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    public Integer getTurnFromDocument(Document document) {
        final String TURN_SUFFIX = "회";

        final String strTurn = document
                .select(LottoConstant.LottoSelector.TURN.getSelector())
                .text()
                .replace(TURN_SUFFIX, BLANK);

        return Integer.parseInt(strTurn);
    }

    public LottoResultVO getLottoResultVOFromDocument(Document document) {
        final Integer turn = getTurnFromDocument(document);
        final String YEAR = "년";
        final String MONTH = "월";
        final String DAY_SUFFIX = "일추첨)";

        final String date = document
                .select(LottoConstant.LottoSelector.DATE.getSelector())
                .text()
                .replace(SPACE, BLANK)
                .replace(PARENTHESIS_OPEN, BLANK)
                .replace(YEAR, DASH)
                .replace(MONTH, DASH)
                .replace(DAY_SUFFIX, BLANK)
                .trim();

        final String number1 = document
                .select(LottoConstant.LottoSelector.NUMBER1.getSelector())
                .text();

        final String number2 = document
                .select(LottoConstant.LottoSelector.NUMBER2.getSelector())
                .text();

        final String number3 = document
                .select(LottoConstant.LottoSelector.NUMBER3.getSelector())
                .text();

        final String number4 = document
                .select(LottoConstant.LottoSelector.NUMBER4.getSelector())
                .text();

        final String number5 = document
                .select(LottoConstant.LottoSelector.NUMBER5.getSelector())
                .text();

        final String number6 = document
                .select(LottoConstant.LottoSelector.NUMBER6.getSelector())
                .text();

        final String number7 = document
                .select(LottoConstant.LottoSelector.NUMBER7.getSelector())
                .text();

        final Long totalSalesPrice = getTotalSalesPriceFromDocument(document);

        final Integer[] winnerCountArray = getWinnerCountArrayFromDocument(document);

        return LottoResultVO.builder()
                .turn(turn)
                .date(date)
                .number1(number1)
                .number2(number2)
                .number3(number3)
                .number4(number4)
                .number5(number5)
                .number6(number6)
                .number7(number7)
                .totalSalesPrice(totalSalesPrice)
                .autoWinnerCount(winnerCountArray[INDEX_WINNER_AUTO_COUNT])
                .semiAutoWinnerCount(winnerCountArray[INDEX_WINNER_SEMI_AUTO_COUNT])
                .manualWinnerCount(winnerCountArray[INDEX_WINNER_MANUAL_COUNT])
                .build();
    }

    /**
     * <pre>
     * Description
     *     Document로부터 총 판매 금액을 반환
     * ===============================================
     * Parameters
     *     Document document
     * Returns
     *     Long (파싱 실패 시 -1)
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    private Long getTotalSalesPriceFromDocument(Document document) {
        final String strTotalSalesPrice = getParsedStringPrice(LottoConstant.LottoSelector.TOTAL_SALES_PRICE.getSelector(), document);
        return Long.parseLong(strTotalSalesPrice);
    }

    /**
     * <pre>
     * Description
     *     Document 객체로부터 "0,000,000원" 과 같은 금액을 "0000000"으로 반환
     * ===============================================
     * Parameters
     *     String selector
     *     Document document
     * Returns
     *     String
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    private String getParsedStringPrice(String selector, Document document) {
        final String WON = "원";
        return document
                .select(selector)
                .text()
                .replaceAll(COMMA, BLANK)
                .replace(WON, BLANK);
    }

    /**
     * <pre>
     * Description
     *     Document로부터 1등 자동 / 수동 / 반자동 당첨자 수 배열을 반환
     * ===============================================
     * Parameters
     *     Document document
     * Returns
     *     Integer[3] ([자동, 수동, 반자동])
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    private Integer[] getWinnerCountArrayFromDocument(Document document) {
        Integer[] winnerCountArray = new Integer[3];

        int autoCount     = 0;
        int manualCount   = 0;
        int semiAutoCount = 0;

        String winMethodText = document.select(LottoConstant.LottoSelector.WINNER_METHOD.getSelector()).text();
        if(winMethodText.length() != 0) {
            winMethodText = winMethodText.trim();
            winMethodText = winMethodText.replace(SPACE, BLANK);

            final String AUTO = "자동";
            final int autoIndex = winMethodText.indexOf(AUTO);
            final String MANUAL = "수동";
            final int manualIndex = winMethodText.indexOf(MANUAL);
            final String SEMI_AUTO = "반자동";
            final int semiAutoIndex = winMethodText.indexOf(SEMI_AUTO);

            final int winMethodTextLength = winMethodText.length();

            final boolean isWinAutoExist     = autoIndex != -1;
            final boolean isWinManualExist   = manualIndex != -1;
            final boolean isWinSemiAutoExist = semiAutoIndex != -1;

            if(isWinAutoExist && isWinManualExist && isWinSemiAutoExist) {
                int autoIndexStart = autoIndex + 2;
                int autoIndexEnd   = manualIndex;

                int manualIndexStart = manualIndex + 2;
                int manulaIndexEnd   = semiAutoIndex;

                int semiAutoIndexStart = semiAutoIndex + 3;
                int semiAutoIndexEnd   = winMethodTextLength;

                autoCount     = Integer.parseInt(winMethodText.substring(autoIndexStart, autoIndexEnd));
                manualCount   = Integer.parseInt(winMethodText.substring(manualIndexStart, manulaIndexEnd));
                semiAutoCount = Integer.parseInt(winMethodText.substring(semiAutoIndexStart, semiAutoIndexEnd));

            } else if(isWinAutoExist && isWinManualExist && !isWinSemiAutoExist) {
                int autoIndexStart = autoIndex + 2;
                int autoIndexEnd   = manualIndex;

                int manualIndexStart = manualIndex + 2;
                int manulaIndexEnd   = winMethodTextLength;

                autoCount     = Integer.parseInt(winMethodText.substring(autoIndexStart, autoIndexEnd));
                manualCount   = Integer.parseInt(winMethodText.substring(manualIndexStart, manulaIndexEnd));

            } else if(isWinAutoExist && !isWinManualExist && isWinSemiAutoExist) {
                int autoIndexStart = autoIndex + 2;
                int autoIndexEnd   = semiAutoIndex;

                int semiAutoIndexStart = semiAutoIndex + 3;
                int semiAutoIndexEnd   = winMethodTextLength;

                autoCount     = Integer.parseInt(winMethodText.substring(autoIndexStart, autoIndexEnd));
                semiAutoCount = Integer.parseInt(winMethodText.substring(semiAutoIndexStart, semiAutoIndexEnd));

            } else if(isWinAutoExist && !isWinManualExist && !isWinSemiAutoExist) {
                int autoIndexStart = autoIndex + 2;
                int autoIndexEnd   = winMethodTextLength;

                autoCount = Integer.parseInt(winMethodText.substring(autoIndexStart, autoIndexEnd));

            } else if(!isWinAutoExist && isWinManualExist && isWinSemiAutoExist) {
                int manualIndexStart = manualIndex + 2;
                int manualIndexEnd   = semiAutoIndex;

                int semiAutoIndexStart = semiAutoIndex + 3;
                int semiAutoIndexEnd   = manualIndexEnd;

                manualCount   = Integer.parseInt(winMethodText.substring(manualIndexStart, manualIndexEnd));
                semiAutoCount = Integer.parseInt(winMethodText.substring(semiAutoIndexStart, semiAutoIndexEnd));

            } else if(!isWinAutoExist && !isWinManualExist && isWinSemiAutoExist) {
                int semiAutoIndexStart = semiAutoIndex + 3;
                int semiAutoIndexEnd   = winMethodTextLength;

                semiAutoCount = Integer.parseInt(winMethodText.substring(semiAutoIndexStart, semiAutoIndexEnd));

            }
        }

        winnerCountArray[INDEX_WINNER_AUTO_COUNT]      = autoCount;
        winnerCountArray[INDEX_WINNER_MANUAL_COUNT]    = manualCount;
        winnerCountArray[INDEX_WINNER_SEMI_AUTO_COUNT] = semiAutoCount;

        return winnerCountArray;
    }

    /**
     * <pre>
     * Description
     *     Document로부터 LottoResultDetailVO 리스트를 파싱하여 반환
     * ===============================================
     * Parameters
     *     Document document
     * Returns
     *     List<LottoResultDetailVO>
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    public List<LottoResultDetailVO> getLottoResultDetailVOListFromURL(Document document) {
        final Integer turn = getTurnFromDocument(document);
        final boolean booleanTotal = true;
        final boolean booleanPerPerson = false;

        LottoResultDetailVO firstLottoResultDetailVO = LottoResultDetailVO.builder()
                .turn(turn)
                .rank(LottoConstant.LottoRank.FIRST.getRank())
                .totalPrize(getPrizeFromDocument(LottoConstant.LottoRank.FIRST, document, booleanTotal))
                .perPersonPrize(getPrizeFromDocument(LottoConstant.LottoRank.FIRST, document, booleanPerPerson))
                .totalWinnerCount(getTotalWinnerCount(LottoConstant.LottoRank.FIRST, document))
                .build();

        LottoResultDetailVO secondLottoResultDetailVO = LottoResultDetailVO.builder()
                .turn(turn)
                .rank(LottoConstant.LottoRank.SECOND.getRank())
                .totalPrize(getPrizeFromDocument(LottoConstant.LottoRank.SECOND, document, booleanTotal))
                .perPersonPrize(getPrizeFromDocument(LottoConstant.LottoRank.SECOND, document, booleanPerPerson))
                .totalWinnerCount(getTotalWinnerCount(LottoConstant.LottoRank.SECOND, document))
                .build();

        LottoResultDetailVO thirdLottoResultDetailVO = LottoResultDetailVO.builder()
                .turn(turn)
                .rank(LottoConstant.LottoRank.THIRD.getRank())
                .totalPrize(getPrizeFromDocument(LottoConstant.LottoRank.THIRD, document, booleanTotal))
                .perPersonPrize(getPrizeFromDocument(LottoConstant.LottoRank.THIRD, document, booleanPerPerson))
                .totalWinnerCount(getTotalWinnerCount(LottoConstant.LottoRank.THIRD, document))
                .build();

        LottoResultDetailVO fourthLottoResultDetailVO = LottoResultDetailVO.builder()
                .turn(turn)
                .rank(LottoConstant.LottoRank.FOURTH.getRank())
                .totalPrize(getPrizeFromDocument(LottoConstant.LottoRank.FOURTH, document, booleanTotal))
                .perPersonPrize(getPrizeFromDocument(LottoConstant.LottoRank.FOURTH, document, booleanPerPerson))
                .totalWinnerCount(getTotalWinnerCount(LottoConstant.LottoRank.FOURTH, document))
                .build();

        LottoResultDetailVO fifthLottoResultDetailVO = LottoResultDetailVO.builder()
                .turn(turn)
                .rank(LottoConstant.LottoRank.FIFTH.getRank())
                .totalPrize(getPrizeFromDocument(LottoConstant.LottoRank.FIFTH, document, booleanTotal))
                .perPersonPrize(getPrizeFromDocument(LottoConstant.LottoRank.FIFTH, document, booleanPerPerson))
                .totalWinnerCount(getTotalWinnerCount(LottoConstant.LottoRank.FIFTH, document))
                .build();

        List<LottoResultDetailVO> result = new ArrayList<>();
        result.add(firstLottoResultDetailVO);
        result.add(secondLottoResultDetailVO);
        result.add(thirdLottoResultDetailVO);
        result.add(fourthLottoResultDetailVO);
        result.add(fifthLottoResultDetailVO);

        return result;
    }

    /**
     * <pre>
     * Description
     *     LottoRank, Document 객체에서 해당 순위의 총 상금을 반환
     * ===============================================
     * Parameters
     *     LottoRank lottoRank
     *     Document document
     *     boolean isTotalPrize
     * Returns
     *     Long (파싱 실패시 -1)
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    private Long getPrizeFromDocument(LottoConstant.LottoRank lottoRank, Document document, Boolean isTotalPrize) {
        final String selector;
        switch (lottoRank) {
            case FIRST:
                if(isTotalPrize)
                    selector = LottoConstant.LottoSelector.TOTAL_PRIZE_1.getSelector();
                else
                    selector = LottoConstant.LottoSelector.PER_PERSON_PRIZE_1.getSelector();
                break;
            case SECOND:
                if(isTotalPrize)
                    selector = LottoConstant.LottoSelector.TOTAL_PRIZE_2.getSelector();
                else
                    selector = LottoConstant.LottoSelector.PER_PERSON_PRIZE_2.getSelector();
                break;
            case THIRD:
                if(isTotalPrize)
                    selector = LottoConstant.LottoSelector.TOTAL_PRIZE_3.getSelector();
                else
                    selector = LottoConstant.LottoSelector.PER_PERSON_PRIZE_3.getSelector();
                break;
            case FOURTH:
                if(isTotalPrize)
                    selector = LottoConstant.LottoSelector.TOTAL_PRIZE_4.getSelector();
                else
                    selector = LottoConstant.LottoSelector.PER_PERSON_PRIZE_4.getSelector();
                break;
            case FIFTH:
                if(isTotalPrize)
                    selector = LottoConstant.LottoSelector.TOTAL_PRIZE_5.getSelector();
                else
                    selector = LottoConstant.LottoSelector.PER_PERSON_PRIZE_5.getSelector();
                break;
            default:
                selector = null;
                break;
        }

        if(selector == null)
            return -1L;

        final String strPrize = getParsedStringPrice(selector, document);

        try {
            return Long.parseLong(strPrize);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            return -1L;
        }
    }

    /**
     * <pre>
     * Description
     *     LottoRank, Document로부터 총 당첨자 수 반환
     * ===============================================
     * Parameters
     *     LottoRank lottoRank
     *     Document document
     * Returns
     *     Integer (파싱 실패 시 -1 반환)
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    private Integer getTotalWinnerCount(LottoConstant.LottoRank lottoRank, Document document) {
        final String selector;
        switch (lottoRank) {
            case FIRST:
                selector = LottoConstant.LottoSelector.TOTAL_WINNER_COUNT_1.getSelector();
                break;
            case SECOND:
                selector = LottoConstant.LottoSelector.TOTAL_WINNER_COUNT_2.getSelector();
                break;
            case THIRD:
                selector = LottoConstant.LottoSelector.TOTAL_WINNER_COUNT_3.getSelector();
                break;
            case FOURTH:
                selector = LottoConstant.LottoSelector.TOTAL_WINNER_COUNT_4.getSelector();
                break;
            case FIFTH:
                selector = LottoConstant.LottoSelector.TOTAL_WINNER_COUNT_5.getSelector();
                break;
            default:
                selector = null;
                break;
        }

        if(selector == null)
            return -1;

        final String totalWinnerCount = document
                .select(selector)
                .text()
                .replaceAll(COMMA, BLANK);

        try {
            return Integer.parseInt(totalWinnerCount);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            return -1;
        }
    }

    /**
     * <pre>
     * Description
     *     회차가 포함된 url로부터 판매점 정보를 파싱
     * ===============================================
     * Parameters
     *     int url
     * Returns
     *     List<LottoResultStoreVO>
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-03-09
     * </pre>
     */
    @Deprecated
    public List<LottoResultStoreVO> getLottoResultStoreVOListFromTurn(int turn) {

        Document document = jsoupUtils.getDocumentFromURL(LottoConstant.LottoURL.STORE_PREFIX.getUrl() + turn);

        List<LottoResultStoreVO> lottoResultStoreVOList = new ArrayList<>();
        if(document.select(LottoConstant.LottoSelector.STORE_FIRST_NODATA.getSelector()).isEmpty())
            lottoResultStoreVOList.addAll(getLottoResultStoreVOFromDocumentWithRank(document, LottoConstant.LottoRank.FIRST, turn));

        if(document.select(LottoConstant.LottoSelector.STORE_SECOND_NODATA.getSelector()).isEmpty()) {
            final int totalPage = document.select(LottoConstant.LottoSelector.STORE_SECOND_PAGINATE.getSelector()).select(A).size();
            if(totalPage >= 1) {
                boolean isPageEnd = false;
                int nowPage = 1;
                while(!isPageEnd) {
                    if(nowPage == 1)
                        lottoResultStoreVOList.addAll(getLottoResultStoreVOFromDocumentWithRank(document, LottoConstant.LottoRank.SECOND, turn));
                    else {
                        Document secondStorePageDocument = jsoupUtils.getDocumentFromURL(LottoConstant.LottoURL.STORE_PREFIX.getUrl() + turn + "&nowPage=" + nowPage);
                        lottoResultStoreVOList.addAll(getLottoResultStoreVOFromDocumentWithRank(secondStorePageDocument, LottoConstant.LottoRank.SECOND, turn));
                    }
                    ++nowPage;
                    isPageEnd = nowPage > totalPage;
                }
            }
        }

        return lottoResultStoreVOList;
    }
    public List<LottoWinningStoreVO> getLottoWinningStoreVOListFromTurn(int turn) {

        Document document = jsoupUtils.getDocumentFromURL(LottoConstant.LottoURL.STORE_PREFIX.getUrl() + turn);

        List<LottoWinningStoreVO> lottoWinningStoreVOList = new ArrayList<>();
        if(document.select(LottoConstant.LottoSelector.STORE_FIRST_NODATA.getSelector()).isEmpty())
            lottoWinningStoreVOList.addAll(getLottoWinningStoreVOFromDocumentWithRank(document, LottoConstant.LottoRank.FIRST, turn));

        if(document.select(LottoConstant.LottoSelector.STORE_SECOND_NODATA.getSelector()).isEmpty()) {
            final int totalPage = document.select(LottoConstant.LottoSelector.STORE_SECOND_PAGINATE.getSelector()).select(A).size();
            if(totalPage >= 1) {
                boolean isPageEnd = false;
                int nowPage = 1;
                while(!isPageEnd) {
                    if(nowPage == 1)
                        lottoWinningStoreVOList.addAll(getLottoWinningStoreVOFromDocumentWithRank(document, LottoConstant.LottoRank.SECOND, turn));
                    else {
                        Document secondStorePageDocument = jsoupUtils.getDocumentFromURL(LottoConstant.LottoURL.STORE_PREFIX.getUrl() + turn + "&nowPage=" + nowPage);
                        lottoWinningStoreVOList.addAll(getLottoWinningStoreVOFromDocumentWithRank(secondStorePageDocument, LottoConstant.LottoRank.SECOND, turn));
                    }
                    ++nowPage;
                    isPageEnd = nowPage > totalPage;
                }
            }
        }
        return lottoWinningStoreVOList;
    }

    /**
     * <pre>
     * Description
     *     Document 객체로부터 1등 또는 2등의 가게 정보들을 LottoResultVO List로 반환
     * ===============================================
     * Parameters
     *     Document document
     *     LottoRank lottoRank
     *     int turn
     * Returns
     *     List<LottoResultStoreVO>
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021/03/12
     * </pre>
     */
    private List<LottoResultStoreVO> getLottoResultStoreVOFromDocumentWithRank(Document document, LottoConstant.LottoRank lottoRank, int turn) {
        List<LottoResultStoreVO> lottoResultStoreVOList = new ArrayList<>();
        switch (lottoRank) {
            case FIRST:
                Elements firstStoreTrElements = document.select(LottoConstant.LottoSelector.STORE_FIRST_TBODY.getSelector()).select(TR);
                for(Element firstStoreTr : firstStoreTrElements) {
                    Elements firstStoreTdElements = firstStoreTr.select(TD);
                    LottoResultStoreVO lottoResultStoreVO = LottoResultStoreVO.builder()
                            .rank(lottoRank.getRank())
                            .turn(turn)
                            .build();
                    for (int index = 0; index < firstStoreTdElements.size(); ++index) {
                        Element storeTd = firstStoreTdElements.get(index);
                        if (index == 0)
                            setStoreNumberAndRowId(storeTd, lottoRank, turn, lottoResultStoreVO);
                        else if (index == 1)
                            lottoResultStoreVO.setStoreName(storeTd.text().trim());
                        else if (index == 2)
                            lottoResultStoreVO.setMethod(convertMethod(storeTd.text().trim()));
                        else if (index == 3)
                            setAddress(storeTd, lottoResultStoreVO);
                        else if (index == 4)
                            setMapIdAndPhone(storeTd, lottoResultStoreVO);

                    }
                    lottoResultStoreVOList.add(lottoResultStoreVO);
                }
                break;
            case SECOND:
                Elements secondStoreTrElements = document.select(LottoConstant.LottoSelector.STORE_SECOND_TBODY.getSelector()).select(TR);

                for(Element secondStoreTr : secondStoreTrElements) {
                    Elements secondStoreTdElements = secondStoreTr.select(TD);
                    LottoResultStoreVO lottoResultStoreVO = LottoResultStoreVO.builder()
                            .rank(lottoRank.getRank())
                            .turn(turn)
                            .build();
                    for (int index = 0; index < secondStoreTdElements.size(); ++index) {
                        Element storeTd = secondStoreTdElements.get(index);
                        if (index == 0)
                            setStoreNumberAndRowId(storeTd, lottoRank, turn, lottoResultStoreVO);
                        else if (index == 1)
                            lottoResultStoreVO.setStoreName(storeTd.text().trim());
                        else if (index == 2)
                            setAddress(storeTd, lottoResultStoreVO);
                        else if (index == 3)
                            setMapIdAndPhone(storeTd, lottoResultStoreVO);

                    }
                    lottoResultStoreVOList.add(lottoResultStoreVO);
                }
                break;
        }
        return lottoResultStoreVOList;
    }

    /**
     * <pre>
     * Description
     *     Document 객체로부터 1등 또는 2등의 가게 정보들을 LottoWinningStore List로 반환
     * ===============================================
     * Parameters
     *     Document document
     *     LottoRank lottoRank
     *     int turn
     * Returns
     *     List<LottoWinningStoreVO>
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-10-23
     * </pre>
     */
    private List<LottoWinningStoreVO> getLottoWinningStoreVOFromDocumentWithRank(Document document, LottoConstant.LottoRank lottoRank, int turn) {
        List<LottoWinningStoreVO> lottoWinningStoreVOList = new ArrayList<>();
        switch (lottoRank) {
            case FIRST:
                Elements firstStoreTrElements = document.select(LottoConstant.LottoSelector.STORE_FIRST_TBODY.getSelector()).select(TR);
                for(Element firstStoreTr : firstStoreTrElements) {
                    Elements firstStoreTdElements = firstStoreTr.select(TD);

                    LottoWinningStoreVO lottoWinningStoreVO = LottoWinningStoreVO.builder()
                            .rank(lottoRank.getRank())
                            .turn(turn)
                            .build();

                    for (int index = 0; index < firstStoreTdElements.size(); ++index) {
                        Element storeTd = firstStoreTdElements.get(index);
                        if (index == 0)
                            setIdAndRowId(storeTd, lottoRank, turn, lottoWinningStoreVO);
                        else if (index == 1)
                            lottoWinningStoreVO.setName(storeTd.text().trim());
                        else if (index == 2)
                            lottoWinningStoreVO.setMethod(convertMethod(storeTd.text().trim()));
                        else if (index == 3)
                            setAddress(storeTd, lottoWinningStoreVO);
                        else if (index == 4)
                            setMapIdAndPhone(storeTd, lottoWinningStoreVO);
                    }
                    lottoWinningStoreVOList.add(lottoWinningStoreVO);
                }
                break;
            case SECOND:
                Elements secondStoreTrElements = document.select(LottoConstant.LottoSelector.STORE_SECOND_TBODY.getSelector()).select(TR);

                for(Element secondStoreTr : secondStoreTrElements) {
                    Elements secondStoreTdElements = secondStoreTr.select(TD);
                    LottoWinningStoreVO lottoWinningStoreVO = LottoWinningStoreVO.builder()
                            .rank(lottoRank.getRank())
                            .turn(turn)
                            .build();
                    for (int index = 0; index < secondStoreTdElements.size(); ++index) {
                        Element storeTd = secondStoreTdElements.get(index);
                        if (index == 0)
                            setIdAndRowId(storeTd, lottoRank, turn, lottoWinningStoreVO);
                        else if (index == 1)
                            lottoWinningStoreVO.setName(storeTd.text().trim());
                        else if (index == 2)
                            setAddress(storeTd, lottoWinningStoreVO);
                        else if (index == 3)
                            setMapIdAndPhone(storeTd, lottoWinningStoreVO);

                    }
                    lottoWinningStoreVOList.add(lottoWinningStoreVO);
                }
                break;
        }
        return lottoWinningStoreVOList;
    }

    /**
     * <pre>
     * Description :
     *     Element 객체로부터 판매점 번호를 파싱하여 LottoResultStoreVO의 파라미터에 판매점번호와 rowId를 set
     *     rowId 는 회차_등수_판매점번호 형태
     *         ex) 512_1_4
     * ===============================================
     * Member fields :
     *     Element storeTd
     *     LottoRank lottoRank
     *     int turn
     *     LottoResultStoreVO lottoResultStoreVO
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021/03/12
     * </pre>
     */
    private void setStoreNumberAndRowId(Element storeTd, LottoConstant.LottoRank lottoRank, int turn, LottoResultStoreVO lottoResultStoreVO) {
        int storeNumber = Integer.parseInt(storeTd.text().trim());
        lottoResultStoreVO.setStoreNumber(storeNumber);
        lottoResultStoreVO.setRowId(turn + UNDERBAR + lottoRank.getRank() + UNDERBAR + storeNumber);
    }
    private void setIdAndRowId(Element storeTd, LottoConstant.LottoRank lottoRank, int turn, LottoWinningStoreVO lottoWinningStoreVO) {
        int storeNumber = Integer.parseInt(storeTd.text().trim());
        lottoWinningStoreVO.setId(storeNumber);
        lottoWinningStoreVO.setRowId(turn + UNDERBAR + lottoRank.getRank() + UNDERBAR + storeNumber);
    }

    /**
     * <pre>
     * Description :
     *     당첨 방식을 한글 -> 영문 또는 영문 -> 한글로 변환
     * ===============================================
     * Member fields :
     *     String method
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021/03/12
     * </pre>
     */
    private String convertMethod(String method) {
        if(method.equals(LottoConstant.LottoMethod.AUTO.getKoreanValue()))
            return LottoConstant.LottoMethod.AUTO.getEnglishValue();
        else if(method.equals(LottoConstant.LottoMethod.SEMI_AUTO.getKoreanValue()))
            return LottoConstant.LottoMethod.SEMI_AUTO.getEnglishValue();
        else if(method.equals(LottoConstant.LottoMethod.MANUAL.getKoreanValue()))
            return LottoConstant.LottoMethod.MANUAL.getEnglishValue();
        else if(method.equals(LottoConstant.LottoMethod.AUTO.getEnglishValue()))
            return LottoConstant.LottoMethod.AUTO.getKoreanValue();
        else if(method.equals(LottoConstant.LottoMethod.SEMI_AUTO.getEnglishValue()))
            return LottoConstant.LottoMethod.SEMI_AUTO.getKoreanValue();
        else if(method.equals(LottoConstant.LottoMethod.MANUAL.getEnglishValue()))
            return LottoConstant.LottoMethod.MANUAL.getKoreanValue();
        else
            return "";
    }

    /**
     * <pre>
     * Description :
     *     Element 객체로부터 주소를 파싱하여 LottoResultStoreVO의 파라미터에 set
     * ===============================================
     * Member fields :
     *     Element storeTd
     *     LottoResultStoreVO lottoResultStoreVO
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021/03/12
     * </pre>
     */
    private void setAddress(Element storeTd, LottoResultStoreVO lottoResultStoreVO) {
        final String storeAddress = storeTd.text().trim();
        final String[] storeAddressSplit = storeAddress.split(SPACE);
        lottoResultStoreVO.setStoreAddress(storeAddress);
        if(storeAddressSplit.length >= 3) {
            lottoResultStoreVO.setStoreAddress1(storeAddressSplit[0]);
            lottoResultStoreVO.setStoreAddress2(storeAddressSplit[1]);
            lottoResultStoreVO.setStoreAddress3(storeAddressSplit[2]);
        } else if(storeAddressSplit.length == 2) {
            lottoResultStoreVO.setStoreAddress1(storeAddressSplit[0]);
            lottoResultStoreVO.setStoreAddress2(storeAddressSplit[1]);
        } else if(storeAddressSplit.length == 1) {
            lottoResultStoreVO.setStoreAddress1(storeAddressSplit[0]);
        }

        setGeoPoint(lottoResultStoreVO);

    }
    private void setAddress(Element storeTd, LottoWinningStoreVO lottoWinningStoreVO) {
        final String storeAddress = storeTd.text().trim();
        final String[] storeAddressSplit = storeAddress.split(SPACE);
        lottoWinningStoreVO.setAddress(storeAddress);
        if(storeAddressSplit.length >= 3) {
            lottoWinningStoreVO.setAddressDivision1(storeAddressSplit[0]);
            lottoWinningStoreVO.setAddressDivision2(storeAddressSplit[1]);
            lottoWinningStoreVO.setAddressDivision3(storeAddressSplit[2]);
        } else if(storeAddressSplit.length == 2) {
            lottoWinningStoreVO.setAddressDivision1(storeAddressSplit[0]);
            lottoWinningStoreVO.setAddressDivision2(storeAddressSplit[1]);
        } else if(storeAddressSplit.length == 1) {
            lottoWinningStoreVO.setAddressDivision1(storeAddressSplit[0]);
        }
        setLocation(lottoWinningStoreVO);
    }

    /**
     * <pre>
     * Description
     *     주소가 입력된 객체로부터 위도 / 경도 정보를 set
     * ===============================================
     * Parameters
     *     LottoResultStoreVO lottoResultStoreVO
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021/03/14
     * </pre>
     */
    private void setGeoPoint(LottoResultStoreVO lottoResultStoreVO) {
        try {
            URL url = new URL("http://www.dawuljuso.com/input_pro.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST"); // HTTP POST 메소드 설정
            httpURLConnection.setDoOutput(true);

            final String targetAddress = lottoResultStoreVO.getStoreAddress();
            String encodedParamsString = "refine_ty=8&protocol_="+ UriEncoder.encode(targetAddress);
            OutputStream os = httpURLConnection.getOutputStream();
            byte[] input = encodedParamsString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            final String result = response.toString();
            br.close();

            final String[] resultSplit = result.split(";");
            final String addressPart = resultSplit[0];
            final String[] addressInformationArr = addressPart.split("\\|");
            boolean isDataEmpty = addressInformationArr[1] == null || addressInformationArr[1].length() == 0;

            if(isDataEmpty) {
//                logger.error("주소 데이터 반환 실패. 대상 : " + lottoResultStoreVO.getTurn() + "회 " + lottoResultStoreVO.getStoreAddress());
//                logger.error("주소 : " + Arrays.toString(addressInformationArr));
                return;
            }

            try {
                lottoResultStoreVO.setStoreLatitude(Double.valueOf(addressInformationArr[4]));
                lottoResultStoreVO.setStoreLongitude(Double.valueOf(addressInformationArr[3]));
            } catch (Exception e) {
//                logger.error(e.getMessage());
//                logger.error("주소 데이터 반환 실패. 대상 : " + lottoResultStoreVO.getTurn() + "회 " + lottoResultStoreVO.getStoreAddress());
//                logger.error("주소 : " + Arrays.toString(addressInformationArr));
                return;
            }

//            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            httpURLConnection.setRequestProperty("Accept", "*/*");
//            httpURLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
//            httpURLConnection.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
//            httpURLConnection.setRequestProperty("User-Agent", "PostmanRuntime/7.26.10");
        } catch (Exception e) {
//            logger.error(e.getMessage());
//            logger.error("주소 데이터 반환 실패. 대상 : " + lottoResultStoreVO.getTurn() + "회 " + lottoResultStoreVO.getStoreAddress());
        }
    }
    private void setLocation(LottoWinningStoreVO lottoWinningStoreVO) {
        try {
            URL url = new URL("http://www.dawuljuso.com/input_pro.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST"); // HTTP POST 메소드 설정
            httpURLConnection.setDoOutput(true);

            final String targetAddress = lottoWinningStoreVO.getAddress();
            String encodedParamsString = "refine_ty=8&protocol_="+ UriEncoder.encode(targetAddress);
            OutputStream os = httpURLConnection.getOutputStream();
            byte[] input = encodedParamsString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            final String result = response.toString();
            br.close();

            final String[] resultSplit = result.split(";");
            final String addressPart = resultSplit[0];
            final String[] addressInformationArr = addressPart.split("\\|");
            boolean isDataEmpty = addressInformationArr[1] == null || addressInformationArr[1].length() == 0;

            if(isDataEmpty) {
//                logger.error("주소 데이터 반환 실패. 대상 : " + lottoResultStoreVO.getTurn() + "회 " + lottoResultStoreVO.getStoreAddress());
//                logger.error("주소 : " + Arrays.toString(addressInformationArr));
                return;
            }

            try {
                lottoWinningStoreVO.setLatitude(Double.parseDouble(addressInformationArr[4]));
                lottoWinningStoreVO.setLongitude(Double.parseDouble(addressInformationArr[3]));
            } catch (Exception e) {
//                logger.error(e.getMessage());
//                logger.error("주소 데이터 반환 실패. 대상 : " + lottoResultStoreVO.getTurn() + "회 " + lottoResultStoreVO.getStoreAddress());
//                logger.error("주소 : " + Arrays.toString(addressInformationArr));
                return;
            }

//            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            httpURLConnection.setRequestProperty("Accept", "*/*");
//            httpURLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
//            httpURLConnection.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
//            httpURLConnection.setRequestProperty("User-Agent", "PostmanRuntime/7.26.10");
        } catch (Exception e) {
//            logger.error(e.getMessage());
//            logger.error("주소 데이터 반환 실패. 대상 : " + lottoResultStoreVO.getTurn() + "회 " + lottoResultStoreVO.getStoreAddress());
        }
    }

    /**
     * <pre>
     * Description :
     *     Element 객체로부터 맵 고유값과 연락처를 파싱하여 LottoResultVO의 파라미터에 set
     * ===============================================
     * Member fields :
     *     Element storeTd
     *     LottoResultStoreVO lottoResultStoreVO
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021/03/12
     * </pre>
     */
    private void setMapIdAndPhone(Element storeTd, LottoResultStoreVO lottoResultStoreVO) {
        final String tdOnclick = storeTd.select(A).attr(ONCLICK);
        final String mapId = tdOnclick.substring(tdOnclick.indexOf("('") + 2, tdOnclick.indexOf("')"));
        lottoResultStoreVO.setStoreMapId(mapId);
        Document mapDocument = jsoupUtils.getDocumentFromURL(LottoConstant.LottoURL.STORE_MAP_PREFIX.getUrl() + mapId);
        lottoResultStoreVO.setStorePhone(mapDocument.select(LottoConstant.LottoSelector.STORE_PHONE.getSelector()).text());
    }
    private void setMapIdAndPhone(Element storeTd, LottoWinningStoreVO lottoWinningStoreVO) {
        final String tdOnclick = storeTd.select(A).attr(ONCLICK);
        final String mapId = tdOnclick.substring(tdOnclick.indexOf("('") + 2, tdOnclick.indexOf("')"));
        lottoWinningStoreVO.setMapId(mapId);
        Document mapDocument = jsoupUtils.getDocumentFromURL(LottoConstant.LottoURL.STORE_MAP_PREFIX.getUrl() + mapId);
        lottoWinningStoreVO.setPhone(mapDocument.select(LottoConstant.LottoSelector.STORE_PHONE.getSelector()).text());
    }
}

package com.devh.scheduler.lotto.service;


import com.devh.scheduler.lotto.vo.LottoResultVO;
import com.devh.scheduler.lotto.entity.LottoResult;

/**
 * <pre>
 * Description : 
 *     LottoResult 엔티티 관련 인터페이스
 * ===============================================
 * Member fields : 
 *     Nothing
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
public interface LottoResultService {
    /**
     * <pre>
     * Description
     *     LottoResultDTO를 테이블에 insert
     * ===============================================
     * Parameters
     *     LottoResultDTO lottoResultDTO
     * Returns
     *     Boolean
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    Boolean saveVO(LottoResultVO lottoResultVO);
    /**
     * <pre>
     * Description
     *     저장된 가장 최신 회차를 select
     * ===============================================
     * Parameters
     *     Nothing
     * Returns
     *     Integer (테이블이 비어있으면 0)
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    Integer getLatestTurn();

    /**
     * <pre>
     * Description
     *     LottoResultDTO -> LottoResult 변환
     * ===============================================
     * Parameters
     *     LottoResultDTO lottoResultDTO
     * Returns
     *     LottoResult
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    default LottoResult voToEntity(LottoResultVO lottoResultVO) {
        return LottoResult.builder()
                .turn(lottoResultVO.getTurn())
                .date(lottoResultVO.getDate())
                .number1(lottoResultVO.getNumber1())
                .number2(lottoResultVO.getNumber2())
                .number3(lottoResultVO.getNumber3())
                .number4(lottoResultVO.getNumber4())
                .number5(lottoResultVO.getNumber5())
                .number6(lottoResultVO.getNumber6())
                .number7(lottoResultVO.getNumber7())
                .totalSalesPrice(lottoResultVO.getTotalSalesPrice())
                .autoWinnerCount(lottoResultVO.getAutoWinnerCount())
                .semiAutoWinnerCount(lottoResultVO.getSemiAutoWinnerCount())
                .manualWinnerCount(lottoResultVO.getManualWinnerCount())
                .build();
    }

}

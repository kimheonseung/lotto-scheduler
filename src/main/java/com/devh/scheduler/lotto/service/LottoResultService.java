package com.devh.scheduler.lotto.service;


import com.devh.scheduler.lotto.dto.LottoResultDTO;
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
    Boolean saveDTO(LottoResultDTO lottoResultDTO);
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
    default LottoResult dtoToEntity(LottoResultDTO lottoResultDTO) {
        return LottoResult.builder()
                .turn(lottoResultDTO.getTurn())
                .date(lottoResultDTO.getDate())
                .number1(lottoResultDTO.getNumber1())
                .number2(lottoResultDTO.getNumber2())
                .number3(lottoResultDTO.getNumber3())
                .number4(lottoResultDTO.getNumber4())
                .number5(lottoResultDTO.getNumber5())
                .number6(lottoResultDTO.getNumber6())
                .number7(lottoResultDTO.getNumber7())
                .totalSalesPrice(lottoResultDTO.getTotalSalesPrice())
                .autoWinnerCount(lottoResultDTO.getAutoWinnerCount())
                .semiAutoWinnerCount(lottoResultDTO.getSemiAutoWinnerCount())
                .manualWinnerCount(lottoResultDTO.getManualWinnerCount())
                .build();
    }

}

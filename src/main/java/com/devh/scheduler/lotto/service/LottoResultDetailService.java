package com.devh.scheduler.lotto.service;

import com.devh.scheduler.lotto.vo.LottoResultDetailVO;
import com.devh.scheduler.lotto.entity.LottoResult;
import com.devh.scheduler.lotto.entity.LottoResultDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Description :
 *     LottoResultDetail 엔티티 관련 인테퍼이스
 * ===============================================
 * Member fields :
 *     Nothing
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
public interface LottoResultDetailService {

    /**
     * <pre>
     * Description
     *     순위 1~5의 LottoResultDetailDTO 리스트를 테이블에 insert
     * ===============================================
     * Parameters
     *     List<LottoResultDetailDTO> lottoResultDetailDTOList
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
    Boolean saveVOList(List<LottoResultDetailVO> lottoResultDetailVOList);

    /**
     * <pre>
     * Description
     *     LottoResultDetailDTO -> LottoResultDetail 변환
     *     lottoResult의 경우 DTO에서 turn 정보를 가져와 turn만 세팅하여 만들면,
     *     쿼리 수행 시 해당 값으로만 외래키 참조된 정보를 사용하므로 상관이 없다.
     * ===============================================
     * Parameters
     *     LottoResultDetailDTO lottoResultDetailDTO
     * Returns
     *     LottoResultDetail
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    default LottoResultDetail voToEntity(LottoResultDetailVO lottoResultDetailVO) {
        return LottoResultDetail.builder()
                .rank(lottoResultDetailVO.getRank())
                .totalPrize(lottoResultDetailVO.getTotalPrize())
                .perPersonPrize(lottoResultDetailVO.getPerPersonPrize())
                .totalWinnerCount(lottoResultDetailVO.getTotalWinnerCount())
                .lottoResult(LottoResult.builder().turn(lottoResultDetailVO.getTurn()).build())
                .build();
    }

    /**
     * <pre>
     * Description
     *     순위 1~5의 LottoResultDetailDTO 리스트를 LottoResultDetail 리스트로 변환
     *     동일 인터페이스 내의 dtoToEntity 이용
     * ===============================================
     * Parameters
     *     List<LottoResultDetailDTO> lottoResultDetailDTOList
     * Returns
     *     List<LottoResultDetail>
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    default List<LottoResultDetail> voListToEntityList(List<LottoResultDetailVO> lottoResultDetailVOList) {
        List<LottoResultDetail> lottoResultDetailList = new ArrayList<>();

        lottoResultDetailVOList.forEach(lottoResultDetailVO -> {
            lottoResultDetailList.add(voToEntity(lottoResultDetailVO));
        });

        return lottoResultDetailList;
    }
}

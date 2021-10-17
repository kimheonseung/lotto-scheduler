package com.devh.scheduler.lotto.service;

import com.devh.scheduler.lotto.dto.LottoResultDetailDTO;
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
    Boolean saveDTOList(List<LottoResultDetailDTO> lottoResultDetailDTOList);

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
    default LottoResultDetail dtoToEntity(LottoResultDetailDTO lottoResultDetailDTO) {
        return LottoResultDetail.builder()
                .rank(lottoResultDetailDTO.getRank())
                .totalPrize(lottoResultDetailDTO.getTotalPrize())
                .perPersonPrize(lottoResultDetailDTO.getPerPersonPrize())
                .totalWinnerCount(lottoResultDetailDTO.getTotalWinnerCount())
                .lottoResult(LottoResult.builder().turn(lottoResultDetailDTO.getTurn()).build())
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
    default List<LottoResultDetail> dtoListToEntityList(List<LottoResultDetailDTO> lottoResultDetailDTOList) {
        List<LottoResultDetail> lottoResultDetailList = new ArrayList<>();

        lottoResultDetailDTOList.forEach(lottoResultDetailDTO -> {
            lottoResultDetailList.add(dtoToEntity(lottoResultDetailDTO));
        });

        return lottoResultDetailList;
    }
}

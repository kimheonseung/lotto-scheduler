package com.devh.scheduler.lotto.service;

import com.devh.scheduler.lotto.dto.LottoResultDetailDTO;
import com.devh.scheduler.lotto.entity.LottoResultDetail;
import com.devh.scheduler.lotto.repository.LottoResultDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * Description :
 *     LottoResultDetailService 구현체
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
@Service
@RequiredArgsConstructor
public class LottoResultDetailServiceImpl implements LottoResultDetailService {
    /* DI */
    private final LottoResultDetailRepository lottoResultDetailRepository;

    @Override
    public Boolean saveDTOList(List<LottoResultDetailDTO> lottoResultDetailDTOList) {
        List<LottoResultDetail> lottoResultDetailList = dtoListToEntityList(lottoResultDetailDTOList);
        List<LottoResultDetail> saveResultList = lottoResultDetailRepository.saveAll(lottoResultDetailList);
        return saveResultList.size() == lottoResultDetailDTOList.size();
    }
}

package com.devh.scheduler.lotto.service;

import com.devh.scheduler.lotto.vo.LottoResultVO;
import com.devh.scheduler.lotto.entity.LottoResult;
import com.devh.scheduler.lotto.repository.LottoResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Description :
 *     LottoResultService 구현체
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
public class LottoResultServiceImpl implements LottoResultService {
    /* DI */
    private final LottoResultRepository lottoResultRepository;

    @Override
    public Boolean saveVO(LottoResultVO lottoResultVO) {
        LottoResult lottoResult = lottoResultRepository.save(voToEntity(lottoResultVO));
        return lottoResult.getTurn().intValue() == lottoResultVO.getTurn().intValue();
    }

    @Override
    public Integer getLatestTurn() {
        LottoResult lottoResult = lottoResultRepository.findFirstByOrderByTurnDesc();
        return lottoResult == null ? 0 : lottoResult.getTurn();
    }
}

package com.devh.scheduler.lotto.service;

import com.devh.scheduler.lotto.entity.LottoWinningStore;
import com.devh.scheduler.lotto.repository.LottoWinningStoreRepository;
import com.devh.scheduler.lotto.vo.LottoWinningStoreVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LottoWinningStoreServiceImpl implements LottoWinningStoreService {

    /* DI */
    private final LottoWinningStoreRepository lottoWinningStoreRepository;

    @Override
    public boolean saveAll(List<LottoWinningStoreVO> lottoWinningStoreVOList) {
        List<LottoWinningStore> lottoWinningStoreList = voListToEntity(lottoWinningStoreVOList);
        List<LottoWinningStore> savedList = lottoWinningStoreRepository.saveAll(lottoWinningStoreList);
        if(savedList.size() == lottoWinningStoreList.size())
            return true;
        else
            return false;
    }
}

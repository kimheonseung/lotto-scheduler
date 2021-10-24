package com.devh.scheduler.lotto.service;

import com.devh.scheduler.lotto.entity.LottoResult;
import com.devh.scheduler.lotto.entity.LottoWinningStore;
import com.devh.scheduler.lotto.vo.LottoWinningStoreVO;

import java.util.ArrayList;
import java.util.List;

public interface LottoWinningStoreService {
    boolean saveAll(List<LottoWinningStoreVO> lottoWinningStoreVOList);

    default LottoWinningStore voToEntity(LottoWinningStoreVO lottoWinningStoreVO) {
        return LottoWinningStore.builder()
                .rowId(lottoWinningStoreVO.getRowId())
                .lottoResult(LottoResult.builder().turn(lottoWinningStoreVO.getTurn()).build())
                .rank(lottoWinningStoreVO.getRank())
                .method(lottoWinningStoreVO.getMethod())
                .id(lottoWinningStoreVO.getId())
                .name(lottoWinningStoreVO.getName())
                .address(lottoWinningStoreVO.getAddress())
                .addressDivision1(lottoWinningStoreVO.getAddressDivision1())
                .addressDivision2(lottoWinningStoreVO.getAddressDivision2())
                .addressDivision3(lottoWinningStoreVO.getAddressDivision3())
                .latitude(lottoWinningStoreVO.getLatitude())
                .longitude(lottoWinningStoreVO.getLongitude())
                .phone(lottoWinningStoreVO.getPhone())
                .mapId(lottoWinningStoreVO.getMapId())
                .build();
    }

    default List<LottoWinningStore> voListToEntity(List<LottoWinningStoreVO> lottoWinningStoreVOList) {

        List<LottoWinningStore> lottoWinningStoreList = new ArrayList<>();
        lottoWinningStoreVOList.forEach(lottoWinningStoreVO -> {
            lottoWinningStoreList.add(voToEntity(lottoWinningStoreVO));
        });

        return lottoWinningStoreList;
    }
}

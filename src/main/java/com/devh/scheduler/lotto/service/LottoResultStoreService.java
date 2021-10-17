package com.devh.scheduler.lotto.service;

import com.devh.scheduler.lotto.dto.LottoResultStoreDTO;

import java.util.*;

/**
 * <pre>
 * Description :
 *     LottoResultStore 엔티티 관련 인터페이스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021/03/12
 * </pre>
 */
public interface LottoResultStoreService {

    boolean bulkLottoResultStoreDTOList(List<LottoResultStoreDTO> lottoResultStoreDTOList);

}

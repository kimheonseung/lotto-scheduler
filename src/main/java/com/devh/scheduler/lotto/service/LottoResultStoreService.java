package com.devh.scheduler.lotto.service;

import com.devh.scheduler.lotto.vo.LottoResultStoreVO;

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
@Deprecated
public interface LottoResultStoreService {

    boolean bulkLottoResultStoreVOList(List<LottoResultStoreVO> lottoResultStoreVOList);

}

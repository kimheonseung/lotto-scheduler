package com.devh.scheduler.lotto.repository;

import com.devh.scheduler.lotto.entity.LottoResult;
import com.devh.scheduler.lotto.projection.LottoResultNumberArrayProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <pre>
 * Description :
 *     LottoResult 엔티티와 대응되는 쿼리 수행 인터페이스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
public interface LottoResultRepository extends JpaRepository<LottoResult, Integer> {
    /**
     * <pre>
     * Description
     *     DB에 저장된 최신 회차를 조회
     * ===============================================
     * Parameters
     *
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
    LottoResult findFirstByOrderByTurnDesc();

}

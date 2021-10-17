package com.devh.scheduler.lotto.repository;

import com.devh.scheduler.lotto.entity.LottoResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <pre>
 * Description :
 *     LottoResultRepository 엔티티와 대응되는 쿼리 수행 인터페이스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
public interface LottoResultDetailRepository extends JpaRepository<LottoResultDetail, Long> {
}

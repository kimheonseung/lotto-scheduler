package com.devh.scheduler.lotto.repository;


import com.devh.scheduler.lotto.entity.LottoResultStore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LottoResultStoreRepository extends ElasticsearchRepository<LottoResultStore, String> {
}

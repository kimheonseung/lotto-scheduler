package com.devh.scheduler.lotto.service;

import com.devh.common.util.ExceptionUtils;
import com.devh.scheduler.lotto.constant.LottoIndex;
import com.devh.scheduler.lotto.vo.LottoResultStoreVO;
import lombok.RequiredArgsConstructor;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
@Deprecated
@RequiredArgsConstructor
public class LottoResultStoreServiceImpl implements LottoResultStoreService {
    /* DI */
//    private final RestHighLevelClient restHighLevelClient;
//    private final ElasticsearchOperations elasticsearchOperations;

    private final Logger logger = LoggerFactory.getLogger(LottoResultStoreServiceImpl.class);

    @Override
    public boolean bulkLottoResultStoreVOList(List<LottoResultStoreVO> lottoResultStoreVOList) {
//        final String indexName = "micro_lotto_store";
        boolean result = false;

//        try {
//            BulkRequest bulkRequest = new BulkRequest();
//
//            lottoResultStoreVOList.forEach(lottoResultStoreVO -> {
////                logger.info(lottoResultStoreVO.toString());
//                IndexRequest indexRequest = new IndexRequest(LottoIndex.INDEX_NAME_PRE.getValue());
//                indexRequest.id(lottoResultStoreVO.getRowId()).source(lottoResultStoreVO.getMapForElasticsearch());
//                bulkRequest.add(indexRequest);
//            });
//
//            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
//
//            if(bulkResponse.hasFailures()) {
//                logger.error(bulkResponse.buildFailureMessage());
//                result = false;
//            } else {
//                logger.info(String.format("Bulk Success. [%d docs, %f seconds]",
//                        bulkResponse.getItems().length,
//                        (bulkResponse.getTook().millis() / (double) 1000)));
//                result = true;
//            }
//
//        } catch (Exception e) {
//            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
//            result = false;
//        }

        return result;
    }
}

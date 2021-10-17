package com.devh.scheduler.lotto.configuration;

import com.devh.scheduler.lotto.constant.LottoIndex;
import com.devh.scheduler.lotto.entity.LottoResultStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.index.AliasActionParameters;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.data.elasticsearch.core.index.AliasActions;
import org.springframework.data.elasticsearch.core.index.PutTemplateRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * <pre>
 * Description :
 *     Elasticsearch 템플릿 설정 관련 클래스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021/03/11
 * </pre>
 */
@Component
public class TemplateInitializer {

    private final Logger logger = LoggerFactory.getLogger(TemplateInitializer.class);
    private final ElasticsearchOperations operations;

    public TemplateInitializer(@Qualifier("elasticsearchOperations") ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Autowired
    public void setup() {
        setupLotto();
    }
    /**
     * <pre>
     * Description
     *     lotto_result_store index template
     * ===============================================
     * Parameters
     *
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-03-22
     * </pre>
     */
    private void setupLotto() {
        var indexOps = operations.indexOps(LottoResultStore.class);

        if(!indexOps.existsTemplate(LottoIndex.TEMPLATE_ALIAS.getValue())) {
            logger.info("Template for " + LottoIndex.TEMPLATE_ALIAS.getValue() + " does not exist... creating tempates...");

            var mapping = indexOps.createMapping();

            var aliasActions = new AliasActions().add(
                    new AliasAction.Add(AliasActionParameters.builderForTemplate()
                            .withAliases(LottoIndex.TEMPLATE_ALIAS.getValue())
                            .build())
            );

            var request = PutTemplateRequest.builder(LottoIndex.TEMPLATE_ALIAS.getValue(), LottoIndex.PATTERN.getValue())
                    .withMappings(mapping)
                    .withAliasActions(aliasActions)
                    .build();

            logger.info("Complete to create template... alias : " + LottoIndex.TEMPLATE_ALIAS.getValue() + ", index patterns : " + Arrays.toString(request.getIndexPatterns()));
            logger.info(Objects.requireNonNull(request.getMappings().toJson()));

            indexOps.putTemplate(request);

        }
    }
}
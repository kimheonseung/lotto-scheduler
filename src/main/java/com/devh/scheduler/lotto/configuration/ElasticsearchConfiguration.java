package com.devh.scheduler.lotto.configuration;

//import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.Arrays;

/**
 * <pre>
 * Description :
 *     Elasticsearch 관련 설정 클래스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-03-11
 * </pre>
 */
//@Configuration
//@EnableElasticsearchRepositories(basePackages = {"com.devh.scheduler.lotto.repository"})
@Deprecated
public class ElasticsearchConfiguration /* extends AbstractElasticsearchConfiguration */ {
//    @Value("${elasticsearch.ip}")
//    private String IP;
//    @Value("${elasticsearch.port}")
//    private String PORT;

//    @Override
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo(IP + ":" + PORT).build();
//        return RestClients.create(clientConfiguration).rest();
//    }
}
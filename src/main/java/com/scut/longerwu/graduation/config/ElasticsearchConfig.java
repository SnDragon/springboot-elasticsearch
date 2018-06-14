package com.scut.longerwu.graduation.config;

import org.elasticsearch.client.transport.*;
import org.elasticsearch.common.settings.*;
import org.elasticsearch.common.transport.*;
import org.elasticsearch.transport.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import java.net.*;

/**
 *  Elasticsearch客户端Bean配置
 */
@Configuration
public class ElasticsearchConfig {

    @Value("${es.host}")
    private String esHost;

    @Value("${es.port}")
    private Integer esPort;

    @Bean
    public TransportClient client() throws UnknownHostException {
//        Settings settings = Settings.builder()
//                .put("cluster.name", "myClusterName").build();
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
        return client;
    }

}

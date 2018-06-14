package com.scut.longerwu.graduation.config;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * MongoDB客户端Bean配置
 */
@Configuration
public class MongoClientConfig {
    private static final String LOCALHOST="localhost";
    @Value("${mongo.host}")
    private String HOST;

    @Value("${mongo.port}")
    private Integer PORT;

    @Bean
    public MongoClient mongoClient(){
        MongoClient client=new MongoClient(HOST,PORT);
        return client;
    }
}

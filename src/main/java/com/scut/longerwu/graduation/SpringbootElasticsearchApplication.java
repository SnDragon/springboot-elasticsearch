package com.scut.longerwu.graduation;

import org.apache.log4j.*;
import org.elasticsearch.client.transport.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@EnableScheduling
public class SpringbootElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootElasticsearchApplication.class, args);
    }

    @Autowired
    private TransportClient client;

    private Logger logger = LogManager.getLogger(SpringbootElasticsearchApplication.class);


    @GetMapping("/")
    public String index() {
        return "index";
    }


}

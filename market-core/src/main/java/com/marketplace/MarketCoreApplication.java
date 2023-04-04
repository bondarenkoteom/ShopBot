package com.marketplace;

import com.marketplace.config.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.marketplace")
@EnableAsync
@EnableScheduling
public class MarketCoreApplication {



    @Autowired
    private RabbitConfig rabbitConfig;

    public static void main(String[] args) {
        SpringApplication.run(MarketCoreApplication.class, args);
    }

}

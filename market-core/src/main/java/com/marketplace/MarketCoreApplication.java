package com.marketplace;

import com.marketplace.config.RabbitConfig;
//import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import jakarta.annotation.PostConstruct;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.marketplace")
//@ServletComponentScan
@EnableScheduling
public class MarketCoreApplication {

//    @Autowired
//    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitConfig rabbitConfig;

    public static void main(String[] args) {
        SpringApplication.run(MarketCoreApplication.class, args);
    }

//    @PostConstruct
//    public void setupQueueDestinations() {
//        rabbitConfig.getQueues()
//                .forEach((key, destination) -> {
//                    Exchange ex = ExchangeBuilder.directExchange(
//                                    destination.getExchange())
//                            .durable(true)
//                            .build();
//                    amqpAdmin.declareExchange(ex);
//                    Queue q = QueueBuilder.durable(
//                                    destination.getRoutingKey())
//                            .build();
//                    amqpAdmin.declareQueue(q);
//                    Binding b = BindingBuilder.bind(q)
//                            .to(ex)
//                            .with(destination.getRoutingKey())
//                            .noargs();
//                    amqpAdmin.declareBinding(b);
//                });
//    }
}

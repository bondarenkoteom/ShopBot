package com.marketplace.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("destinations")
public class RabbitConfig {

    //    @Autowired
    //    private AmqpAdmin amqpAdmin;

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


    private Map<String, DestinationInfo> queues = new HashMap<>();

    private Map<String, DestinationInfo> topics = new HashMap<>();


    public Map<String, DestinationInfo> getQueues() {
        return queues;
    }

    public void setQueues(Map<String, DestinationInfo> queues) {
        this.queues = queues;
    }

    public Map<String, DestinationInfo> getTopics() {
        return topics;
    }

    public void setTopics(Map<String, DestinationInfo> topics) {
        this.topics = topics;
    }

    // DestinationInfo stores the Exchange name and routing key used
    // by our producers when posting messages
    public static class DestinationInfo {

        private String exchange;
        private String routingKey;


        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }

    }

}
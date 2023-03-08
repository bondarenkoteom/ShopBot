package com.marketplace.quque;

//import com.marketplace.config.RabbitConfig;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

//@RestController
public class ProducerController {

//    @Autowired
//    private AmqpTemplate amqpTemplate;
//
//    @Autowired
//    private RabbitConfig destinationsConfig;
//
//    @PostMapping(value = "/queue/{name}")
//    public Mono<ResponseEntity<?>> sendMessageToQueue(
//            @PathVariable String name, @RequestBody String payload) {
//
//        RabbitConfig.DestinationInfo d = destinationsConfig
//                .getQueues().get(name);
//        if (d == null) {
//            return Mono.just(
//                    ResponseEntity.notFound().build());
//        }
//
//        return Mono.fromCallable(() -> {
//            amqpTemplate.convertAndSend(
//                    d.getExchange(),
//                    d.getRoutingKey(),
//                    payload);
//            return ResponseEntity.accepted().build();
//        });
//    }
}

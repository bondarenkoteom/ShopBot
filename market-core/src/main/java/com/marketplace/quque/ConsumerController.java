package com.marketplace.quque;

import com.marketplace.config.RabbitConfig;
//import org.springframework.amqp.core.MessageListener;
//import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

//@RestController
public class ConsumerController {

//    @Autowired
//    private MessageListenerContainerFactory messageListenerContainerFactory;
//
//    @Autowired
//    private RabbitConfig destinationsConfig;
//
//    @GetMapping(
//            value = "/queue/{name}",
//            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<?> receiveMessagesFromQueue(@PathVariable String name) {
//
//        RabbitConfig.DestinationInfo d = destinationsConfig
//                .getQueues()
//                .get(name);
//        if (d == null) {
//            return Flux.just(ResponseEntity.notFound()
//                    .build());
//        }
//
//        MessageListenerContainer mlc = messageListenerContainerFactory
//                .createMessageListenerContainer(d.getRoutingKey());
//
//        Flux<String> f = Flux.<String> create(emitter -> {
//            mlc.setupMessageListener((MessageListener) m -> {
//                String payload = new String(m.getBody());
//                emitter.next(payload);
//            });
//            emitter.onRequest(v -> {
//                mlc.start();
//            });
//            emitter.onDispose(() -> {
//                mlc.stop();
//            });
//        });
//
//        return Flux.interval(Duration.ofSeconds(5))
//                .map(v -> "No news is good news")
//                .mergeWith(f);
//    }
}

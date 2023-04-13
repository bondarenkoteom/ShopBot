package com.marketplace.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.database.model.DisputeMessage;
import com.marketplace.database.repository.r2dbc.DisputeMessageRepository;
import io.r2dbc.postgresql.api.PostgresqlConnection;
import io.r2dbc.postgresql.api.PostgresqlResult;
import io.r2dbc.spi.ConnectionFactory;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class DisputeEventService implements EventService<DisputeMessage> {

    private final Map<String, Sinks.Many<DisputeMessage>> sinks = new HashMap<>();

    PostgresqlConnection receiver;

    ObjectMapper objectMapper = new ObjectMapper();

    private Date lastUpdate;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private DisputeMessageRepository disputeMessageRepository;

    @Override
    public void onNext(DisputeMessage next) {
        sinks.forEach((key, value) -> value.tryEmitNext(next));
    }

//    @Override
//    public Flux<DisputeMessage> getMessages(String session) {
//        return sinks.get(session).asFlux().log()
//                .doOnCancel(() -> sinks.remove(session));
//    }

    @Override
    public Flux<DisputeMessage> getMessages(String session) {
        return receiver.getNotifications()
                .delayElements(Duration.ofMillis(100))
                .log()
                .map(notification -> {
                    System.out.println("received notification: " +  notification);
                    try {
                        return objectMapper.readValue(notification.getParameter(), DisputeMessage.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void onStart(String session) {
        sinks.putIfAbsent(session, Sinks.many().multicast().onBackpressureBuffer());
        disputeMessageRepository.findByChannelId(1L).subscribe(this::onNext);
        lastUpdate = new Date();
    }

//    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
//    public void disputeMessagesScheduler() {
//        if (lastUpdate != null) {
//            disputeMessageRepository.findByChannelIdAndDateAfter(1L, lastUpdate)
//                    .subscribe(this::onNext);
//            lastUpdate = new Date();
//        }
//    }

//    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
//    public void disputeMessagesScheduler() {
//        receiver.getNotifications()
//                .log()
//                .map(notification -> {
//                    System.out.println("received notification: " +  notification);
//                    try {
//                        return objectMapper.readValue(notification.getParameter(), DisputeMessage.class);
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    }
//                }).subscribe(this::onNext);
//    }

    @PostConstruct
    public void initialize() throws InterruptedException {
        receiver = Mono.from(connectionFactory.create())
                .cast(PostgresqlConnection.class)
                .block();

        receiver.createStatement("LISTEN dispute_message")
                .execute()
                .flatMap(PostgresqlResult::getRowsUpdated)
                .log("listen::")
                .subscribe();

    }

    @PreDestroy
    public void destroy() {
        System.out.println("DESTROOOOOOY!!!!!");
        receiver.close().subscribe();
    }

}

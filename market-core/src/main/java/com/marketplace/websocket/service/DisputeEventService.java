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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DisputeEventService implements EventService<DisputeMessage> {

    private final Map<String, Sinks.Many<DisputeMessage>> sinks = new HashMap<>();

    private final ObjectMapper localDateTimeObjectMapper;

    private final DisputeMessageRepository disputeMessageRepository;

    private final ConnectionFactory connectionFactory;

    private PostgresqlConnection receiver;

    @Override
    public void onNext(DisputeMessage next) {
        sinks.forEach((key, value) -> value.tryEmitNext(next));
    }

    @Override
    public Flux<DisputeMessage> getMessages(String session) {
        return sinks.get(session).asFlux().log()
                .doOnCancel(() -> sinks.remove(session));
    }

    @Override
    public void onStart(String session) {
        sinks.putIfAbsent(session, Sinks.many().multicast().onBackpressureBuffer());
        disputeMessageRepository.findByChannelId(1L).subscribe(this::onNext);
    }

    @PostConstruct
    public void initialize() {
        receiver = Mono.from(connectionFactory.create())
                .cast(PostgresqlConnection.class)
                .block();

        receiver.createStatement("LISTEN dispute_channel")
                .execute()
                .flatMap(PostgresqlResult::getRowsUpdated)
                .log("listen::")
                .subscribe();

        receiver.getNotifications()
                .delayElements(Duration.ofMillis(100))
                .log()
                .map(notification -> {
                    System.out.println("received notification: " +  notification);
                    try {
                        return localDateTimeObjectMapper.readValue(notification.getParameter(), DisputeMessage.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).subscribe(this::onNext);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("DESTROY dispute_channel");
        receiver.close().subscribe();
    }

}

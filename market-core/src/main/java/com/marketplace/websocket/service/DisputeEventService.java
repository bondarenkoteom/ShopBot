package com.marketplace.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.database.r2dbc.model.DisputeChannel;
import com.marketplace.database.r2dbc.model.DisputeMessage;
import com.marketplace.database.r2dbc.repository.DisputeChannelRepository;
import com.marketplace.database.r2dbc.repository.DisputeMessageRepository;
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
public class DisputeEventService implements EventService<Object> {

    private final Map<String, Sinks.Many<Object>> sinks = new HashMap<>();

    private final ObjectMapper localDateTimeObjectMapper;

    private final DisputeMessageRepository disputeMessageRepository;

    private final DisputeChannelRepository disputeChannelRepository;

    private final ConnectionFactory connectionFactory;

    private PostgresqlConnection message_receiver;
    private PostgresqlConnection channel_receiver;

    @Override
    public void onNext(Object next) {
        sinks.forEach((key, value) -> value.tryEmitNext(next));
    }

    @Override
    public Flux<Object> getMessages(String session) {
        return sinks.get(session).asFlux()
                .doOnCancel(() -> {
                    System.out.println("EEEEEEEEEEEEEEEEEEE!!!!!!!!!!RRRRRRRRRR");
                    sinks.remove(session);
                });
    }

    @Override
    public void onStart(String session) {
        sinks.putIfAbsent(session, Sinks.many().multicast().onBackpressureBuffer());
        disputeChannelRepository.findAll().log()
                .subscribe(channel -> sinks.get(session).emitNext(channel, Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(2))));
        disputeMessageRepository.findAll().log()
                .subscribe(message -> sinks.get(session).emitNext(message, Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(2))));
    }

    @PostConstruct
    public void initialize() {
        channel_receiver = Mono.from(connectionFactory.create())
                .cast(PostgresqlConnection.class)
                .block();

        channel_receiver.createStatement("LISTEN dispute_channel_notify")
                .execute()
                .flatMap(PostgresqlResult::getRowsUpdated)
                .log("listen::")
                .subscribe();

        channel_receiver.getNotifications()
                .log()
                .map(notification -> {
                    System.out.println("received notification: " +  notification);
                    try {
                        return localDateTimeObjectMapper.readValue(notification.getParameter(), DisputeChannel.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).subscribe(this::onNext);

        message_receiver = Mono.from(connectionFactory.create())
                .cast(PostgresqlConnection.class)
                .block();

        message_receiver.createStatement("LISTEN dispute_message_notify")
                .execute()
                .flatMap(PostgresqlResult::getRowsUpdated)
                .log("listen::")
                .subscribe();

        message_receiver.getNotifications()
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
        channel_receiver.close().subscribe();
        message_receiver.close().subscribe();
    }

}

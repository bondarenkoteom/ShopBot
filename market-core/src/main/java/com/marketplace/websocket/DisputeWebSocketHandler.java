package com.marketplace.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.marketplace.database.model.DisputeMessage;
import com.marketplace.database.repository.r2dbc.DisputeMessageRepository;
import com.marketplace.websocket.service.DisputeEventService;
import com.marketplace.websocket.service.EventService;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DisputeWebSocketHandler implements WebSocketHandler {

    private final DisputeEventService eventService;

    private final ObjectMapper objectMapper;

    private final DisputeMessageRepository disputeMessageRepository;

    @Autowired
    public DisputeWebSocketHandler(DisputeEventService eventService,
                                   DisputeMessageRepository disputeMessageRepository) {
        this.eventService = eventService;
        this.disputeMessageRepository = disputeMessageRepository;

        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        eventService.onStart(session.getId());
        return session.send(eventService.getMessages(session.getId())
                        .map(message -> session.textMessage(conwertToString(message))))
                .and(session.receive().map(WebSocketMessage::getPayloadAsText)
                        .flatMap(payload -> disputeMessageRepository.save(convertToMessage(payload))).then()
                );
    }

    String conwertToString(DisputeMessage disputeMessage) {
        try {
            return objectMapper.writeValueAsString(disputeMessage);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    //todo refactor
    DisputeMessage convertToMessage(String message) {
        try {
            DisputeMessage result;
            result = objectMapper.readValue(message, DisputeMessage.class);
            result.setId(null);
            result.setDate(LocalDateTime.now());
            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

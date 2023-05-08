package com.marketplace.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.database.model.DisputeMessage;
import com.marketplace.database.repository.r2dbc.DisputeMessageRepository;
import com.marketplace.websocket.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DisputeWebSocketHandler implements WebSocketHandler {

    private final EventService<Object> eventService;

    private final ObjectMapper localDateTimeObjectMapper;

    private final DisputeMessageRepository disputeMessageRepository;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        eventService.onStart(session.getId());
        return session.send(eventService.getMessages(session.getId())
                        .map(message -> session.textMessage(conwertToString(message))))
                .and(session.receive().map(WebSocketMessage::getPayloadAsText)
                        .flatMap(payload -> disputeMessageRepository.save(convertToMessage(payload))).then()
                );
    }

    String conwertToString(Object o) {
        try {
            return localDateTimeObjectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    //todo refactor
    DisputeMessage convertToMessage(String message) {
        try {
            DisputeMessage result;
            result = localDateTimeObjectMapper.readValue(message, DisputeMessage.class);
            result.setId(null);
            result.setDate(LocalDateTime.now());
            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

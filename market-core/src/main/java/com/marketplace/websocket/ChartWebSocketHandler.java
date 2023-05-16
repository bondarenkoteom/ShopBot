package com.marketplace.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.database.r2dbc.model.ChartData;
import com.marketplace.websocket.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ChartWebSocketHandler implements WebSocketHandler {

    private final EventService<ChartData> eventService;

    private final ObjectMapper localDateTimeObjectMapper;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        eventService.onStart(session.getId());
        Flux<WebSocketMessage> messages = session.receive()
                .flatMap(message -> eventService.getMessages(session.getId()))
                .flatMap(o -> {
                    try {
                        return Mono.just(localDateTimeObjectMapper.writeValueAsString(o));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(session::textMessage);
        return session.send(messages);
    }
}

package com.marketplace.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.database.model.DisputeChannel;
import com.marketplace.database.model.DisputeMessage;
import com.marketplace.database.repository.r2dbc.DisputeChannelRepository;
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
    private final DisputeChannelRepository disputeChannelRepository;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        eventService.onStart(session.getId());
        return session.send(eventService.getMessages(session.getId())
                        .map(message -> {
                            if (message instanceof DisputeMessage) {
                                return session.textMessage(serialize(new WebsocketMessage("message", message)));
                            } else {
                                return session.textMessage(serialize(new WebsocketMessage("channel", message)));
                            }
                        }))
                .and(session.receive().map(WebSocketMessage::getPayloadAsText)
                        .flatMap(payload -> {
                            WebsocketMessage message = deserialize(payload, WebsocketMessage.class);
                            if (message.getType().equals("message")) {
                                DisputeMessage disputeMessage = deserialize(message.getObject(), DisputeMessage.class);
                                disputeMessage.setId(null);
                                disputeMessage.setDate(LocalDateTime.now());
                                return disputeMessageRepository.save(disputeMessage);
                            } else {
                                return disputeChannelRepository.save(deserialize(message.getObject(), DisputeChannel.class));
                            }
                        }).then()
                );
    }

    String serialize(Object o) {
        try {
            return localDateTimeObjectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    <T> T deserialize(Object message, Class<T> cls) {
        try {
            if (message instanceof String) {
                return localDateTimeObjectMapper.readValue((String) message, cls);
            } else {
                return localDateTimeObjectMapper.readValue(localDateTimeObjectMapper.writeValueAsString(message), cls);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.marketplace.websocket.service;

import com.marketplace.websocket.model.Event;
import reactor.core.publisher.Flux;

public interface EventUnicastService {

    void onNext(Event next);

    Flux<Event> getMessages();
}

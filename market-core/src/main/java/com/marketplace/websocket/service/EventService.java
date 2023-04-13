package com.marketplace.websocket.service;

import reactor.core.publisher.Flux;

public interface EventService<T> {

    void onNext(T next);

    Flux<T> getMessages(String session);

    void onStart(String session);
}

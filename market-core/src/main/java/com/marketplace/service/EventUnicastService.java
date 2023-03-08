package com.marketplace.service;

import com.marketplace.model.Event;
import reactor.core.publisher.Flux;

public interface EventUnicastService {

    void onNext(Event next);

    Flux<Event> getMessages();
}

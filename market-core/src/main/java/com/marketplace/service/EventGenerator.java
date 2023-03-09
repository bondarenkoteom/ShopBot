package com.marketplace.service;

import com.marketplace.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EventGenerator {

    private AtomicInteger counter = new AtomicInteger(0);

    private EventUnicastService eventUnicastService;

    @Autowired
    public EventGenerator(EventUnicastService eventUnicastService) {
        this.eventUnicastService = eventUnicastService;
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 2000)
    public void generateEvent() {
        int count = counter.getAndIncrement();

        Integer[] numbers = new Integer[7];
        for(int i = 0; i < numbers.length; i++) {
            numbers[i] = (int)(Math.random()*20 + 1);
        }

        Event event = new Event(Arrays.asList(numbers));
        eventUnicastService.onNext(event);
    }
}

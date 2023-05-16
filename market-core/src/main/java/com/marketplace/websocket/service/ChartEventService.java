package com.marketplace.websocket.service;

import com.marketplace.database.r2dbc.model.ChartData;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChartEventService implements EventService<ChartData> {

    private final Map<String, Sinks.Many<ChartData>> sinks = new HashMap<>();

    @Override
    public void onNext(ChartData next) {
        sinks.forEach((key, value) -> value.tryEmitNext(next));
    }

    @Override
    public Flux<ChartData> getMessages(String session) {
        return sinks.get(session).asFlux().log()
                .doOnCancel(() -> sinks.remove(session));
    }

    @Override
    public void onStart(String session) {
        sinks.putIfAbsent(session, Sinks.many().multicast().onBackpressureBuffer());
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 2000)
    public void generateChartData() {
        Integer[] numbers = new Integer[7];
        for(int i = 0; i < numbers.length; i++) {
            numbers[i] = (int)(Math.random()*20 + 1);
        }

        ChartData event = new ChartData(Arrays.asList(numbers));
        onNext(event);
    }
}

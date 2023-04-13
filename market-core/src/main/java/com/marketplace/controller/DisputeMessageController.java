package com.marketplace.controller;

import com.marketplace.database.model.DisputeMessage;
import com.marketplace.database.repository.r2dbc.DisputeMessageRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class DisputeMessageController {

    @Autowired
    private DisputeMessageRepository disputeMessageRepository;


    @RequestMapping(value = "/api/v1/chat", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<DisputeMessage> postChat(@Valid @RequestBody DisputeMessage disputeMessage) {
        return disputeMessageRepository.save(disputeMessage);
    }

    @RequestMapping(value = "/api/v1/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE,  method = RequestMethod.GET)
    public Flux<String> streamMessages(@RequestParam Long channelId){
        return Flux.interval(Duration.ofSeconds(1)).map(it -> {
            return String.valueOf(Math.random());
        });
    }
}

package com.marketplace.websocket.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Event {

    private List<Integer> data;

    public Event(List<Integer> data) {
        this.data = data;
    }

}

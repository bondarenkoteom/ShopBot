package com.marketplace.entity;

import lombok.Data;

@Data
public class DisputeChannel {
    Long id;
    String name;
    String description;
    Integer unread;
}

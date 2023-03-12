package com.marketplace.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    Long id;
    String text;
    User sender;
    User receiver;
    Date date;
}

package com.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DisputeMessage {
    Long id;
    String message;
    String sender;
    String receiver;
    Long channelId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    LocalDateTime date;
}

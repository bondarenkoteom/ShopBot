package com.marketplace.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(value = "dispute_message")
public class DisputeMessage {

    @Id
    private Long id;

    @Column("message")
    private String message;

    @Column("sender")
    private String sender;

    @Column("receiver")
    private String receiver;

    @Column("channel_id")
    private Long channelId;

//    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")//dd MMMM, HH:mm
    @Column("date")
    private LocalDateTime date;

}

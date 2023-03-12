package com.marketplace.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class Dispute {
    Long id;
    String text;
    User sender;
    Date date;
    Long purchaseId;
}

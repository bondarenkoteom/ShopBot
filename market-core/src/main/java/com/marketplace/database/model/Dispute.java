package com.marketplace.database.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_dispute")
public class Dispute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @Column(name = "sender_id")
    private Long sender;

    @Column
    private Date date;

    @Column(name = "purchase_id")
    private Long purchaseId;
}

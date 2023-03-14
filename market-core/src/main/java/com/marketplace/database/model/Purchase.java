package com.marketplace.database.model;

import com.marketplace.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "item")
    private String item;

    @Column(name = "name")
    private String name;

    @Column(name = "instruction")
    private String instruction;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column
    private Date date;
}

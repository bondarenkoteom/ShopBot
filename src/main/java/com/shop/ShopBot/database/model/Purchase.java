package com.shop.ShopBot.database.model;

import com.shop.ShopBot.constant.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    private User buyer;

    @ManyToOne
    private User seller;

    @Column
    private Date date;
}

package com.shop.ShopBot.database.model;

import com.shop.ShopBot.constant.OrderStatus;
import com.shop.ShopBot.constant.ProductStatus;
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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    private User owner;

    @ManyToOne
    private User seller;

    @Column
    private Date date;
}

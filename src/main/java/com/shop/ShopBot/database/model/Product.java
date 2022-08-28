package com.shop.ShopBot.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ownerId")
    private Long ownerId;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "productName")
    private String productName;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] bytea;

}

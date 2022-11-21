package com.shop.ShopBot.database.model;

import com.shop.ShopBot.constant.ProductStatus;
import com.shop.ShopBot.utils.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "image_id")
    private String imageId;

//    @Lob
//    @Convert(converter = StringListConverter.class)
//    @Column(name = "items")
//    private List<String> items;

    @Column(name = "is_editing")
    private boolean isEditing;

}

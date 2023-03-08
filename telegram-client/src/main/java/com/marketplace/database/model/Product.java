package com.marketplace.database.model;

import com.marketplace.constant.Category;
import com.marketplace.constant.ProductStatus;
import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
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

    @Column(name = "rating_good")
    private Integer ratingGood;

    @Column(name = "rating_bad")
    private Integer ratingBad;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "instruction")
    private String instruction;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "items", columnDefinition = "text[]")
    @Type(StringArrayType.class)
    private String[] items;

    @Column(name = "is_editing")
    private boolean isEditing;

}

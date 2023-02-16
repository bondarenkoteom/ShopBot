package com.shop.ShopBot.database.model;

import com.shop.ShopBot.constant.Category;
import com.shop.ShopBot.constant.ProductStatus;
import com.shop.ShopBot.constant.Trigger;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Data
@NoArgsConstructor
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
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
    @Type(type = "string-array")
    private String[] items;

    @Column(name = "is_editing")
    private boolean isEditing;

}

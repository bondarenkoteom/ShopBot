package com.marketplace.entity;

import com.marketplace.constant.Category;
import com.marketplace.constant.ProductStatus;
import lombok.Data;

@Data
public class Product {
    Long id;
    User owner;
    ProductStatus status;
    Integer ratingGood;
    Integer ratingBad;
    String productName;
    String instruction;
    String description;
    Double price;
    Category category;
    String imageId;
    String[] items;
    Boolean isEditing;
}

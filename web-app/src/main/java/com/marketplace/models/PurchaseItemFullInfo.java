package com.marketplace.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseItemFullInfo {
    Long id;
    String seller;
    Integer reviews;
    Double rating;
    String title;
    Double price;
    Long imageId;
    String description;
    Integer sells;
    String status;
}

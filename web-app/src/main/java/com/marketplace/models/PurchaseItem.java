package com.marketplace.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseItem {
    Long id;
    String title;
    Double price;
    Long imageId;
    String status;
}

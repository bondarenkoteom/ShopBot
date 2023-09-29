package com.marketplace.models;

import com.marketplace.constant.Category;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewProduct {
    String userId;
    String title;
    String description;
    String instruction;
    Double price;
    Category[] categories;
}

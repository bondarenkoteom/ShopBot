package com.marketplace.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchItemFullInfo {
    Long id;
    String seller;
    Integer reviews;
    Double rating;
    String title;
    Double price;
    Long imageId;
    Boolean favorite;
    String description;
    Integer sells;
}

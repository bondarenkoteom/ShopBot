package com.marketplace.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchItem {
    Long id;
    String seller;
    Integer reviews;
    Double rating;
    String title;
    Double price;
    Long imageId;
    Boolean favorite;
}

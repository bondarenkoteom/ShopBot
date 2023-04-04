package com.marketplace.entity;

import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class ProductImage {
    Long id;
    ByteBuffer image;

}

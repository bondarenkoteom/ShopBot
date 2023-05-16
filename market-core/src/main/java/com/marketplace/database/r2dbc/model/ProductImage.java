package com.marketplace.database.r2dbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.nio.ByteBuffer;

@Data
@NoArgsConstructor
@Table(value = "product_image")
public class ProductImage {

    @SneakyThrows
    public ProductImage(Resource image) {
        this.image = ByteBuffer.wrap(image.getContentAsByteArray());
    }

    @Id
    private Long id;

    @Column("image")
    private ByteBuffer image;

}

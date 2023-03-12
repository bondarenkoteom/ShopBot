package com.marketplace.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
public class ProductRequest {

    @JsonInclude(Include.NON_NULL)
    Long productId;

    @JsonInclude(Include.NON_NULL)
    Long sellerId;

    @JsonInclude(Include.NON_NULL)
    Boolean isEditing;

}

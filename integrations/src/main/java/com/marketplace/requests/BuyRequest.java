package com.marketplace.requests;

import lombok.Data;

@Data
public class BuyRequest {
    Long productId;
    Long userId;
}

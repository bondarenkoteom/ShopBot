package com.marketplace.responses;

import com.marketplace.entity.Purchase;
import lombok.Data;

import java.util.Optional;

@Data
public class BuyResponse {
    Boolean error;
    String message;
    Purchase purchase;
    Double balance;
}

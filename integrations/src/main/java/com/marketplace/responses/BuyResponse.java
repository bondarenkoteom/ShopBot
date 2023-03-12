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

    public Optional<Purchase> getPurchase() {
        return purchase == null ? Optional.empty() : Optional.of(purchase);
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}

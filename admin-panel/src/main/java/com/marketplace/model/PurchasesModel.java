package com.marketplace.model;

import com.marketplace.entity.Purchase;
import com.marketplace.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PurchasesModel {

    private Boolean checked;
    private Purchase purchase;

    public PurchasesModel(Purchase purchase) {
        this.purchase = purchase;
        this.checked = false;
    }

    public static List<PurchasesModel> of(List<Purchase> purchases) {
        return purchases.stream().map(PurchasesModel::new).collect(Collectors.toList());
    }
}

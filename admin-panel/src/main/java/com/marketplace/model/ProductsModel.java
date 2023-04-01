package com.marketplace.model;

import com.marketplace.entity.Product;
import com.marketplace.entity.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductsModel {

    private Boolean checked;
    private Product product;

    public ProductsModel(Product product) {
        this.product = product;
        this.checked = false;
    }

    public static List<ProductsModel> of(List<Product> products) {
        return products.stream().map(ProductsModel::new).collect(Collectors.toList());
    }
}

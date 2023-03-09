package com.marketplace.client;

import com.marketplace.database.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

public interface HttpCoreInterface {

    @GetExchange("/api/v1/createnewadress")
    String createNewAddress();

    @GetExchange(value = "/api/v1/product")
    List<Product> getProduct();

    @PostExchange(value = "/api/v1/product")
    Product saveProduct(@RequestBody Product product);

    @DeleteExchange("/api/v1/product")
    ResponseEntity<Void> deleteProduct(@PathVariable boolean editing);

    @DeleteExchange("/api/v1/product")
    ResponseEntity<Void> deleteProduct(@PathVariable long id);

}

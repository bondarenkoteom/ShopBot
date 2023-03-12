package com.marketplace.controller.internal_api;

import com.marketplace.database.model.Product;
import com.marketplace.database.model.Purchase;
import com.marketplace.database.repository.ProductRepository;
import com.marketplace.database.service.ProductService;
import com.marketplace.requests.ProductRequest;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Optional<Product> productGet(@RequestParam Long productId) {
        return productService.getById(productId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void productUpdate(@RequestBody Product product) {
        productService.save(product);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void productDelete(@RequestParam Long productId) {
        productService.deleteById(productId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Page<Product> products(@ParameterObject Pageable pageable, @RequestBody ProductRequest productRequest) {
        return productService.getAllProducts(
                productRequest.getSellerId(),
                pageable);
    }

    @RequestMapping(value = "/editing", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Optional<Product> productEditingGet(@RequestParam Long ownerId) {
        return productService.getEditingProductByOwnerId(ownerId);
    }

    @RequestMapping(value = "/editing", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void productEditingDelete(@RequestParam Long ownerId) {
        productService.deleteAllEditing(ownerId);
    }

}

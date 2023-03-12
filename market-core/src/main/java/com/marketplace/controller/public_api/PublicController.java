package com.marketplace.controller.public_api;

import com.marketplace.database.model.Product;
import com.marketplace.database.model.Purchase;
import com.marketplace.database.service.BuyProcess;
import com.marketplace.database.service.ProductService;
import com.marketplace.database.service.PurchaseService;
import com.marketplace.requests.BuyRequest;
import com.marketplace.requests.PurchaseRequest;
import com.marketplace.requests.SearchRequest;
import com.marketplace.responses.BuyResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublicController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private BuyProcess buyProcess;

    @RequestMapping(value = "/api/v1/product/search", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Page<Product> search(@ParameterObject Pageable pageable, @RequestBody SearchRequest searchRequest) {
        return productService.findAllProducts(
                searchRequest.getQuery(),
                searchRequest.getCategory(),
                searchRequest.getOwnerId(),
                pageable);
    }

    @RequestMapping(value = "/api/v1/product/buy", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    BuyResponse buy(@RequestBody BuyRequest buyRequest) {
        return buyProcess.run(
                buyRequest.getProductId(),
                buyRequest.getProductId());
    }

    @RequestMapping(value = "/api/v1/purchases", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Page<Purchase> purchases(@ParameterObject Pageable pageable, @RequestBody PurchaseRequest purchaseRequest) {
        return purchaseService.getAllPurchases(
                purchaseRequest.getBuyerId(),
                pageable);
    }


}

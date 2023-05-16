package com.marketplace.controller;

import com.marketplace.database.jpa.model.Product;
import com.marketplace.database.jpa.model.Purchase;
import com.marketplace.database.service.BuyProcess;
import com.marketplace.database.service.ProductService;
import com.marketplace.database.service.PurchaseService;
import com.marketplace.requests.BuyRequest;
import com.marketplace.requests.SearchRequest;
import com.marketplace.responses.BuyResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public @ResponseBody
    ResponseEntity<BuyResponse> buy(@RequestBody BuyRequest buyRequest) {
        return ResponseEntity.ok(buyProcess.run(
                buyRequest.getProductId(),
                buyRequest.getUserId()));
    }

    @RequestMapping(value = "/api/v1/purchases", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Page<Purchase> purchases(@ParameterObject Pageable pageable,
                             @RequestParam(required = false) Long buyerId,
                             @RequestParam(required = false) Long orderId) {
        if (buyerId != null) {
            return purchaseService.findByBuyerId(buyerId, pageable);
        } else if(orderId != null) {
            return purchaseService.findByOrderId(orderId, pageable);
        } else {
            return purchaseService.getAllPurchases(pageable);
        }
    }


}

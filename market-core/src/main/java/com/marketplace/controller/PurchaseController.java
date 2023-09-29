package com.marketplace.controller;

import com.marketplace.constant.OrderStatus;
import com.marketplace.database.jpa.model.Purchase;
import com.marketplace.database.service.PurchaseService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

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

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<Purchase> purchaseGet(@RequestParam Long purchaseId) {
        return purchaseService.getById(purchaseId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @RequestMapping(value = "/status", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void statusUpdate(@RequestParam Long purchaseId, @RequestParam OrderStatus status) {
        if (status.equals(OrderStatus.CONFIRMED)) {
            purchaseService.confirmDelivery(purchaseId);
        } else {
            purchaseService.updateOrderStatus(purchaseId, status);
        }
    }

}
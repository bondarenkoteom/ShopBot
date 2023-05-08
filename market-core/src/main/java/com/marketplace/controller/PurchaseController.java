package com.marketplace.controller;

import com.marketplace.constant.OrderStatus;
import com.marketplace.database.model.Purchase;
import com.marketplace.database.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Optional<Purchase> purchaseGet(@RequestParam Long purchaseId) {
        return purchaseService.getById(purchaseId);
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
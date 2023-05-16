package com.marketplace.controller;

import com.marketplace.database.r2dbc.model.DisputeMessage;
import com.marketplace.database.jpa.model.Purchase;
import com.marketplace.database.service.DisputeService;
import com.marketplace.database.service.PurchaseService;
import com.marketplace.requests.DisputeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "/api/v1/disputes", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<DisputeMessage> disputesGet(@RequestParam Long purchaseId) {
        return disputeService.getDisputeMessages(purchaseId);
    }

    @RequestMapping(value = "/api/v1/disputes/purchases", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    List<Purchase> disputesPurchasesGet(@RequestParam(required = false) Long sellerId,
                                        @RequestParam(required = false) Long buyerId) {
        if (sellerId != null) {
            return purchaseService.getSellerDisputesPurchases(sellerId);
        } else if (buyerId != null) {
            return purchaseService.getBuyerDisputesPurchases(buyerId);
        } else {
            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "/api/v1/dispute", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<DisputeMessage> disputeCreate(@RequestBody DisputeRequest dispute) {
        return disputeService.save(dispute);
    }
}

package com.marketplace.controller.internal_api;

import com.marketplace.database.model.Dispute;
import com.marketplace.database.model.Purchase;
import com.marketplace.database.service.DisputeService;
import com.marketplace.database.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody
    List<Dispute> disputesGet(@RequestParam Long purchaseId) {
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
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void disputeCreate(@RequestBody Dispute dispute) {
        disputeService.save(dispute);
    }
}

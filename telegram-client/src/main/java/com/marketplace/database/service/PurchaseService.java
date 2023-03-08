package com.marketplace.database.service;

import com.marketplace.database.repository.PurchaseRepository;
import com.marketplace.constant.OrderStatus;
import com.marketplace.database.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public Page<Purchase> getAllPurchases(Long buyerId, Pageable pageable) {
        return purchaseRepository.getPurchasesByBuyerId(buyerId, pageable);
    }

    public void createOrder(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    public void updateOrderStatus(Long purchaseId, OrderStatus status) {
        Purchase purchase = purchaseRepository.getReferenceById(purchaseId);
        purchase.setStatus(status);
        purchaseRepository.save(purchase);
    }

    public void updateOrdersStatus() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);
        purchaseRepository.updateOverdueOrders(Date.from(localDateTime.toInstant(ZoneOffset.UTC)));
    }

    public void confirmDelivery(Long purchaseId) {
        purchaseRepository.confirmDelivery(purchaseId);
    }

    public Optional<Purchase> getById(Long id) {
        return purchaseRepository.findById(id);
    }

    public List<Purchase> getBuyerChatsPurchases(Long id) {
        return purchaseRepository.disputeBuyerPurchasesChats(id);
    }

    public List<Purchase> getSellerChatsPurchases(Long id) {
        return purchaseRepository.disputeSellerPurchasesChats(id);
    }
}

package com.marketplace.database.service;

import com.marketplace.constant.OrderStatus;
import com.marketplace.database.model.Product;
import com.marketplace.database.model.Purchase;
import com.marketplace.database.model.User;
import com.marketplace.responses.BuyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;

@Service
public class BuyProcess {

    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final UserService userService;

    @Autowired
    public BuyProcess(ProductService productService, PurchaseService purchaseService, UserService userService) {
        this.productService = productService;
        this.purchaseService = purchaseService;
        this.userService = userService;
    }

    public BuyResponse run(Long productId, Long userId) {
        Optional<Product> productOptional = productService.getById(productId);
        BuyResponse buyResponse = new BuyResponse();

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            User buyer = userService.getUser(userId);
            User seller = product.getOwner();

            if (product.getPrice() > buyer.getBalance()) {
                buyResponse.setMessage("Not enough money");
                buyResponse.setError(true);
                return buyResponse;
            }

            String item = productService.pollItem(product.getId());
            if (!item.isEmpty()) {

                buyer.setBalance(BigDecimal.valueOf(buyer.getBalance() - product.getPrice())
                        .setScale(2, RoundingMode.DOWN).doubleValue());

                userService.save(buyer);

                Purchase purchase = new Purchase();
                purchase.setDate(new Date());
                purchase.setName(product.getProductName());
                purchase.setProductId(product.getId());
                purchase.setInstruction(product.getInstruction());
                purchase.setItem(item);
                purchase.setStatus(OrderStatus.IN_PROGRESS);
                purchase.setBuyer(buyer);
                purchase.setSeller(seller);
                purchase.setPrice(product.getPrice());
                purchaseService.createOrder(purchase);

                buyResponse.setPurchase(purchase.toIntegrationPurchase());
                buyResponse.setBalance(buyer.getBalance());
                buyResponse.setMessage("success");
                buyResponse.setError(false);
                return buyResponse;
            } else {
                buyResponse.setMessage("Item is empty");
                buyResponse.setError(true);
                return buyResponse;
            }

        } else {
            buyResponse.setMessage("Product not found");
            buyResponse.setError(true);
            return buyResponse;
        }
    }

}

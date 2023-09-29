package com.marketplace.client;

import com.marketplace.entity.Product;
import com.marketplace.entity.ProductImage;
import com.marketplace.entity.Purchase;
import com.marketplace.entity.User;
import com.marketplace.requests.BuyRequest;
import com.marketplace.responses.BuyResponse;
import com.marketplace.responses.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.Optional;

public interface HttpCoreInterface {

    /**
     * Список всех пользователей
     */
    @GetExchange(value = "/api/v1/users")
    PageResponse<User> usersGet(@RequestParam int page,
                                @RequestParam int size,
                                @RequestParam(required = false) String[] sort,
                                @RequestParam(required = false) List<Long> userIds,
                                @RequestParam(required = false) String username);

    /**
     * Обновление пользователя
     */
    @PutExchange(value = "/api/v1/user")
    ResponseEntity<Void> userUpdate(@RequestBody User user);

    /**
     * Список покупок
     */
    @GetExchange(value = "/api/v1/purchases")
    PageResponse<Purchase> purchases(@RequestParam int page,
                                     @RequestParam int size,
                                     @RequestParam(required = false) String[] sort,
                                     @RequestParam(required = false) Long buyerId,
                                     @RequestParam(required = false) Long orderId);

    /**
     * Список товаров
     */
    @GetExchange(value = "/api/v1/products")
    PageResponse<Product> products(@RequestParam int page,
                                   @RequestParam int size,
                                   @RequestParam(required = false) String[] sort,
                                   @RequestParam(required = false) Long productId,
                                   @RequestParam(required = false) Long sellerId,
                                   @RequestParam(required = false) String name);

    @GetExchange(value = "/api/v1/product/image/{id}")
    ResponseEntity<ProductImage> getImage(@PathVariable Long id);

    /**
     * Обновление товара
     */
    @PutExchange(value = "/api/v1/product")
    ResponseEntity<Void> productUpdate(@RequestBody Product product);

    /**
     * Получение товара
     */
    @GetExchange(value = "/api/v1/product")
    Optional<Product> productGet(@RequestParam Long productId);

    /**
     * Покупка товара
     */
    @PostExchange(value = "/api/v1/product/buy")
    BuyResponse buy(@RequestBody BuyRequest buyRequest);
}

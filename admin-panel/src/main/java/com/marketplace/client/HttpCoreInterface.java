package com.marketplace.client;

import com.marketplace.entity.Purchase;
import com.marketplace.entity.User;
import com.marketplace.requests.PurchaseRequest;
import com.marketplace.responses.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

public interface HttpCoreInterface {

    /**
     * Список всех пользователей
     */
    @GetExchange(value = "/api/v1/users")
    PageResponse<User> usersGet(@RequestParam int page,
                                @RequestParam int size,
                                @RequestParam(required = false) String[] sort,
                                @RequestParam(required = false) List<Long> userIds);

    /**
     * Обновление пользователя
     */
    @PutExchange(value = "/api/v1/user")
    ResponseEntity<Void> userUpdate(@RequestBody User user);

    /**
     * Список покупок
     */
    @PostExchange(value = "/api/v1/purchases")
    PageResponse<Purchase> purchases(@RequestParam int page,
                                     @RequestParam int size,
                                     @RequestParam(required = false) String[] sort,
                                     @RequestBody PurchaseRequest purchaseRequest);

}

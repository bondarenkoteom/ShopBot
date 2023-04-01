package com.marketplace.client;

import com.marketplace.constant.OrderStatus;
import com.marketplace.constant.Trigger;
import com.marketplace.entity.*;
import com.marketplace.requests.*;
import com.marketplace.responses.BuyResponse;
import com.marketplace.responses.PageResponse;
import com.marketplace.responses.TriggerResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.Optional;

public interface HttpCoreInterface {


    //++++++++++++++++++++++ PUBLIC API ++++++++++++++++++++++

    /**
     * Поиск товаров
     */
    @PostExchange(value = "/api/v1/product/search")
    PageResponse<Product> search(@RequestParam int page,
                                 @RequestParam int size,
                                 @RequestParam(required = false) String[] sort,
                                 @RequestBody SearchRequest searchRequest);

    /**
     * Покупка товара
     */
    @PostExchange(value = "/api/v1/product/buy")
    BuyResponse buy(@RequestBody BuyRequest buyRequest);

    /**
     * Список покупок
     */
    @GetExchange(value = "/api/v1/purchases")
    PageResponse<Purchase> purchases(@RequestParam int page,
                                     @RequestParam int size,
                                     @RequestParam(required = false) String[] sort,
                                     @RequestParam(required = false) Long buyerId,
                                     @RequestParam(required = false) Long orderId);



    //+++++++++++++++++++++ INTERNAL API +++++++++++++++++++++
    //++++++++++++++++++++++++ PRODUCT +++++++++++++++++++++++

    /**
     * Получение товара
     */
    @GetExchange(value = "/api/v1/product")
    Optional<Product> productGet(@RequestParam Long productId);

    /**
     * Обновление товара
     */
    @PutExchange(value = "/api/v1/product")
    ResponseEntity<Void> productUpdate(@RequestBody Product product);

    /**
     * Удаление товара
     */
    @DeleteExchange(value = "/api/v1/product")
    ResponseEntity<Void> productDelete(@RequestParam Long productId);

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

    /**
     * Получение товара, который находится в состоянии редактирования
     */
    @GetExchange(value = "/api/v1/product/editing")
    Optional<Product> productEditingGet(@RequestParam Long ownerId);

    /**
     * Удаление товаров, которые находятся в состоянии редактирования
     */
    @DeleteExchange(value = "/api/v1/product/editing")
    ResponseEntity<Void> productEditingDelete(@RequestParam Long ownerId);

    /**
     * Сохранение фото продукта
     */
    @PostExchange(value = "/api/v1/product/image", contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Object> saveImage(@RequestPart Product product, @RequestPart MultipartFile document);


    //+++++++++++++++++++++++ PURCHASE +++++++++++++++++++++++

    /**
     * Получение покупки
     */
    @GetExchange(value = "/api/v1/purchase")
    Optional<Purchase> purchaseGet(@RequestParam Long purchaseId);

    /**
     * Обновление статуса
     */
    @PutExchange(value = "/api/v1/purchase/status")
    ResponseEntity<Void> statusUpdate(@RequestParam Long purchaseId,
                                      @RequestParam OrderStatus status);


    //+++++++++++++++++++++++++ USER +++++++++++++++++++++++++

    /**
     * Получение пользователя
     */
    @GetExchange(value = "/api/v1/user")
    Optional<User> userGet(@RequestParam(required = false) Long userId,
                           @RequestParam(required = false) String username);

    /**
     * Создание пользователя
     */
    @PostExchange(value = "/api/v1/user")
    ResponseEntity<Void> userCreate(@RequestBody UserRequest userRequest);

    /**
     * Обновление пользователя
     */
    @PutExchange(value = "/api/v1/user")
    ResponseEntity<Void> userUpdate(@RequestBody User user);

    /**
     * Получение тригера
     */
    @GetExchange(value = "/api/v1/user/trigger")
    TriggerResponse triggerGet(@RequestParam Long userId);

    /**
     * Обновление тригера
     */
    @PutExchange(value = "/api/v1/user/trigger")
    ResponseEntity<Void> triggerUpdate(@RequestBody TriggerRequest triggerRequest);

    /**
     * Получение списка топ пользователей
     */
    @GetExchange(value = "/api/v1/users/top")
    List<User> topUsers();


    //+++++++++++++++++++++++ DISPUTES +++++++++++++++++++++++

    /**
     * Список сообщений диспута
     */
    @GetExchange(value = "/api/v1/disputes")
    List<Dispute> disputesGet(@RequestParam Long purchaseId);

    /**
     * Список диспутов
     */
    @GetExchange(value = "/api/v1/disputes/purchases")
    List<Purchase> disputesPurchasesGet(@RequestParam(required = false) Long sellerId,
                                        @RequestParam(required = false) Long buyerId);

    /**
     * Создание сообщения диспута
     */
    @PostExchange(value = "/api/v1/dispute")
    ResponseEntity<Void> disputeCreate(@RequestBody Dispute dispute);


    //+++++++++++++++++++++++ MESSAGES +++++++++++++++++++++++

    /**
     * Список сообщений
     */
    @GetExchange(value = "/api/v1/messages")
    PageResponse<Message> messagesGet(@RequestParam int page,
                                      @RequestParam int size,
                                      @RequestParam(required = false) String[] sort,
                                      @RequestParam Long superUserId,
                                      @RequestParam Long userId);

    /**
     * Список чатов
     */
    @GetExchange(value = "/api/v1/messages/users")
    List<User> messagesUsersGet(@RequestParam Long userId);

    /**
     * Создание сообщения
     */
    @PostExchange(value = "/api/v1/message")
    ResponseEntity<Void> messageCreate(@RequestBody Message message);
}

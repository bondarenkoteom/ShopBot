package com.marketplace.client;

import com.marketplace.entity.User;
import com.marketplace.responses.PageResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface HttpCoreInterface {

    /**
     * Список всех пользователей
     */
    @GetExchange(value = "/api/v1/users")
    PageResponse<User> usersGet(@RequestParam int page,
                                @RequestParam int size,
                                @RequestParam(required = false) String[] sort);

}

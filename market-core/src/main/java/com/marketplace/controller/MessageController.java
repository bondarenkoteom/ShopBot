package com.marketplace.controller;

import com.marketplace.database.model.Message;
import com.marketplace.database.model.User;
import com.marketplace.database.service.MessageService;
import com.marketplace.database.service.UserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/v1/messages", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Page<Message> messagesGet(@ParameterObject Pageable pageable,
                              @RequestParam Long superUserId,
                              @RequestParam Long userId) {
        return messageService.getChatMessages(superUserId, userId, pageable);
    }

    @RequestMapping(value = "/api/v1/messages/users", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    List<User> messagesUsersGet(@RequestParam Long userId) {
        return userService.getChatsUsers(userId);
    }

    @RequestMapping(value = "/api/v1/message", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void messageCreate(@RequestBody Message message) {
        messageService.save(message);
    }
}

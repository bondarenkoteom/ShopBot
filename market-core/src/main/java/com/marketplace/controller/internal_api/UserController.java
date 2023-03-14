package com.marketplace.controller.internal_api;

import com.marketplace.database.model.User;
import com.marketplace.database.service.UserService;
import com.marketplace.requests.TriggerRequest;
import com.marketplace.requests.UserRequest;
import com.marketplace.responses.TriggerResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/v1/user", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Optional<User> userGet(@RequestParam(required = false) Long userId, @RequestParam(required = false) String username) {
        return userService.findUser(userId, username);
    }

    @RequestMapping(value = "/api/v1/user", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void userCreate(@RequestBody UserRequest userRequest) {
        userService.createIfAbsent(
                userRequest.getUserId(),
                userRequest.getUsername());
    }

    @RequestMapping(value = "/api/v1/user", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void userUpdate(@RequestBody User user) {
        userService.save(user);
    }

    @RequestMapping(value = "/api/v1/users", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Page<User> users(@ParameterObject Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @RequestMapping(value = "/api/v1/user/trigger", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    TriggerResponse triggerGet(@RequestParam Long userId) {
        TriggerResponse triggerResponse = new TriggerResponse();
        triggerResponse.setTrigger(userService.getWaitFor(userId));
        return triggerResponse;
    }

    @RequestMapping(value = "/api/v1/user/trigger", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void triggerUpdate(@RequestBody TriggerRequest triggerRequest) {
        userService.setWaitFor(
                triggerRequest.getUserId(),
                triggerRequest.getTrigger());
    }

    @RequestMapping(value = "/api/v1/users/top", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    List<User> topUsers() {
        return userService.findTop25Vendors();
    }
}

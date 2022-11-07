package com.shop.ShopBot.database.service;

import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.database.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createIfAbsent(Long userId, String username) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) {
            User user = new User(userId, username, Trigger.UNDEFINED);
            userRepository.save(user);
        }
    }

    public void setWaitFor(Long userId, Trigger trigger) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setWaitFor(trigger);
            userRepository.save(user);
        }
    }

    public Trigger getWaitFor(Long userId) {
        Optional<User> optional = userRepository.findById(userId);
        return optional.isPresent() ? optional.get().getWaitFor() : Trigger.UNDEFINED;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}

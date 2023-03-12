package com.marketplace.database.service;

import com.marketplace.constant.Trigger;
import com.marketplace.database.model.User;
import com.marketplace.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createIfAbsent(Long userId, String username) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) {
            User user = new User(userId, username);
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

    public Optional<User> findUser(Long userId, String username) {
        if (userId != null) {
            return userRepository.findById(userId);
        } else if (username != null && !username.isEmpty()) {
            return userRepository.findByUsername(username);
        } else {
            return Optional.empty();
        }
    }

    public User save(User user) {
        return userRepository.save(user);
    }


    public List<User> getChatsUsers(Long id) {
        return userRepository.unionChatsUsers(id);
    }

    public List<User> findTop25Vendors() {
        return userRepository.findTop25Vendors();
    }

    public void incrementSells(Long userId) {
        User user = getUser(userId);
        user.setSells(user.getSells() + 1);
        userRepository.save(user);
    }
}

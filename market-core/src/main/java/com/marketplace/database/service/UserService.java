package com.marketplace.database.service;

import com.marketplace.constant.Trigger;
import com.marketplace.database.model.User;
import com.marketplace.database.repository.RoleRepository;
import com.marketplace.database.repository.UserRepository;
import com.marketplace.utils.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void createIfAbsent(Long userId, String username) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) {
            User user = new User(userId, username);
            user.setRole(roleRepository.findByName("ROLE_USER"));
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
        } else if (Values.isNotEmpty(username)) {
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

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findByIds(List<Long> userIds, Pageable pageable) {
        return userRepository.findByIdIn(userIds, pageable);
    }

    public Page<User> findByUsername(String username, Pageable pageable) {
        return userRepository.findByUsernameContainingIgnoreCase(username, pageable);
    }

}

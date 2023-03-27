package com.marketplace.entity;

import com.marketplace.constant.Trigger;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
        this.rating = 0;
        this.sells = 0;
        this.waitFor = Trigger.UNDEFINED;
        this.balance = 0.0;
    }

    Long id;
    Role role;
    String username;
    String status;
    Integer rating;
    Trigger waitFor;
    Double balance;
    Integer sells;
    Integer purchases;
}

package com.marketplace.entity;

import com.marketplace.constant.Trigger;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

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

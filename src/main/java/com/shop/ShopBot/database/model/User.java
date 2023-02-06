package com.shop.ShopBot.database.model;

import com.shop.ShopBot.constant.Trigger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
        this.rating = 0;
        this.sells = 0;
        this.waitFor = Trigger.UNDEFINED;
        this.balance = 0.0;
    }

    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "wait_for_input")
    @Enumerated(EnumType.STRING)
    private Trigger waitFor;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "sells")
    private Integer sells;

    @Column(name = "purchases")
    private Integer purchases;

}

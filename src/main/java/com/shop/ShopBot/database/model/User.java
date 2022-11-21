package com.shop.ShopBot.database.model;

import com.shop.ShopBot.constant.Trigger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {

    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "wait_for_input")
    @Enumerated(EnumType.STRING)
    private Trigger waitFor;

}

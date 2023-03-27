package com.marketplace.database.model;

import com.marketplace.constant.Trigger;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

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

    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)
    private Role role;

    @Column(name = "username")
    private String username;

    @Column(name = "status")
    private String status;

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

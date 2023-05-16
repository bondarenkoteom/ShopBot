package com.marketplace.database.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marketplace.constant.Trigger;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Data
@ToString(exclude = {"purchases", "sells"})
@EqualsAndHashCode(exclude = {"purchases", "sells"})
@JsonIgnoreProperties(value = {"purchases", "sells"})
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
        this.rating = 0;
        this.waitFor = Trigger.UNDEFINED;
        this.balance = 0.0;
        this.status = "ACTIVE";
        this.joinDate = new Date();
        this.lastActiveDate = new Date();
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

    @Column(name = "join_date")
    private Date joinDate;

    @Column(name = "last_active_date")
    private Date lastActiveDate;

    @OneToMany(mappedBy="buyer", fetch = FetchType.EAGER)
    private Set<Purchase> purchases;

    @OneToMany(mappedBy="seller", fetch = FetchType.EAGER)
    private Set<Purchase> sells;

    public com.marketplace.entity.User toIntegrationUser() {
        com.marketplace.entity.User integrationUser = new com.marketplace.entity.User();
        com.marketplace.entity.Role integrationRole = new com.marketplace.entity.Role();
        integrationRole.setId(getRole().getId());
        integrationRole.setName(getRole().getName());
        integrationUser.setId(getId());
        integrationUser.setRole(integrationRole);
        integrationUser.setUsername(getUsername());
        integrationUser.setStatus(getStatus());
        integrationUser.setRating(getRating());
        integrationUser.setWaitFor(getWaitFor());
        integrationUser.setBalance(getBalance());
        //todo joinDate
        //todo lastActiveDate
        integrationUser.setPurchases(getPurchases().size());
        integrationUser.setSells(getSells().size());
        return integrationUser;
    }

}

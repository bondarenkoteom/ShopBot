package com.shop.ShopBot.database.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_user")
public class User {

    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "wait_for_input")
    private String waitFor;

    @Column(name = "type")
    private String type;

    public User() {

    }

    public User(Long id, String username, String waitFor) {
        this.id = id;
        this.username = username;
        this.waitFor = waitFor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWaitFor() {
        return waitFor;
    }

    public void setWaitFor(String waitFor) {
        this.waitFor = waitFor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

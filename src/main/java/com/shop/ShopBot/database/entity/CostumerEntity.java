package com.shop.ShopBot.database.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "costumer")
public class CostumerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "costumer_id")
    private Long id;

    @Column(name = "costumer_name")
    private String name;

    @Column(name = "costumer_date_registration")
    private Date dateRegistration;

    public CostumerEntity() {
    }

    public CostumerEntity(Long id, String name, Date dateRegistration) {
        this.id = id;
        this.name = name;
        this.dateRegistration = dateRegistration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

}

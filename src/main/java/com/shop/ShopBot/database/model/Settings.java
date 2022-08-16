package com.shop.ShopBot.database.model;

import javax.persistence.*;

@Entity
@Table(name = "t_settings")
public class Settings {

    @Id
    private Long id;
}

package com.marketplace.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "t_settings")
public class Settings {

    @Id
    private Long id;
}

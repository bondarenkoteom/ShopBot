package com.marketplace.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_settings")
public class Settings {

    @Id
    private Long id;
}

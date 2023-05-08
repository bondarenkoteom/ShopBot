package com.marketplace.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table(value = "dispute_channel")
public class DisputeChannel {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("unread")
    private Integer unread;

}

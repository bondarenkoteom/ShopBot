package com.marketplace.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table(value = "dispute_channel")
@JsonIgnoreProperties(value = {"new"})
public class DisputeChannel implements Persistable<Long> {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("unread")
    private Integer unread;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @JsonProperty(value = "new")
    @Transient
    private Boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void isNew(boolean isNew) {
        this.isNew = isNew;
    }
}

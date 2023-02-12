package com.dconnect.infrastructure.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Connection {

    @Id
    @GeneratedValue(generator = "channel_seq")
    @GenericGenerator(name = "channel_seq", strategy = "increment")
    private Long id;

    private String name;
    private String creationBy;
    private OffsetDateTime creationDate;
    private String modificationBy;
    private OffsetDateTime modificationDate;
    private boolean active;
    @Column(name = "private")
    private boolean conPrivate;
}

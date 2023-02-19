package com.dconnect.infrastructure.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(generator = "servers_seq")
    @GenericGenerator(name = "servers_seq", strategy = "increment")
    private Long id;

    @OneToOne
    private Connection connection;
    private String token;
    private boolean active;
    private OffsetDateTime creationDate;
    private OffsetDateTime modificationDate;
}

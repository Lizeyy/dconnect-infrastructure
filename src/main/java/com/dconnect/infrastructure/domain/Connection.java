package com.dconnect.infrastructure.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "connection")
public class Connection {

    @Id
    @GeneratedValue(generator = "connection_seq")
    @GenericGenerator(name = "connection_seq", strategy = "increment")
    private Long id;

    private String name;
    @OneToOne
    private Channel rootChannel;
    private String creationBy;
    private OffsetDateTime creationDate;
    private String modificationBy;
    private OffsetDateTime modificationDate;
    private boolean active;

    @OneToMany
    private Set<Channel> channels = new HashSet<>();
}

package com.dconnect.infrastructure.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "active = true")
@Table(name = "connection_details")
public class ConnectionDetails {

    @Id
    @GeneratedValue(generator = "connections_details_seq")
    @GenericGenerator(name = "connections_details_seq", strategy = "increment")
    private Long id;

    private String creationBy;
    private OffsetDateTime creationDate;
    private String modificationBy;
    private OffsetDateTime modificationDate;

    @Column(name = "active")
    private boolean active;

    @OneToOne
    private Channel channel;
}

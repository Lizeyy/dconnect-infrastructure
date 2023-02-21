package com.dconnect.infrastructure.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    @Id
    @GeneratedValue(generator = "server_seq")
    @GenericGenerator(name = "server_seq", strategy = "increment")
    private Long id;
    private String discordServerId;
    private String name;
    private String creationBy;
    private OffsetDateTime creationDate;
    private String modificationBy;
    private OffsetDateTime modificationDate;
    @OneToMany(mappedBy = "server")
    private Set<Channel> channels = new HashSet<>();
}

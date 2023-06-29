package com.dconnect.infrastructure.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(generator = "channel_seq")
    @GenericGenerator(name = "channel_seq", strategy = "increment")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "server_id", nullable=false)
    private Server server;

    private String discordChannelId;
    private String name;
    private String creationBy;
    private OffsetDateTime creationDate;
    private String modificationBy;
    private OffsetDateTime modificationDate;

    @OneToOne(mappedBy = "channel")
    private ConnectionDetails details;

    @ManyToOne
    @JoinColumn(name = "connection_id")
    private Connection connection;
}

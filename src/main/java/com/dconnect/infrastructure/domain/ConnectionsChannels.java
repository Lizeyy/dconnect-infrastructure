package com.dconnect.infrastructure.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionsChannels {

    @Id
    @GeneratedValue(generator = "channel_seq")
    @GenericGenerator(name = "channel_seq", strategy = "increment")
    private Long id;

    @OneToOne
    @JoinColumn(name="channel_id", nullable=false)
    private Channel channelId;

    @OneToOne
    @JoinColumn(name="connection_id", nullable=false)
    private Connection connectionId;
    private String creationBy;
    private OffsetDateTime creationDate;
    private String modificationBy;
    private OffsetDateTime modificationDate;
    private boolean active;
}

package com.dconnect.infrastructureservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionsChannels {

    @Id
    @GeneratedValue(generator = "channel_seq")
    @GenericGenerator(name = "channel_seq", strategy = "increment")
    private Long id;

    @ManyToOne
    @JoinColumn(name="channel_id", nullable=false)
    private Channel channelId;

    @ManyToOne
    @JoinColumn(name="connection_id", nullable=false)
    private Connection connectionId;
    private String creationBy;
    private OffsetDateTime creationDate;
    private String modificationBy;
    private OffsetDateTime modificationDate;
    private boolean active;
}

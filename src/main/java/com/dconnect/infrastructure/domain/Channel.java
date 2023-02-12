package com.dconnect.infrastructure.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(generator = "channel_seq")
    @GenericGenerator(name = "channel_seq", strategy = "increment")
    private Long id;

    @ManyToOne
    @JoinColumn(name="server_id", nullable=false)
    private Server serverId;
    private String discordChannelId;
    private String name;
    private String creationBy;
    private OffsetDateTime creationDate;
    private String modificationBy;
    private OffsetDateTime modificationDate;
}

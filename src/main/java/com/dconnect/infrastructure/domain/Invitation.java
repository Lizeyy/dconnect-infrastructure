package com.dconnect.infrastructure.domain;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document(collection = "invitation")
public class Invitation {

    @Id
    private String id;

    private String connectionId;
    private String serverId;
    private String serverName;
    private String channelId;
    private String createdBy;
    private OffsetDateTime creationDate;
}

package com.dconnect.infrastructure.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "invitation")
public class Invitation {

    @Id
    private String id;

    private Long connectionId;
    private String serverId;
    private String serverName;
    private String channelId;
    private String createdBy;
    private LocalDateTime creationDate;
}

package com.dconnect.infrastructureservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    @Id
    @GeneratedValue(generator = "servers_seq")
    @GenericGenerator(name = "servers_seq", strategy = "increment")
    private Long id;
    private String discordServerId;
    private String name;
    private String creationBy;
    private OffsetDateTime creationDate;
    private String modificationBy;
    private OffsetDateTime modificationDate;
}

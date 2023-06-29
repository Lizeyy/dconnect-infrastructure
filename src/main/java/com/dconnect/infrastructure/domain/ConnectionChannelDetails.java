package com.dconnect.infrastructure.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@Table(name = "connection_channel_details")
public class ConnectionChannelDetails {

    @OneToOne
    @JoinColumn(name = "connection_id")
    private Connection connection;

    @OneToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToOne
    @JoinColumn(name = "details_id")
    private ConnectionDetails details;
}

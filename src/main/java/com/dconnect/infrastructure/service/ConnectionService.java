package com.dconnect.infrastructure.service;

import com.dconnect.client.protocol.domain.request.ConnectionCreateRequest;
import com.dconnect.client.protocol.domain.response.ConnectionCreateResponse;
import com.dconnect.infrastructure.domain.*;
import com.dconnect.infrastructure.error.ChannelAlreadyUsed;
import com.dconnect.infrastructure.mapper.ConnectionMapper;
import com.dconnect.infrastructure.repository.ConnectionRepository;
import com.dconnect.infrastructure.repository.ConnectionsChannelsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final ConnectionsChannelsRepository connectionsChannelsRepository;
    private final ChannelService channelService;
    private final ServerService serverService;
    private final TokenService tokenService;

    public ConnectionCreateResponse createConnection(ConnectionCreateRequest request) {
        if (channelService.checkIfChannelExist(request.getServerId(), request.getChannelId())) {
            throw new ChannelAlreadyUsed("Kanał już jest połączony z siecią!");
        }

        final Server server = serverService.getOrCreateServer(request.getServerId(), request.getCreationBy());
        final Channel channel = channelService.createChannel(server, request.getChannelId(), request.getCreationBy());
        serverService.addChannelToServer(server, channel);

        final Connection connection = ConnectionMapper.INSTANCE.map(request);
        connection.setRootChannel(channel);
        connection.setCreationDate(OffsetDateTime.now());
        connectionRepository.save(connection);

        createConnectionsChannels(channel, connection, request.getCreationBy());

        final String token = tokenService.createToken(connection);

        return ConnectionMapper.INSTANCE.map(connection, token);
    }

    private ConnectionsChannels createConnectionsChannels(Channel channel, Connection connection, String creationBy) {
        final ConnectionsChannels con = new ConnectionsChannels();
        con.setConnectionId(connection);
        con.setChannelId(channel);
        con.setActive(true);
        con.setCreationBy(creationBy);
        con.setCreationDate(OffsetDateTime.now());
        return connectionsChannelsRepository.save(con);
    }
}

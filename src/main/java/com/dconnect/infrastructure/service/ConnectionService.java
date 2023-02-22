package com.dconnect.infrastructure.service;

import com.dconnect.client.protocol.domain.request.ConnectionCreateRequest;
import com.dconnect.client.protocol.domain.request.ConnectionJoinRequest;
import com.dconnect.client.protocol.domain.response.ConnectionCreateResponse;
import com.dconnect.client.protocol.domain.response.ConnectionJoinResponse;
import com.dconnect.infrastructure.domain.*;
import com.dconnect.infrastructure.error.ChannelAlreadyUsed;
import com.dconnect.infrastructure.error.TokenNotActive;
import com.dconnect.infrastructure.mapper.ConnectionMapper;
import com.dconnect.infrastructure.repository.ConnectionRepository;
import com.dconnect.infrastructure.repository.ConnectionsChannelsRepository;
import com.dconnect.infrastructure.repository.InvitationRepository;
import jakarta.transaction.Transactional;
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
    private final InvitationService invitationService;

    @Transactional //żeby nie zapisało w razie errora
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

        createConnectionsChannels(channel, connection, request.getCreationBy());
        channelService.addChannelToConnection(channel, connection);

        connectionRepository.save(connection);
        final String token = tokenService.createToken(connection);

        return ConnectionMapper.INSTANCE.map(connection, token);
    }

    private void createConnectionsChannels(Channel channel, Connection connection, String creationBy) {
        final ConnectionDetails con = new ConnectionDetails();
        con.setActive(true);
        con.setCreationBy(creationBy);
        con.setCreationDate(OffsetDateTime.now());
        con.setChannel(channel);
        connectionsChannelsRepository.save(con);
    }


}

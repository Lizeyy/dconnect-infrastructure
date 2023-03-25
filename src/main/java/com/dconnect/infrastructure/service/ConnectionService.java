package com.dconnect.infrastructure.service;

import com.dconnect.client.protocol.domain.request.ConnectionCreateRequest;
import com.dconnect.client.protocol.domain.request.ConnectionJoinRequest;
import com.dconnect.client.protocol.domain.response.ConnectionCreateResponse;
import com.dconnect.client.protocol.domain.response.ConnectionJoinResponse;
import com.dconnect.client.protocol.domain.response.ConnectionListOnServerResponse;
import com.dconnect.infrastructure.domain.*;
import com.dconnect.infrastructure.error.ChannelAlreadyUsed;
import com.dconnect.infrastructure.error.ConnectionNotFound;
import com.dconnect.infrastructure.error.TokenNotActive;
import com.dconnect.infrastructure.mapper.ConnectionMapper;
import com.dconnect.infrastructure.repository.ConnectionRepository;
import com.dconnect.infrastructure.repository.ConnectionsChannelsRepository;
import com.dconnect.infrastructure.repository.InvitationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final ConnectionsChannelsRepository connectionsChannelsRepository;
    private final ChannelService channelService;
    private final ServerService serverService;
    private final TokenService tokenService;

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

    public Connection addToConnection(Long connectionId, Channel channel, String creationBy) {
        final Connection connection = getConnection(connectionId);
        createConnectionsChannels(channel, connection, creationBy);
        channelService.addChannelToConnection(channel, connection);
        return connectionRepository.save(connection);
    }

    public ConnectionListOnServerResponse getConnectionListOnServerResponse(String serverId) {
        final Optional<Server> server = serverService.getServer(serverId);
        if (server.isPresent()) {
            final List<Connection> connections = connectionRepository.findAllByChannelsServer(server.get()).stream()
                    .filter(Connection::isActive).toList();
            return ConnectionListOnServerResponse.builder()
                    .serverName(server.get().getName())
                    .connections(createChannelConnectionMap(connections, serverId))
                    .build();
        }
        return new ConnectionListOnServerResponse();
    }

    public String getConnectionName(Long id) {
        return connectionRepository.findById(id).orElseThrow(() -> new ConnectionNotFound("Nie znaleziono połączenia")).getName();
    }

    public Connection getConnection(Long connectionId) {
        return connectionRepository.findById(connectionId).orElseThrow(() -> new ConnectionNotFound("Nie znaleziono połączenia"));
    }

    public boolean checkIfChannelExistInConnection(String channelId) {
        return connectionRepository.existsByChannelsDiscordChannelId(channelId);
    }
    public Set<String> getChannelsInConnectionByChannelId(String channelId) {
        final Connection connection = connectionRepository.findByChannelsDiscordChannelId(channelId);
        return connection.getChannels().stream().map(Channel::getDiscordChannelId).collect(Collectors.toSet());
    }

    private void createConnectionsChannels(Channel channel, Connection connection, String creationBy) {
        final ConnectionDetails con = new ConnectionDetails();
        con.setActive(true);
        con.setCreationBy(creationBy);
        con.setCreationDate(OffsetDateTime.now());
        con.setChannel(channel);
        connectionsChannelsRepository.save(con);
    }

    private Map<String, String> createChannelConnectionMap(List<Connection> connections, String serverId) {
        final Map<String, String> map = new HashMap<>();
        connections.forEach(connection -> {
            final List<Channel> channelList = connection.getChannels().stream().filter(channel1 -> channel1.getServer().getDiscordServerId().equals(serverId)).toList();
            channelList.forEach(channel -> {
                if (checkChannelConIsActive(channel)) {
                    map.put(channel.getName(), connection.getName());
                }
            });
        });
        return map;
    }

    private boolean checkChannelConIsActive(Channel channel) {
        return connectionsChannelsRepository.existsByChannelAndActiveIsTrue(channel);
    }

}

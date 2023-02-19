package com.dconnect.infrastructure.service;

import com.dconnect.client.protocol.domain.response.ServerInfo;
import com.dconnect.infrastructure.client.DiscordClient;
import com.dconnect.infrastructure.domain.Channel;
import com.dconnect.infrastructure.domain.Server;
import com.dconnect.infrastructure.repository.ChannelsRepository;
import com.dconnect.infrastructure.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;
    private final DiscordClient discordClient;

    public Server getOrCreateServer(String serverId, String creationBy) {
        Optional<Server> server = serverRepository.findByDiscordServerId(serverId);
        return server.orElseGet(() -> createServer(serverId, creationBy));
    }

    public void addChannelToServer(Server server, Channel channel) {
        server.getChannels().add(channel);
        serverRepository.save(server);
    }

    private Server createServer(String serverId, String creationBy) {
        final ServerInfo serverInfo = discordClient.getServerInfo(serverId);

        final Server server = new Server();
        server.setDiscordServerId(serverId);
        server.setName(serverInfo.getName());
        server.setCreationBy(creationBy);
        server.setCreationDate(OffsetDateTime.now());
        return serverRepository.save(server);
    }

}
